package com.infinite.jsf.Provider.controller;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Query;
import org.hibernate.Session;

import com.infinite.jsf.Provider.dao.ProviderDao;
import com.infinite.jsf.Provider.dao.ProviderDaoImpl;
import com.infinite.jsf.Provider.model.Provider;
import com.infinite.jsf.Provider.util.SessionHelper;

@ManagedBean(name = "providerController")
@SessionScoped

public class ProviderController implements Serializable {
	private static final long serialVersionUID = 1L;

	private ProviderDao providerdao = new ProviderDaoImpl();

	// For signup
	private Provider signupProvider = new Provider();

	// For login
	private String loginOrEmail;
	private String loginPassword;

	// Logged in user
	private Provider currentUser;

	// For change password
	private String oldPassword;
	private String newPassword;
	private String newPasswordConfirm;

	// For forgot-password/OTP
	private String forgotEmail;
	private int forgotProviderId;
	private String otpCode;
	private String resetNewPassword;
	private String resetNewPasswordConfirm;

	// ───── Signup ────────────────────────────────────────────────────────────────
	public String signup() {
		try {
			providerdao.signup(signupProvider);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Signup successful. Please login."));
			return "login.jsp?faces-redirect=true";
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
			return null;
		}
	}

	// ───── Login ─────────────────────────────────────────────────────────────────
	public String login() {
		try {
			Provider p = providerdao.login(loginOrEmail, loginPassword);
			if (p != null) {
				currentUser = p;
				return "dashboard.jsp?faces-redirect=true";
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid credentials", null));
				return null;
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
			return null;
		}
	}

	// change password---------------------

	public String changePassword() {
		if (!newPassword.equals(newPasswordConfirm)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Passwords do not match", null));
			return null;
		}

		try {
			providerdao.changePassword(currentUser.getProviderId(), oldPassword, newPassword);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Password changed successfully."));
			return "dashboard.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
			return null;
		}
	}

	// ───── Forgot Password: send OTP ─────────────────────────────────────────────
	public String sendForgotPasswordOtp() {
		try {
			// 1) Delegate to DAO to generate & email the OTP
			providerdao.sendForgotPasswordOtp(forgotEmail);

			// 2) Now look up that Provider’s ID for the JSF flow
			Session session = null;
			try {
				session = SessionHelper.getSessionFactory().openSession();
				Query q = session.createQuery("FROM Provider p WHERE p.email = :e");
				q.setParameter("e", forgotEmail);
				Object result = q.uniqueResult();
				if (result != null) {
					Provider p = (Provider) result;
					forgotProviderId = p.getProviderId();
				} else {
					forgotProviderId = 0;
				}
			} finally {
				if (session != null) {
					session.close();
				}
			}

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("OTP sent to your email."));
			return "validate-otp.xhtml?faces-redirect=true";

		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
			return null;
		}
	}

	// ───── Validate OTP ──────────────────────────────────────────────────────────
	public String validateForgotPasswordOtp() {
		try {
			boolean ok = providerdao.validateForgotPasswordOtp(forgotProviderId, otpCode);
			if (!ok) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid or expired OTP", null));
				return null;
			}
			return "reset-password.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
			return null;
		}
	}

	// ───── Complete Forgot Password ──────────────────────────────────────────────
	public String completeForgotPassword() {
		if (!resetNewPassword.equals(resetNewPasswordConfirm)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Passwords do not match", null));
			return null;
		}

		try {
			providerdao.completeForgotPassword(forgotProviderId, resetNewPassword);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Password reset successfully. Please login."));
			return "login.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
			return null;
		}
	}

	// ───── Logout ────────────────────────────────────────────────────────────────
	public String logout() {
		currentUser = null;
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login.xhtml?faces-redirect=true";
	}

	// ───── Getters & Setters ────────────────────────────────────────────────────

	public Provider getSignupProvider() {
		return signupProvider;
	}

	public void setSignupProvider(Provider signupProvider) {
		this.signupProvider = signupProvider;
	}

	public String getLoginOrEmail() {
		return loginOrEmail;
	}

	public void setLoginOrEmail(String loginOrEmail) {
		this.loginOrEmail = loginOrEmail;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public Provider getCurrentUser() {
		return currentUser;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordConfirm() {
		return newPasswordConfirm;
	}

	public void setNewPasswordConfirm(String newPasswordConfirm) {
		this.newPasswordConfirm = newPasswordConfirm;
	}

	public String getForgotEmail() {
		return forgotEmail;
	}

	public void setForgotEmail(String forgotEmail) {
		this.forgotEmail = forgotEmail;
	}

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

	public String getResetNewPassword() {
		return resetNewPassword;
	}

	public void setResetNewPassword(String resetNewPassword) {
		this.resetNewPassword = resetNewPassword;
	}

	public String getResetNewPasswordConfirm() {
		return resetNewPasswordConfirm;
	}

	public void setResetNewPasswordConfirm(String resetNewPasswordConfirm) {
		this.resetNewPasswordConfirm = resetNewPasswordConfirm;
	}
}
