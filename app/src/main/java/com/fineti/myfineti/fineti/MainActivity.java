package com.fineti.myfineti.fineti;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    final Context context = this;
    UserLocalStore userLocalStore;
    EditText etregion,etname, etDesignation, etUsername;
    Button bLogout, bREPORT, bSURVEY, bPJP, bALERT, bTARGET, bLOCATION, bCONTACTUS, bOTHERS;
    TextView etTxtDate, etTxtDay, etTimeNow;
    ImageButton bProfile, bCall, bText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.fineti.myfineti.fineti.R.layout.activity_main);

        etTxtDay = (TextView) findViewById(R.id.etTxtDay);
        etTxtDate = (TextView) findViewById(R.id.etTxtDate);
        etUsername = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etUsername);
        etname = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etname);
        etregion = (EditText) findViewById(R.id.etregion);
        etDesignation = (EditText) findViewById(com.fineti.myfineti.fineti.R.id.etDesignation);
        bLogout = (Button) findViewById(com.fineti.myfineti.fineti.R.id.bLogout);
        etTimeNow = (TextView) findViewById(R.id.etTimeNow);


        bLogout.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);

        bREPORT = (Button) findViewById(R.id.bREPORT);
        bSURVEY = (Button) findViewById(R.id.bSURVEY);
        bPJP = (Button) findViewById(R.id.bPJP);
        bALERT = (Button) findViewById(R.id.bALERT);
        bTARGET = (Button) findViewById(R.id.bTARGET);
        bLOCATION = (Button) findViewById(R.id.bLOCATION);
        bCONTACTUS = (Button) findViewById(R.id.bCONTACTUS);
        bCall = (ImageButton) findViewById(R.id.bCall);
        bOTHERS = (Button) findViewById(R.id.bOTHERS);
        bProfile = (ImageButton) findViewById(R.id.bProfile);
        bText = (ImageButton) findViewById(R.id.bText);

        // add PhoneStateListener
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        // add button listener
        bCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9768327053"));
                startActivity(callIntent);
            }
        });

        String delegate = "dd/MMM/yy"; // 09/21/2011 02:17 pm
        String TT = "HH:mm:ss a";
        String DAYTT = "EEEE";
        java.util.Date noteTS = Calendar.getInstance().getTime();
        etTxtDate.setText(DateFormat.format(delegate, noteTS));
        etTimeNow.setText(DateFormat.format(TT, noteTS));
        etTxtDay.setText(DateFormat.format(DAYTT, noteTS));

        bProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        MProfile.class);
                startActivity(myIntent);
            }
        });

        bText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        text_massage.class);
                startActivity(myIntent);
            }
        });


        bSURVEY.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        Survey.class);
                startActivity(myIntent);
            }
        });

        bREPORT.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        Report.class);
                startActivity(myIntent);
            }
        });


        bPJP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        Agenda.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case com.fineti.myfineti.fineti.R.id.bLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                Intent loginIntent = new Intent(this, Login.class);
                startActivity(loginIntent);
                break;
        }
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
        etDesignation.setText(user.designation);
    }

    //monitor phone call activities
    private class PhoneCallListener extends PhoneStateListener {

        String LOG_TAG = "LOGGING 123";
        private boolean isPhoneCalling = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }
}
