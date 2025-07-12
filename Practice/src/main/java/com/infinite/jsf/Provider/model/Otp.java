package com.infinite.jsf.Provider.model;

import java.time.LocalDateTime;
import lombok.Data;
@Data
public class Otp {
	    private int otpId;
	    private Provider provider;
	    private String otpCode;
	    private LocalDateTime expiresAt;
	    private boolean verified;
	    private LocalDateTime createdAt;

	   
		public Otp() {
			super();
			// TODO Auto-generated constructor stub
		}
		//
		 protected void onCreate() {
		        this.createdAt = LocalDateTime.now();
		    }

		    // --- Getters & Setters ---

		    public int getOtpId() {
		        return otpId;
		    }

		    public Provider getProvider() {
		        return provider;
		    }

		    public void setProvider(Provider provider) {
		        this.provider = provider;
		    }

		    public String getOtpCode() {
		        return otpCode;
		    }

		    public void setOtpCode(String otpCode) {
		        this.otpCode = otpCode;
		    }

		    public LocalDateTime getExpiresAt() {
		        return expiresAt;
		    }

		    public void setExpiresAt(LocalDateTime expiresAt) {
		        this.expiresAt = expiresAt;
		    }

		    public boolean isVerified() {
		        return verified;
		    }

		    public void setVerified(boolean verified) {
		        this.verified = verified;
		    }

		    public LocalDateTime getCreatedAt() {
		        return createdAt;
		    }
		
		
		//
		
		
		
		 public Otp(Provider provider, String otpCode, LocalDateTime expiresAt) {
		        this.provider = provider;
		        this.otpCode = otpCode;
		        this.expiresAt = expiresAt;
		    }
		
		 @Override
			public String toString() {
				return "Otp [otpId=" + otpId + ", provider=" + provider + ", otpCode=" + otpCode + ", expiresAt="
						+ expiresAt + ", verified=" + verified + ", createdAt=" + createdAt + "]";
			}
		
	    // Getters and Setters
	}


	  


