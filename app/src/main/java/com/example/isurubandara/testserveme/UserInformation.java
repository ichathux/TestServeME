package com.example.isurubandara.testserveme;

/**
 * Created by Isuru Bandara on 4/25/2017.
 */

public class UserInformation {



    public String fname, lname, address, phone, dob, about, utype, uid, avatar;

    public UserInformation(String fname, String lname, String address, String phone, String dob, String about, String utype, String uid, String avatar) {
        this.uid = uid;
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.phone = phone;
        this.dob = dob;
        this.about = about;
        this.utype = utype;
        this.avatar = avatar;
    }

    public UserInformation(){

    }



    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) { this.fname = fname; }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) { this.avatar = avatar; }
}
