package com.fineti.myfineti.fineti;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;


public class Menual_Survey extends ActionBarActivity implements View.OnClickListener{

    // Widget GUI

    private final String serverUrl = "http://fineti.netai.net/newdborder.php";
    protected EditText etname;
    ImageButton btnCalendar;
    Button bSubmitDBOrder;
    UserLocalStore userLocalStore;
    private EditText txtDate;
    private EditText etState;
    private EditText etregion;
    private EditText etDesignation;
    private EditText etCustDB;
    private EditText etTown;
    private EditText etTarget;
    private EditText etAchv;
    private EditText etCustSS;
    private EditText etPjpStts;
    private EditText etReport_to, etRemarks;
    // Variable for storing current date and time
    private int mYear, mMonth, mDay;
    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menual__survey);
        btnCalendar = (ImageButton) findViewById(R.id.btnCalendar);
        txtDate = (EditText) findViewById(R.id.txtDate);
        btnCalendar.setOnClickListener(this);

        etname = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etname);
        etState = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etState);
        etregion = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etregion);
        etDesignation = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etDesignation);
        etCustDB = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etCustDB);
        etTown = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etTown);
        etTarget = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etTarget);
        etAchv = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etAchv);
        etCustSS = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etCustSS);
        etPjpStts = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etPjpStts);
        etReport_to = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etReport_to);
        etRemarks = (EditText) findViewById(R.id.etRemarks);

        bSubmitDBOrder = (Button) findViewById(com.fineti.myfineti.fineti.R.id.bSubmitDBOrder);
        bSubmitDBOrder.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        // set current date into textview
        txtDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(mYear).append(" ").append("-").append(mMonth + 1).append("-")
                .append(mDay));
    }
    @Override
    public void onClick(View v) {
        if (v == btnCalendar) {
            // Process to get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            txtDate.setText(year + "-"
                                    + (monthOfYear + 1) + "-" + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
        }
        if (v == bSubmitDBOrder) {
            String region = etregion.getText().toString();
            String date = txtDate.getText().toString();
            String pr_name = etname.getText().toString();
            String state = etState.getText().toString();
            String designation = etDesignation.getText().toString();
            String db_name = etCustDB.getText().toString();
            String town = etTown.getText().toString();
            String target = etTarget.getText().toString();
            String achievement = etAchv.getText().toString();
            String ss_name = etCustSS.getText().toString();
            String pjp = etPjpStts.getText().toString();
            String report_to = etReport_to.getText().toString();
            String remarks = etRemarks.getText().toString();

            if (  ( !etCustDB.getText().toString().equals("")) && ( !etTown.getText().toString().equals("")) && ( !etPjpStts.getText().toString().equals("") ) ) {
                Toast.makeText(this, "Submitting Data...", Toast.LENGTH_SHORT).show();
                new SignupActivity(this).execute(region, date, pr_name, state, designation, db_name, town, target, achievement, ss_name, pjp, report_to, remarks);
                Intent Intent = new Intent(Menual_Survey.this, Survey.class);
                startActivity(Intent);
            }else {
                Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            }
}}
    @Override
    protected void onStart(){
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
        etDesignation.setText(user.designation);
        etState.setText(user.state);
    }
}
