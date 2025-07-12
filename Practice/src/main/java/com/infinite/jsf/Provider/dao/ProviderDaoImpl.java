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
//Session session = null;
//Transaction tx= null;



   // private final SessionFactory factory = SessionHelper.getSessionFactory();
   

       // private final SessionHelper sessionHelper = SessionHelper.getInstance();
   

        private final SessionFactory factory = SessionHelper.getConnection();

        @Override
        public void signup(Provider provider) throws Exception {
            String rawPwd = provider.getPassword();
            String hashed = PasswordEncryptor.hash(rawPwd);
            provider.setPassword(hashed);

            Session session = null;
            Transaction tx = null;
            try {
                session = factory.openSession();
                tx = session.beginTransaction();

                session.save(provider);
                session.save(new PasswordHistory(provider, hashed));

                tx.commit();
            } catch (Exception ex) {
                if (tx != null) tx.rollback();
                throw ex;
            } finally {
                if (session != null) session.close();
            }
        }

        @Override
        public Provider login(String usernameOrEmail, String password) throws Exception {
            Session session = null;
            try {
                session = factory.openSession();
                String hql = "FROM Provider p "
                           + "WHERE (p.userName = :u OR p.email = :u)";
                Query q = session.createQuery(hql);
                q.setParameter("u", usernameOrEmail);

                Object result = q.uniqueResult();
                if (result == null) {
                    return null;
                }
                Provider provider = (Provider) result;

                // verify BCrypt hash
                if (PasswordEncryptor.verify(password, provider.getPassword())) {
                    return provider;
                }
                return null;
            } finally {
                if (session != null) session.close();
            }
        }

        @Override
        public void changePassword(int providerId,
                                   String oldPassword,
                                   String newPassword) throws Exception {
            Session session = null;
            Transaction tx = null;

            try {
                session = factory.openSession();
                tx = session.beginTransaction();

                Provider provider = (Provider) session.get(Provider.class, providerId);
                if (provider == null) {
                    throw new Exception("Provider not found");
                }
                if (!PasswordEncryptor.verify(oldPassword, provider.getPassword())) {
                    throw new Exception("Old password is incorrect");
                }

                // fetch last 3 hashes
                String hqlHist = "SELECT ph.passwordHash "
                               + "FROM PasswordHistory ph "
                               + "WHERE ph.provider.providerId = :pid "
                               + "ORDER BY ph.changedAt DESC";
                Query qHist = session.createQuery(hqlHist);
                qHist.setParameter("pid", providerId);
                qHist.setMaxResults(3);
                @SuppressWarnings("unchecked")
                List<String> last3 = qHist.list();

                String newHashed = PasswordEncryptor.hash(newPassword);
                if (last3.contains(newHashed)) {
                    throw new Exception("New password must differ from your last 3 passwords");
                }

                provider.setPassword(newHashed);
                session.update(provider);
                session.save(new PasswordHistory(provider, newHashed));

                tx.commit();
            } catch (Exception ex) {
                if (tx != null) tx.rollback();
                throw ex;
            } finally {
                if (session != null) session.close();
            }
        }

        @Override
        public void sendForgotPasswordOtp(String email) throws Exception {
            Provider provider;
            Session session = null;

            // 1) Find provider by email
            try {
                session = factory.openSession();
                String hql = "FROM Provider p WHERE p.email = :e";
                Query q = session.createQuery(hql);
                q.setParameter("e", email);
                Object res = q.uniqueResult();
                provider = (res == null) ? null : (Provider) res;
            } finally {
                if (session != null) session.close();
            }

            if (provider == null) {
                throw new Exception("No account found for email: " + email);
            }

            // 2) Generate & save OTP
            String code = OTPGenerator.generateOTP(6);
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
            Otp otp = new Otp(provider, code, expiresAt);

            try {
                session = factory.openSession();
                Transaction tx = session.beginTransaction();
                session.save(otp);
                tx.commit();
            } finally {
                if (session != null) session.close();
            }

            // 3) Send via your existing mail method
            MailSend.sendInfo(
                email,
                "Your Password Reset OTP",
                "Your OTP is: " + code + "\nIt expires in 5 minutes."
            );
        }

        @Override
        public boolean validateForgotPasswordOtp(int providerId, String otpCode) throws Exception {
            Otp otp;
            Session session = null;

            // 1) Fetch latest OTP
            try {
                session = factory.openSession();
                String hql = "FROM Otp o "
                           + "WHERE o.provider.providerId = :pid "
                           + "ORDER BY o.createdAt DESC";
                Query q = session.createQuery(hql);
                q.setParameter("pid", providerId);
                q.setMaxResults(1);
                @SuppressWarnings("unchecked")
                List<Otp> list = q.list();
                otp = list.isEmpty() ? null : list.get(0);
            } finally {
                if (session != null) session.close();
            }

            if (otp == null || otp.isVerified()) {
                return false;
            }
            if (LocalDateTime.now().isAfter(otp.getExpiresAt())
                || !otp.getOtpCode().equals(otpCode)) {
                return false;
            }

            // 2) Mark verified
            try {
                session = factory.openSession();
                Transaction tx = session.beginTransaction();
                otp.setVerified(true);
                session.update(otp);
                tx.commit();
            } finally {
                if (session != null) session.close();
            }

            return true;
        }

        @Override
        public void completeForgotPassword(int providerId, String newPassword) throws Exception {
            Session session = null;
            Transaction tx = null;

            try {
                session = factory.openSession();
                tx = session.beginTransaction();

                Provider provider = (Provider) session.get(Provider.class, providerId);
                if (provider == null) {
                    throw new Exception("Provider not found");
                }

                // fetch last 3 hashes
                String hqlHist = "SELECT ph.passwordHash "
                               + "FROM PasswordHistory ph "
                               + "WHERE ph.provider.providerId = :pid "
                               + "ORDER BY ph.changedAt DESC";
                Query qHist = session.createQuery(hqlHist);
                qHist.setParameter("pid", providerId);
                qHist.setMaxResults(3);
                @SuppressWarnings("unchecked")
                List<String> last3 = qHist.list();

                String newHashed = PasswordEncryptor.hash(newPassword);
                if (last3.contains(newHashed)) {
                    throw new Exception("New password must differ from your last 3 passwords");
                }

                provider.setPassword(newHashed);
                session.update(provider);
                session.save(new PasswordHistory(provider, newHashed));

                tx.commit();
            } catch (Exception ex) {
                if (tx != null) tx.rollback();
                throw ex;
            } finally {
                if (session != null) session.close();
            }
        }
    }

        
       
       
        
           

            
                   

    

   

        


