package com.fineti.myfineti.fineti;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends ActionBarActivity implements View.OnClickListener {

    Button bLogin;

    EditText etUsername, etPassword;

    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.fineti.myfineti.fineti.R.layout.activity_login);

        bLogin = (Button) findViewById(com.fineti.myfineti.fineti.R.id.bLogin);
        etUsername = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etUsername);
        etPassword = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etPassword);

        bLogin.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case com.fineti.myfineti.fineti.R.id.bLogin:
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                User user = new User(username, password);

                authenticate(user);
                break;
        }
    }

    private void authenticate(User user) {
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchUserDataAsyncTask(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null) {
                    showErrorMessage();
                } else {
                    logUserIn(returnedUser);
                }
            }
        });
    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Incorrect user details");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void logUserIn(User returnedUser) {
        userLocalStore.setUserLoggedIn(true);
        userLocalStore.storeUserData(returnedUser);
        startActivity(new Intent(this, MainActivity.class));
    }
}
