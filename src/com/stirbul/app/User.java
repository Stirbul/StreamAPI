package com.stirbul.app;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class User {

    private String name;
    private boolean isActive;
    private ArrayList<String> roles;
    private ArrayList<String> emails;
    private Double balance;
    private LocalDateTime registrationDate;

    private User(Builder builder) {
        this.name = builder.name;
        this.isActive = builder.isActive;
        this.roles = builder.roles;
        this.emails = builder.emails;
        this.balance = builder.balance;
        this.registrationDate = builder.registrationDate;
    }

    public static class Builder {
        private String name;
        private boolean isActive;
        private ArrayList<String> roles;
        private ArrayList<String> emails;
        private Double balance;
        private LocalDateTime registrationDate;

        public Builder(String name){
            this.name = name;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder isActive(boolean isActive){
            this.isActive = isActive;
            return this;
        }

        public Builder roles(ArrayList<String> roles){
            this.roles = roles;
            return this;
        }

        public Builder emails(ArrayList<String> emails){
            this.emails = emails;
            return this;
        }

        public Builder balance(Double balance){
            this.balance = balance;
            return this;
        }

        public Builder registrationDate(LocalDateTime registrationDate){
            this.registrationDate = registrationDate;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }


    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        User o = (User) obj;

        return this.getName().compareTo(o.getName()) == 0;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
