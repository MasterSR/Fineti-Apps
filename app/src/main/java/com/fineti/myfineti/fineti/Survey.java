package com.fineti.myfineti.fineti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class Survey extends ActionBarActivity {

    UserLocalStore userLocalStore;
    EditText etregion,etname, etDesignation, etUsername;
    TextView etTxtDate, etTxtDay, etTimeNow;
    ImageButton bBack;
    Button bDBOrderPJP, bDBOrderNONPJP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        etTxtDay = (TextView) findViewById(R.id.etTxtDay);
        etTxtDate = (TextView) findViewById(R.id.etTxtDate);
        etTimeNow = (TextView) findViewById(R.id.etTimeNow);
        etUsername = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etUsername);
        etname = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etname);
        etregion = (EditText) findViewById(R.id.etregion);
        etDesignation = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etDesignation);
        userLocalStore = new UserLocalStore(this);

        String delegate = "dd/MMM/yy";
        String TT = "HH:mm:ss a";
        String DAYTT = "EEEE";

        java.util.Date noteTS = Calendar.getInstance().getTime();
        etTxtDate.setText(DateFormat.format(delegate, noteTS));
        etTimeNow.setText(DateFormat.format(TT, noteTS));
        etTxtDay.setText(DateFormat.format(DAYTT, noteTS));

        bBack = (ImageButton) findViewById(R.id.bBack);
        bBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(Survey.this,
                        MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
        bDBOrderNONPJP = (Button) findViewById(R.id.bDBOrderNONPJP);
        bDBOrderNONPJP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(Survey.this,
                        Menual_Survey.class);
                startActivity(myIntent);
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
