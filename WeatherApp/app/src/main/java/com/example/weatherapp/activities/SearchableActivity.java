package com.example.weatherapp.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;
import com.example.weatherapp.appDataHandler.LocationInfo;
import com.example.weatherapp.external.ServerAPI;
import com.example.weatherapp.fragments.SummaryFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SearchableActivity extends AppCompatActivity {

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private SummaryFragment result;
    private SearchableActivity mActivity;
    private Toolbar myToolbar;
    private LinearLayout linlaHeaderProgress;
    private LocationInfo locationInfo;
    String query;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("SearchableActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        linlaHeaderProgress = findViewById(R.id.linlaHeaderProgress);
        linlaHeaderProgress.setVisibility(View.VISIBLE);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        query = intent.getStringExtra(SearchManager.QUERY);
        doMySearch(query);

        myToolbar = (Toolbar) findViewById(R.id.result_toolbar);
        myToolbar.setTitle(query);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });

        mActivity = this;
        fragmentManager = this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        result = new SummaryFragment();

        Log.e("SearchableActivity", "onCreate finish");

        FloatingActionButton fab = findViewById(R.id.fab_plus);
        fab.setBackgroundColor(Color.parseColor("#690569"));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if (locationInfo != null) {
                    Toast.makeText(getApplicationContext(), query + "was added to favorites", Toast.LENGTH_SHORT).show();
//                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                    SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(locationInfo);
                    prefsEditor.putString(locationInfo.getAddress(), json);
                    prefsEditor.apply();
                }

            }
        });
    }

    private void doMySearch(String query) {
        Log.d("Searchable", "querying " + query);

        // get Location Info

        String url = String.format(ServerAPI.GEO_CODING, query);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("JSON", response.toString());
                            double lat = response.getJSONObject("location").getDouble("lat");
                            double lon = response.getJSONObject("location").getDouble("lng");
                            String addr = response.getString("formatted address");

                            locationInfo = new LocationInfo(lat, lon, addr);

                            // give the info to search result
//                            result = (SummaryFragment) fragmentManager.findFragmentById(R.id.frame_summary);


                            result.setLocationInfo(mActivity, locationInfo);
                            Log.d("SearchableActivity", "summary fragment has been set");
                            fragmentTransaction.replace(R.id.frame_summary, result);
                            fragmentTransaction.commit();

                            linlaHeaderProgress.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            Log.e("JSON Exception", "getting Geo Coding");
                            // show err
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", "getting Geo Coding");
                        // show err
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }


}
