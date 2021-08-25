package com.example.myproject;

public class User {
    private String id;
    private String email;
    private String name;
    private String phone;
    private String image;
    private String gender;

    public User() {
    }

    public User(String id, String email, String name, String phone, String image, String gender) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
