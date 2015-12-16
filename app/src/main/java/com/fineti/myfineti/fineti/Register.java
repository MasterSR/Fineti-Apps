package com.fineti.myfineti.fineti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText etName, etMobile, etUsername, etPassword, etState, etRegion,etReport_to, etStatus;
    Button bRegister;
    Spinner spinneretDesignation;

        private String[] DSGN = { "SO", "SSO", "FM", "FSM",
                "ASM", "RSM", "MIS", "Admin"
                 };

    @Override
    protected void onCreate(Bundle savedInstanceDSGN) {
        super.onCreate(savedInstanceDSGN);
        setContentView(com.fineti.myfineti.fineti.R.layout.activity_register);

        System.out.println(DSGN.length);
        spinneretDesignation = (Spinner) findViewById(R.id.etDesignation);
        ArrayAdapter<String> adapter_DSGN = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, DSGN);
        adapter_DSGN
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneretDesignation.setAdapter(adapter_DSGN);
        spinneretDesignation.setOnItemSelectedListener(this);

        etName = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etName);
        etMobile = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etMobile);
        etUsername = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etUsername);
        etPassword = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etPassword);
        etState = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etState);
        etRegion = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etRegion);
        etReport_to = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etReport_to);
        etStatus = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etStatus);

        bRegister = (Button) findViewById(com.fineti.myfineti.fineti.R.id.bRegister);
        bRegister.setOnClickListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        spinneretDesignation.setSelection(position);

        String selDSGN = (String) spinneretDesignation.getSelectedItem();
        Toast.makeText(parent.getContext(), "Selected: " +selDSGN, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case com.fineti.myfineti.fineti.R.id.bRegister:
                String name = etName.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String mobile = etMobile.getText().toString();
                String designation = (String) spinneretDesignation.getSelectedItem();
                String state = etState.getText().toString();
                String region = etRegion.getText().toString();
                String report_to = etReport_to.getText().toString();
                String status = etStatus.getText().toString();
                User user = new User(name, mobile, username, password, designation,state,region,report_to,status);
                registerUser(user);
                break;
        }
    }
    private void registerUser(User user) {

        if (  ( !etName.getText().toString().equals("")) && ( !etUsername.getText().toString().equals(""))&& ( !etRegion.getText().toString().equals("")) && ( !etPassword.getText().toString().equals("") ) ) {
            ServerRequests serverRequest = new ServerRequests(this);
            serverRequest.storeUserDataInBackground(user, new GetUserCallback() {
                @Override
                public void done(User returnedUser) {
                    Intent loginIntent = new Intent(Register.this, Login.class);
                    startActivity(loginIntent);
                }
            });
        }
    }
}