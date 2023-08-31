package com.facebook.volley_pract_post;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText nameEdt, jobEdt;
    private Button postDataBtn;
    private TextView responseTV;
    RequestQueue mqueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameEdt = findViewById(R.id.idEdtName);
        jobEdt = findViewById(R.id.idEdtJob);
        postDataBtn = findViewById(R.id.idBtnPost);
        responseTV = findViewById(R.id.idTVResponse);
        mqueue= Volley.newRequestQueue(this);

        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=nameEdt.getText().toString();
                String job=jobEdt.getText().toString();
                postusingvolley(name,job);
            }
        });

    }

    private void postusingvolley(String name, String job) {
        String url="https://reqres.in/api/users";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            jobEdt.setText("");
            nameEdt.setText("");
                Toast.makeText(MainActivity.this, "Data posted succesfully", Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    responseTV.setText(jsonObject.getString("name") +"\n"+jsonObject.getString("job"));

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                  error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params=new HashMap<>();
                params.put("name",name);
                params.put("job",job);
                return params;
            }
        };
        mqueue.add(stringRequest);
    }
}