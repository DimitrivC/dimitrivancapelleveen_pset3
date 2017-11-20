package com.example.dimitrivc.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main2Activity extends AppCompatActivity {


    Integer orderCount = 0;
    ArrayList<String> forOrders = new ArrayList<>();
    Map<String, String> dictionary = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final TextView ordercount = findViewById(R.id.textview_ordercount);
        ordercount.setText(orderCount);

        // SHARED PREFS
        //loadFromSharedPreferences();

        final Intent intent = getIntent();
        final String namePosition = intent.getStringExtra("EXTRA_MESSAGE");
        final TextView textView2 = findViewById(R.id.textview_error);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final ListView listViewMenu = findViewById(R.id.listView2);
        String url = "https://resto.mprog.nl/menu";
        final List<String> listdata2 = new ArrayList<>();

        final ArrayAdapter<String> adapter2 =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        listdata2);

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //textView2.setText(response.toString());

                        try {

                            // convert JSONObject to JSONArray
                            JSONArray jsonArray2 = response.getJSONArray("items");

                            if (jsonArray2 != null) {
                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    if (namePosition.equals("entrees")) {
                                        if (jsonArray2.getJSONObject(i).getString("category").equals("entrees")) {
                                            listdata2.add(jsonArray2.getJSONObject(i).getString("name"));
                                            // aan de Global list toevoegen: het id, en ook de name. Name als key
                                            dictionary.put(jsonArray2.getJSONObject(i).getString("name"), jsonArray2.getJSONObject(i).getString("id"));
                                        }
                                    }
                                    if (namePosition.equals("appetizers")) {
                                        if (jsonArray2.getJSONObject(i).getString("category").equals("appetizers")) {
                                            listdata2.add(jsonArray2.getJSONObject(i).getString("name"));
                                        }
                                    }
                                }
                            }

                            listViewMenu.setAdapter(adapter2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView2.setText("@string/error");
                    }
                });
        requestQueue.add(jsonObjectRequest2);

        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String str =  parent.getAdapter().getItem(position).toString();

                String order_id = dictionary.get(str);

                forOrders.add(order_id);

                orderCount += 1;
                ordercount.setText(orderCount);
                
                // SHAREDPREF
                //saveToSharedPreferences();
            }
        });

    // ON CREATE EINDE
    }


//    public void saveToSharedPreferences(){
//        // wordt aangeroepen als een item wordt aangeklikt.
//
//        // je kan multiple preferences hebben, zie site.
//
//        // shared preferences aanmaken: check
//        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
//        // editor aanmaken om aanpassingen eraan te kunnen doen: check
//        SharedPreferences.Editor editor = prefs.edit();
//
//        //editor.putArrayList("key_id_array_list", forOrders);
//
//
//        //Set<String> set = myScores.getStringSet("key", null);
//
//        //Set the values
//        Set<String> set = new HashSet<String>();
//        set.addAll(forOrders);
//        editor.putStringSet("key_ids", set);
//
//        editor.putInt("key_order_count", orderCount);
//
//
//
//        editor.apply();
//        // OF
//        editor.commit();
//
//    }
//
//    public void loadFromSharedPreferences(){
//         // wordt aangeroepen bij onCreate
//        TextView ordercount = findViewById(R.id.textview_ordercount);
//        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
//
//        // null is default value, die moet je geve
//        String restoredOrderCount = prefs.getString("key_order_count", null);
//
////        ArrayList<String> arrayIdList = prefs.getArrayList("key_id_array_list", null);
////
////          if (arrayIdList != null) {
////             forOrders.addAll(arrayIdList);
////             {
//
//        Set<String> name = prefs.getStringSet("key_ids", null);
//
//        if (name != null) {
//
//        }
//
//        if (restoredOrderCount != null){
//            ordercount.setText(restoredOrderCount);
//        }
//   }

    public void goToOrder(View view) {

        final Intent intent2 = new Intent(this, Main3Activity.class);

        intent2.putExtra("Extra_list", forOrders);

        // giving (global) list to intent: https://stackoverflow.com/questions/6543811/intent-putextra-list
        startActivity(intent2);
    }

// ACTIVITY EINDE
}
