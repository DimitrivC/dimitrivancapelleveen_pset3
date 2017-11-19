package com.example.dimitrivc.restaurant;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    // VOOR GLOBAL V2
    //Global g = Global.getInstance();

    // global int als het kan hier al aanmaken zodat niet elke keer dat onCreate
    // wordt aangeroepen deze weer op 0 gaat.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // VOOR GLOBAL V2  - dit is alleen voor ints nog.
        //g.setTest(BuildConfig.VERSION_CODE);
        //List<String> naam = g.getTest();

        // VOOR SINGLETON
        final Singleton orderList = Singleton.getInstance();
            //

        // VOOR SINGLETON (MOGELIJK OVERBODIG)
        Singleton.getInstance().getArrayList();
            //
        // VOOR GLOBAL V2
        //Global g = (Global)getApplication();
        //int data=g.getData();
        //TextView testGlobal = findViewById(R.id.textViewGlobal);
        //testGlobal.setText(data);

        // SHARED PREFS
        loadFromSharedPreferences();

        // VOOR GLOBAL INT:
        // set textview op global int.

        final Intent intent3 = new Intent(this, Main3Activity.class);

        final Intent intent = getIntent();
        final String namePosition = intent.getStringExtra("EXTRA_MESSAGE");
        final TextView textView2 = findViewById(R.id.textView2);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final ListView listViewMenu = findViewById(R.id.listView2);
        String url = "https://resto.mprog.nl/menu";
        final List<String> listdata2 = new ArrayList<>();

        final ArrayAdapter<String> adapter2 =
                new ArrayAdapter<String>(
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
                                            // aan de Global list toevoegen: het id, en ook de name. Name als key,
                                            // id als value. Dus een dict?

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

        ListView name = findViewById(R.id.listView2);


        name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                    // VOOR DE SINGLETON
                //orderList.getInstance().getArrayList().add("x");
                    //

                    // zo krijg je de position, die gelijk is, als string, aan de name van het object:
                    // (en voeg je die toe aan de intent, maar dat wil je niet per se)
                String str =  parent.getAdapter().getItem(position).toString();
                intent3.putExtra("Extra_message4", str);

                // dit met de get(position) werkt! (maar, als je dan via andere intent naar 3 gaat, geeft het niet mee...)
                // dus dat wat je via listdata... krijgt meegeven aan list.
                intent3.putExtra("Extra_message3",listdata2.get(position).toString());
                    //

                // die name check je dan in de global list, en dan neem je de id.
                // die moet dan doorgegeven worden aan button method, via tweede global variable.

                // alternatief: get long id. Want wat is dat? miss wel de id.
                // dan hoef ik die enkel mee te geven aan button via global list.

                // VOOR ORDER COUNT:
                // global int++
                // setTextview van global int op global int nieuwe waarde.

                // SHAREDPREF
                saveToSharedPreferences();

                startActivity(intent3);

            }
        });

    // ON CREATE EINDE
    }


    public void saveToSharedPreferences(){
        // wordt aangeroepen als een item wordt aangeklikt.

        // je kan multiple preferences hebben, zie site.

        // hierin opslaan: shit van global variables: hoeveel orders en welke orders.

        // shared preferences aanmaken: check
        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        // editor aanmaken om aanpassingen eraan te kunnen doen: check
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("key", value);
        editor.putInt("key2", value);
               
        editor.apply();
        // OF
        editor.commit();

    }

    public void loadFromSharedPreferences(){
         // wordt aangeroepen bij onCreate
        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);

        // null is default value, die moet je geven
        String restoredValues = prefs.getString("key", null);

        if (restoredValues != null){
            nameTextView.setText(restoredValues);
        }

        // na de shared prefs even een textwindow maken hier die de orders geeft
        // en elke keer dat een item wordt aangetikt er een bij opteld.

    }

    public void goToOrder(View view) {

        final Intent intent2 = new Intent(this, Main3Activity.class);

        // VOOR SINGLETON
        //intent2.putExtra("EXTRA_MESSAGE2", orderList.getInstance().getArrayList());
            //

        //intent2.putExtra("EXTRA_TEST", "bla");

        // giving (global) list to intent: https://stackoverflow.com/questions/6543811/intent-putextra-list
        startActivity(intent2);
    }

// ACTIVITY EINDE
}
