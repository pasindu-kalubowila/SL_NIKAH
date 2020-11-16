package com.slnikah.Model;

public class Users {
    String name;
    String access;
    String mobile;
    String email;
    String imageurl;
    String id;

    public Users() {
    }

    public Users(String name, String access, String mobile, String email, String imageurl, String id) {
        this.name = name;
        this.access = access;
        this.mobile = mobile;
        this.email = email;
        this.imageurl = imageurl;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
