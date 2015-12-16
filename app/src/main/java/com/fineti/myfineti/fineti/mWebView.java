package com.fineti.myfineti.fineti;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class mWebView extends ActionBarActivity {
    private WebView ViewMasterReport;
    private String LOG_TAG = "AndroidWebViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_web_view);

        ViewMasterReport = (WebView) findViewById(R.id.ViewMasterReport);
        //enable Zooming
        ViewMasterReport.getSettings().setBuiltInZoomControls(true);
        //enable Javascript
        ViewMasterReport.getSettings().setJavaScriptEnabled(true);
        //loads the WebView completely zoomed out
        ViewMasterReport.getSettings().setLoadWithOverviewMode(true);

        //true makes the Webview have a normal viewport such as a normal desktop browser
        //when false the webview will have a viewport constrained to it's own dimensions
        ViewMasterReport.getSettings().setUseWideViewPort(true);

        //override the web client to open all links in the same webview
        ViewMasterReport.setWebViewClient(new MyWebViewClient());
        ViewMasterReport.setWebChromeClient(new MyWebChromeClient());

        //Injects the supplied Java object into this WebView. The object is injected into the
        //JavaScript context of the main frame, using the supplied name. This allows the
        //Java object's public methods to be accessed from JavaScript.


        //load the home page URL
        ViewMasterReport.loadUrl("http://fineti.net23.net/DBOrderSummaryReport.html");
    }

    //Web view has record of all pages visited so you can go back and forth
    //just override the back button to go back in history if there is page
    //available for display
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && ViewMasterReport.canGoBack()) {
            ViewMasterReport.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //customize your web view client to open links from your own site in the
    //same web view otherwise just open the default browser activity with the URL
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("www.fineti.net23.net")) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

        //display alert message in Web View
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d(LOG_TAG, message);
            new AlertDialog.Builder(view.getContext())
                    .setMessage(message).setCancelable(true).show();
            result.confirm();
            return true;
        }

    }

    public class JavaScriptInterface {
        Context mContext;

        // Instantiate the interface and set the context
        JavaScriptInterface(Context c) {
            mContext = c;
        }

        //using Javascript to call the finish activity
        public void closeMyActivity() {
            finish();
        }

    }

}