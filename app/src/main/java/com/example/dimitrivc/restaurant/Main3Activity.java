package com.example.dimitrivc.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent intent = getIntent();

        final TextView textView3 = findViewById(R.id.textView);
        textView3.setText(intent.getStringExtra("Extra_message4"));

        // { = object ; [ = array.

        List<String> arrayList = intent.getStringArrayListExtra("Extra_list");

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        // iterate over arrayList and add items to JsonArray
        for (int i=0; i<arrayList.size(); i++)
            jsonArray.put(arrayList.get(i));

        // put key menuIds and value jsonArray in jsonObject
        try {
            jsonObject.put("menuIds", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ListView listView3 = findViewById(R.id.listView3);
        final List<String> listdata3 = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://resto.mprog.nl/order";

        final ArrayAdapter<String> adapter2 =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        listdata3);

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest
                (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //textView3.setText(response.toString());

                        try {

                        listdata3.add(response.getString("preparation_time"));

                            listView3.setAdapter(adapter2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView3.setText("@string/error");
                    }
                });
        requestQueue.add(jsonObjectRequest2);

    }
}
