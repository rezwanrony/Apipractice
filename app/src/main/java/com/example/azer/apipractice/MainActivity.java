package com.example.azer.apipractice;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
TextView tv_show;
    String data="";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show=(TextView)findViewById(R.id.tv_show);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait Loading....");
        progressDialog.setCancelable(false);
        fetchdata();

    }

    void fetchdata(){
        showpDialog();
        String url="http://59205b856a9bc800110a13c2.mockapi.io/api/fb/person";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<response.length();i++) {
                    try {
                        JSONObject person=(JSONObject)response.get(i);
                        String name=person.getString("name");
                        String phonenumber=person.getString("phonenumber");
                        String Email=person.getString("Email");

                       data+="Name: "+name+"\n\n";
                       data+="Phonenumber: "+phonenumber+"\n\n";
                       data+="Email: "+Email+"\n\n\n";


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    hidepDialog();
                }

                tv_show.setText(data);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley log",error);
                Toast.makeText(getApplicationContext(),"Error occured",Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

           AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        Toast.makeText(getApplicationContext(),"Data loaded s" +
                "uccessfully",Toast.LENGTH_SHORT).show();
    }

    private void showpDialog() {
       if (!progressDialog.isShowing())
        progressDialog.show();
        }
    private void hidepDialog() {
      if (progressDialog.isShowing())
        progressDialog.dismiss();
        }
}
