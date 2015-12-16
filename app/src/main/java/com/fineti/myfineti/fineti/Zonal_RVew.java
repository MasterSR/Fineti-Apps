package com.fineti.myfineti.fineti;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class Zonal_RVew extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG_RESULTS="result";
    private static final String TAG_ID = "region";
    private static final String TAG_NAME = "date";
    private static final String TAG_ADD ="pr_name";
    Spinner spinneretRegion;
    String myJSON;
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;
    private Button buttonGet;
    private String[] REG = {"Select Region", "EAST", "WEST", "NORTH", "SOUTH"};
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zonal__rvew);
        System.out.println(REG.length);
        spinneretRegion = (Spinner) findViewById(R.id.selREG);
        ArrayAdapter<String> adapter_DSGN = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, REG);
        adapter_DSGN
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneretRegion.setAdapter(adapter_DSGN);
        spinneretRegion.setOnItemSelectedListener(this);
        buttonGet = (Button) findViewById(R.id.buttonGet);
        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String,String>>();

        buttonGet.setOnClickListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        spinneretRegion.setSelection(position);

        String selREG = (String) spinneretRegion.getSelectedItem();
        Toast.makeText(parent.getContext(), "Selected: " +selREG, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String region = c.getString(TAG_ID);
                String date = c.getString(TAG_NAME);
                String pr_name = c.getString(TAG_ADD);
                HashMap<String,String> persons = new HashMap<String,String>();
                persons.put(TAG_ID,region);
                persons.put(TAG_NAME,date);
                persons.put(TAG_ADD,pr_name);
                personList.add(persons);
                }
            ListAdapter adapter = new SimpleAdapter(Zonal_RVew.this, personList, R.layout.list_item, new String[]{TAG_ID,TAG_NAME,TAG_ADD}, new int[]{R.id.id, R.id.name, R.id.address}
            );
            list.setAdapter(adapter);
            } catch (JSONException e) {
            e.printStackTrace();
            }
        }
    private void getData() {
        String id = spinneretRegion.getSelectedItem().toString().trim();
        if (id.equals("")) {
            Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = Config.DATA_URL+spinneretRegion.getSelectedItem().toString().trim();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
                myJSON=response;
                showList();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Zonal_RVew.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onClick(View v) {
        if (personList.isEmpty()) {
            getData();
        }
        else {
            personList.clear();
            getData();
        }
    }
}
