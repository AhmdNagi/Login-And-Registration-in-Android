package com.nagi.socyle.socyleapp.ModelClass;

/**
 * Created by Fehoo on 6/15/2016.
 */
public class User {

    private String userName;
    private String userEmail;
    private String password;
    private String phone;

    public User(){}
    public User(String name , String email , String password , String phone){
        this.setUserName(name);
        this.setUserEmail(email);
        this.setPassword(password);
        this.setPhone(phone);
    }
    public User(String email , String password ){
        this.setUserEmail(email);
        this.setPassword(password);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
