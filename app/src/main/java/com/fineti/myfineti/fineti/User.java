package com.fineti.myfineti.fineti;

/**
 * Created by sirin_000 on 27-Oct-15.
 */
public class User {

    String name, username, password, mobile, designation, state, region, report_to, status;

    public User(String name, String mobile, String username, String password, String designation, String state, String region, String report_to, String status) {
        this.name = name;
        this.mobile = mobile;
        this.username = username;
        this.password = password;
        this.designation = designation;
        this.state = state;
        this.region = region;
        this.report_to = report_to;
        this.status = status;
    }

    public User( String username, String password) {
        this("","", username, password,"","","","","");
    }


}
