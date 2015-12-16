package com.fineti.myfineti.fineti;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class Report extends ActionBarActivity {
    Button bMasterReportView, bRegisterLink, bToDayRprt,bShwFltrRprt,bDwnldMstrRprt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        bToDayRprt = (Button) findViewById(R.id.bToDayRprt);
        bMasterReportView = (Button) findViewById(R.id.bMasterReportView);
        bShwFltrRprt = (Button) findViewById(R.id.bShwFltrRprt);
        bRegisterLink = (Button) findViewById(R.id.bRegisterLink);

        bMasterReportView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(Report.this,
                        mWebView.class);
                startActivity(myIntent);
            }
        });

        bShwFltrRprt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(Report.this,
                        Zonal_RVew.class);
                startActivity(myIntent);
            }
        });

        bRegisterLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(Report.this,
                        Register.class);
                startActivity(myIntent);
                finish();
            }
        });

        bToDayRprt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent browserIntent =
                        new Intent(Intent.ACTION_VIEW, Uri.parse("http://fineti.netai.net/DBOrderSummaryReport.html"));
                startActivity(browserIntent);
            }
        });
    }
}