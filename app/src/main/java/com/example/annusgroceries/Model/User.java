package com.example.annusgroceries.Model;

public class User {

    private String name;
    private String email;
    private String phone;
    private String address;

    public User(String email,String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
