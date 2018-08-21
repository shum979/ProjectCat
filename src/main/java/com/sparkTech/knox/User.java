package com.sparkTech.knox;

/**
 * Created by Shubham A Gupta on 03-May-18.
 */
public class User {

    public String username;
    public String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private User(){}

   public String toJson() {
        return "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }";
    }
}
