package com.fineti.myfineti.fineti;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sirin_000 on 27-Oct-15.
 */
public class UserLocalStore {

    public static final String SP_NAME = "userDetails";

    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("name", user.name);
        userLocalDatabaseEditor.putString("mobile", user.mobile);
        userLocalDatabaseEditor.putString("username", user.username);
        userLocalDatabaseEditor.putString("password", user.password);
        userLocalDatabaseEditor.putString("designation", user.designation);
        userLocalDatabaseEditor.putString("state", user.state);
        userLocalDatabaseEditor.putString("region", user.region);
        userLocalDatabaseEditor.putString("report_to", user.report_to);
        userLocalDatabaseEditor.putString("status", user.status);

        userLocalDatabaseEditor.commit();
    }




    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }

    public User getLoggedInUser() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == false) {
            return null;
        }

        String name = userLocalDatabase.getString("name", "");
        String mobile = userLocalDatabase.getString("mobile", "");
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        String designation = userLocalDatabase.getString("designation", "");
        String state = userLocalDatabase.getString("state", "");
        String region = userLocalDatabase.getString("region", "");
        String report_to = userLocalDatabase.getString("report_to", "");
        String status = userLocalDatabase.getString("status", "");

        User user = new User(name, mobile, username, password,designation,state,region,report_to,status);
        return user;
    }
}
