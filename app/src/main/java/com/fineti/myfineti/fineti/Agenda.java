package com.fineti.myfineti.fineti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;


public class Agenda extends ActionBarActivity{

    UserLocalStore userLocalStore;
    EditText etregion,etname, etDesignation, etUsername;
    TextView etTxtDate, etTxtDay, etTimeNow;
    ImageButton bBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        etTxtDay = (TextView) findViewById(R.id.etTxtDay);
        etTxtDate = (TextView) findViewById(R.id.etTxtDate);
        etTimeNow = (TextView) findViewById(R.id.etTimeNow);
        etUsername = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etUsername);
        etname = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etname);
        etregion = (EditText) findViewById(R.id.etregion);
        etDesignation = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etDesignation);
        userLocalStore = new UserLocalStore(this);

        String delegate = "dd/MMM/yy"; // 09/21/2011 02:17 pm
        String TT = "HH:mm:ss a";

        java.util.Date noteTS = Calendar.getInstance().getTime();
        etTimeNow.setText(DateFormat.format(TT, noteTS));
        etTxtDate.setText(DateFormat.format(delegate, noteTS));

        String delegate1 = "EEEE"; // Wednesday
        java.util.Date noteTS1 = Calendar.getInstance().getTime();
        etTxtDay.setText(DateFormat.format(delegate1, noteTS1));
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
        etregion.setText(user.region);
    }
    }
