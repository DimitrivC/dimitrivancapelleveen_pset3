package com.example.dimitrivc.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(this, Main2Activity.class);
        final TextView mTextView = findViewById(R.id.textViewCategories);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final ListView listCategories = findViewById(R.id.listCategories);
        String url = "https://resto.mprog.nl/categories";
        final List<String> listdata = new ArrayList<>();

        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        listdata);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //mTextView.setText(response.toString());
                        try {

                            // convert JSONObject to JSONArray
                            JSONArray jsonArray = response.getJSONArray("categories");
                            if (jsonArray != null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    listdata.add(jsonArray.getString(i));
                                }
                            }

                            listCategories.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText("@string/error");
                    }
                });
        requestQueue.add(jsonObjectRequest);

        ListView name = findViewById(R.id.listCategories);

        name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                intent.putExtra("EXTRA_MESSAGE", listdata.get(position).toString());
                startActivity(intent);
            }
        });

    // EINDE ON CREATE
    }

// EINDE MAIN ACTIVITY
}
