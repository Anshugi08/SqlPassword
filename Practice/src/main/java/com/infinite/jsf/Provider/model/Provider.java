package com.infinite.jsf.Provider.model;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class Provider {
	    private int providerId;
	    private String firstName;
	    private String lastName;
	    private String userName;
	    private String password;
	    private String email;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        public Provider() {}

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

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

       

        public LocalDateTime getUpdatedAt() {
            return updatedAt;
        }

        
    }

	  
	


