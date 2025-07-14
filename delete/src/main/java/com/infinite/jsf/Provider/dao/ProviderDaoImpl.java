package com.infinite.jsf.Provider.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.infinite.jsf.Provider.model.Otp;
import com.infinite.jsf.Provider.model.PasswordHistory;
import com.infinite.jsf.Provider.model.Provider;
import com.infinite.jsf.Provider.util.MailSend;
import com.infinite.jsf.Provider.util.PasswordEncryptor;
import com.infinite.jsf.Provider.util.SessionHelper;
import com.infinite.jsf.Provider.util.OTPGenerator;

public class ProviderDaoImpl implements ProviderDao {

    private final SessionFactory factory = SessionHelper.getSessionFactory();

    @Override
    public void signup(Provider provider) throws Exception {
        System.out.println("signup() called for user: " + provider.getUserName());
        String rawPwd = provider.getPassword();
        String hashed = PasswordEncryptor.hash(rawPwd);
        provider.setPassword(hashed);

        Session session = null;
        Transaction tx = null;
        try {
            session = factory.openSession();
            System.out.println("Hibernate session opened for signup()");
            tx = session.beginTransaction();

            session.save(provider);
            System.out.println("Saved provider: " + provider);
            session.save(new PasswordHistory(provider, hashed));
            System.out.println("Saved initial password history.");

            tx.commit();
            System.out.println("Transaction committed for signup()");
        } catch (Exception ex) {
            System.out.println("Exception in signup(): " + ex.getMessage());
            if (tx != null) {
                tx.rollback();
                System.out.println("Transaction rolled back in signup()");
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            if (session != null) {
                session.close();
                System.out.println("Hibernate session closed for signup()");
            }
        }
    }

    @Override
    public Provider login(String usernameOrEmail, String password) throws Exception {
        System.out.println("login() called for: " + usernameOrEmail);
        Session session = null;
        try {
            session = factory.openSession();
            System.out.println("Hibernate session opened for login()");
            String hql = "FROM Provider p "
                       + "WHERE (p.userName = :u OR p.email = :u)";
            Query q = session.createQuery(hql);
            q.setParameter("u", usernameOrEmail);

            Object result = q.uniqueResult();
            System.out.println("Query result for login(): " + result);
            if (result == null) {
                return null;
            }
            Provider provider = (Provider) result;

            if (PasswordEncryptor.verify(password, provider.getPassword())) {
                System.out.println("Password verified for: " + usernameOrEmail);
                return provider;
            } else {
                System.out.println("Password verification failed for: " + usernameOrEmail);
            }
            return null;
        } catch (Exception ex) {
            System.out.println("Exception in login(): " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        } finally {
            if (session != null) {
                session.close();
                System.out.println("Hibernate session closed for login()");
            }
        }
    }

    @Override
    public void changePassword(int providerId,
                               String oldPassword,
                               String newPassword) throws Exception {
        System.out.println("changePassword() called for providerId: " + providerId);
        Session session = null;
        Transaction tx = null;

        try {
            session = factory.openSession();
            System.out.println("Hibernate session opened for changePassword()");
            tx = session.beginTransaction();

            Provider provider = (Provider) session.get(Provider.class, providerId);
            System.out.println("Fetched provider: " + provider);
            if (provider == null) {
                throw new Exception("Provider not found");
            }
            if (!PasswordEncryptor.verify(oldPassword, provider.getPassword())) {
                throw new Exception("Old password is incorrect");
            }

            String hqlHist = "SELECT ph.passwordHash "
                           + "FROM PasswordHistory ph "
                           + "WHERE ph.provider.providerId = :pid "
                           + "ORDER BY ph.changedAt DESC";
            Query qHist = session.createQuery(hqlHist);
            qHist.setParameter("pid", providerId);
            qHist.setMaxResults(3);
            @SuppressWarnings("unchecked")
            List<String> last3 = qHist.list();
            System.out.println("Last 3 password hashes: " + last3);

            String newHashed = PasswordEncryptor.hash(newPassword);
            if (last3.contains(newHashed)) {
                throw new Exception("New password must differ from your last 3 passwords");
            }

            provider.setPassword(newHashed);
            session.update(provider);
            session.save(new PasswordHistory(provider, newHashed));
            System.out.println("Password changed and history updated.");

            tx.commit();
            System.out.println("Transaction committed for changePassword()");
        } catch (Exception ex) {
            System.out.println("Exception in changePassword(): " + ex.getMessage());
            if (tx != null) {
                tx.rollback();
                System.out.println("Transaction rolled back in changePassword()");
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            if (session != null) {
                session.close();
                System.out.println("Hibernate session closed for changePassword()");
            }
        }
    }

    @Override
    public void sendForgotPasswordOtp(String email) throws Exception {
        System.out.println("sendForgotPasswordOtp() called for: " + email);
        Provider provider;
        Session session = null;

        try {
            session = factory.openSession();
            System.out.println("Hibernate session opened for sendForgotPasswordOtp()");
            String hql = "FROM Provider p WHERE p.email = :e";
            Query q = session.createQuery(hql);
            q.setParameter("e", email);
            Object res = q.uniqueResult();
            provider = (res == null) ? null : (Provider) res;
            System.out.println("Found provider: " + provider);
        } catch (Exception ex) {
            System.out.println("Exception fetching provider in sendForgotPasswordOtp(): " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        } finally {
            if (session != null) {
                session.close();
                System.out.println("Hibernate session closed after lookup in sendForgotPasswordOtp()");
            }
        }

        if (provider == null) {
            throw new Exception("No account found for email: " + email);
        }

        String code = OTPGenerator.generateOTP(6);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
        Otp otp = new Otp(provider, code, expiresAt);
        System.out.println("Generated OTP: " + code + " expires at " + expiresAt);

        try {
            session = factory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(otp);
            tx.commit();
            System.out.println("OTP saved to DB.");
        } catch (Exception ex) {
            System.out.println("Exception saving OTP in sendForgotPasswordOtp(): " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        } finally {
            if (session != null) {
                session.close();
                System.out.println("Hibernate session closed after saving OTP");
            }
        }

        MailSend.sendInfo(
            email,
            "Your Password Reset OTP",
            "Your OTP is: " + code + "\nIt expires in 5 minutes."
        );
        System.out.println("OTP emailed to: " + email);
    }

    @Override
    public boolean validateForgotPasswordOtp(int providerId, String otpCode) throws Exception {
        System.out.println("validateForgotPasswordOtp() called for providerId: " + providerId);
        Otp otp;
        Session session = null;

        try {
            session = factory.openSession();
            System.out.println("Hibernate session opened for validateForgotPasswordOtp()");
            String hql = "FROM Otp o "
                       + "WHERE o.provider.providerId = :pid "
                       + "ORDER BY o.createdAt DESC";
            Query q = session.createQuery(hql);
            q.setParameter("pid", providerId);
            q.setMaxResults(1);
            @SuppressWarnings("unchecked")
            List<Otp> list = q.list();
            otp = list.isEmpty() ? null : list.get(0);
            System.out.println("Fetched OTP: " + otp);
        } catch (Exception ex) {
            System.out.println("Exception fetching OTP in validateForgotPasswordOtp(): " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        } finally {
            if (session != null) {
                session.close();
                System.out.println("Hibernate session closed after OTP fetch");
            }
        }

        if (otp == null || otp.isVerified()) {
            System.out.println("No OTP to validate or already verified.");
            return false;
        }
        if (LocalDateTime.now().isAfter(otp.getExpiresAt())
            || !otp.getOtpCode().equals(otpCode)) {
            System.out.println("OTP expired or mismatch.");
            return false;
        }

        try {
            session = factory.openSession();
            Transaction tx = session.beginTransaction();
            otp.setVerified(true);
            session.update(otp);
            tx.commit();
            System.out.println("OTP marked as verified.");
        } catch (Exception ex) {
            System.out.println("Exception updating OTP in validateForgotPasswordOtp(): " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        } finally {
            if (session != null) {
                session.close();
                System.out.println("Hibernate session closed after verifying OTP");
            }
        }

        return true;
    }

    @Override
    public void completeForgotPassword(int providerId, String newPassword) throws Exception {
        System.out.println("completeForgotPassword() called for providerId: " + providerId);
        Session session = null;
        Transaction tx = null;

        try {
            session = factory.openSession();
            System.out.println("Hibernate session opened for completeForgotPassword()");
            tx = session.beginTransaction();

            Provider provider = (Provider) session.get(Provider.class, providerId);
            System.out.println("Fetched provider: " + provider);
            if (provider == null) {
                throw new Exception("Provider not found");
            }

            String hqlHist = "SELECT ph.passwordHash "
                           + "FROM PasswordHistory ph "
                           + "WHERE ph.provider.providerId = :pid "
                           + "ORDER BY ph.changedAt DESC";
            Query qHist = session.createQuery(hqlHist);
            qHist.setParameter("pid", providerId);
            qHist.setMaxResults(3);
            @SuppressWarnings("unchecked")
            List<String> last3 = qHist.list();
            System.out.println("Last 3 password hashes: " + last3);

            String newHashed = PasswordEncryptor.hash(newPassword);
            if (last3.contains(newHashed)) {
                throw new Exception("New password must differ from your last 3 passwords");
            }

            provider.setPassword(newHashed);
            session.update(provider);
            session.save(new PasswordHistory(provider, newHashed));
            System.out.println("Password reset and history updated.");

            tx.commit();
            System.out.println("Transaction committed for completeForgotPassword()");
        } catch (Exception ex) {
            System.out.println("Exception in completeForgotPassword(): " + ex.getMessage());
            if (tx != null) {
                tx.rollback();
                System.out.println("Transaction rolled back in completeForgotPassword()");
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            if (session != null) {
                session.close();
                System.out.println("Hibernate session closed for completeForgotPassword()");
            }
        }
    }
}
