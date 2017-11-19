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

        // POST request for https://resto.mprog.nl/order (GET method not allowed)
        // A POST to this endpoint with the collection of menu item id values will
        // submit the order and will return a response with the estimated time before
        // the order will be ready. The IDs you send need to be be contained with JSON
        // data under the key, menuIds. When you parse the JSON, an estimate of the time
        // before the order is ready will be under the key “preparation_time”.

        Intent intent = getIntent();

        final TextView textView3 = findViewById(R.id.textView);
        textView3.setText(intent.getStringExtra("Extra_message4"));

        // uit intent krijg je collectie van id's.
        // getting list from intent: https://stackoverflow.com/questions/6543811/intent-putextra-list

        // nu maar even list aanmaken met twee id's.:
        List<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");

        // { = object ; [ = array.

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

            //The IDs you send need to be be contained with JSON
            // data under the key, menuIds. When you parse the JSON, an estimate of the time
            // before the order is ready will be under the key “preparation_time”.

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
                        // werkt.
                        textView3.setText(response.toString());
                        //listdata3.add(response.toString());
                        //listView3.setAdapter(adapter2);
                        try {


                        listdata3.add(response.getJSONArray("preparation_time").getString(1));

                            // convert JSONObject to JSONArray
//                            JSONArray jsonArray = response.getJSONArray("preparation_time");
//                            if (jsonArray != null) {
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    listdata3.add(jsonArray.getString(i));
//                                }
//                            }

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
