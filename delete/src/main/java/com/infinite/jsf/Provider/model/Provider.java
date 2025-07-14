package com.infinite.jsf.Provider.model;

import java.time.LocalDateTime;

import java.util.Date;

public class Provider {

	private int providerId;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String email;
	//private LocalDateTime createdAt;
	private Date updatedAt;

	// -- Getters and Setters --

	public int getProviderId() {
		return providerId;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	// ← Here are the password accessors you need
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Provider(int providerId, String firstName, String lastName, String userName, String password, String email,
			Date createdAt, Date updatedAt) {
		super();
		this.providerId = providerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Provider [providerId=" + providerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", userName=" + userName + ", password=" + password + ", email=" + email + ", createdAt=" + 
				", updatedAt=" + updatedAt + "]";
	}

	public Provider() {

		Date now = new Date(); // gets the current date and time
		
		this.updatedAt = now;

	}

	
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
