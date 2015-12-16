package com.fineti.myfineti.fineti;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by sirin_000 on 26-Nov-15.
 */
public class SignupActivity extends AsyncTask<String, Void, String> {
    private Context context;

    public SignupActivity(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {
        String region = arg0[0];
        String date = arg0[1];
        String pr_name = arg0[2];
        String state = arg0[3];
        String designation = arg0[4];
        String db_name = arg0[5];
        String town = arg0[6];
        String target = arg0[7];
        String achievement = arg0[8];
        String ss_name = arg0 [9];
        String pjp = arg0[10];
        String report_to = arg0[11];
        String remarks = arg0[12];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?region=" + URLEncoder.encode(region, "UTF-8");
            data += "&date=" + URLEncoder.encode(date, "UTF-8");
            data += "&pr_name=" + URLEncoder.encode(pr_name, "UTF-8");
            data += "&state=" + URLEncoder.encode(state, "UTF-8");
            data += "&designation=" + URLEncoder.encode(designation, "UTF-8");
            data += "&db_name=" + URLEncoder.encode(db_name, "UTF-8");
            data += "&town=" + URLEncoder.encode(town, "UTF-8");
            data += "&target=" + URLEncoder.encode(target, "UTF-8");
            data += "&achievement=" + URLEncoder.encode(achievement, "UTF-8");
            data += "&ss_name=" + URLEncoder.encode(ss_name, "UTF-8");
            data += "&pjp=" + URLEncoder.encode(pjp, "UTF-8");
            data += "&report_to=" + URLEncoder.encode(report_to, "UTF-8");
            data += "&remarks=" + URLEncoder.encode(remarks, "UTF-8");


            link = "http://fineti.netai.net/newdborder.php" + data;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        String jsonStr = result;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Bravo! Data inserted successfully.", Toast.LENGTH_SHORT).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Data could not be inserted. Upload failed.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Couldn't connect to Server. Contact Headquarter.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }
}
