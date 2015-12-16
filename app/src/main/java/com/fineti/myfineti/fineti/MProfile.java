package com.fineti.myfineti.fineti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MProfile extends ActionBarActivity {

    UserLocalStore userLocalStore;
    EditText etname, etUsername, etDesignation, etState, etregion, etReport_to;
    ImageButton bBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mprofile);

        etUsername = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etUsername);
        etname = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etname);
        etDesignation = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etDesignation);
        etState = (EditText) findViewById(R.id.etState);
        etregion = (EditText) findViewById(R.id.etregion);
        etReport_to = (EditText) findViewById(R.id.etReport_to);
        userLocalStore = new UserLocalStore(this);

        bBack = (ImageButton) findViewById(R.id.bBack);

        bBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate() == true) {
            displayUserDetails();
        }
    }

    private boolean authenticate() {
        if (userLocalStore.getLoggedInUser() == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

    private void displayUserDetails() {
        User user = userLocalStore.getLoggedInUser();
        etname.setText(user.name);
        etUsername.setText(user.username);
        etDesignation.setText(user.designation);
        etState.setText(user.state);
        etregion.setText(user.region);
        etReport_to.setText(user.report_to);
    }

}
