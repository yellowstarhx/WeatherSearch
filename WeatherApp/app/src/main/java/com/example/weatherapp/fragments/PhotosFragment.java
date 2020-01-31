package com.example.weatherapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.weatherapp.R;
import com.example.weatherapp.activities.DetailedWeatherActivity;
import com.example.weatherapp.adaptors.CityPhotoAdapter;
import com.example.weatherapp.external.ServerAPI;

public class PhotosFragment extends Fragment {

    private View view;
    private String address;
    private DetailedWeatherActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (DetailedWeatherActivity) getActivity();
        address = mActivity.getAddress();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_photos, container, false);
        setUI();
        return view;
    }

    private void setUI() {
        // step 1 get urls
        final String url = String.format(ServerAPI.CITY_VIEW, address);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mActivity);

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray array = response.getJSONArray("link");

                            Set<String> urlSet = new HashSet<>();
                            for (int i = 0; i < array.length(); i++) {
                                urlSet.add(array.getString(i));
                            }
                            Log.d("Parsing URL List", urlSet.toString());

                            // step 2 set RecyclerView
                            setRecyclerView(new ArrayList<>(urlSet));

                        } catch (JSONException e) {
                            Log.e("JSON Exception", "Error get location by IP");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", "Error get location by IP");
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    private void setRecyclerView(List<String> urlList) {
        RecyclerView recyclerView = view.findViewById(R.id.detail_photos_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        CityPhotoAdapter cityPhotoAdapter = new CityPhotoAdapter(urlList);
        recyclerView.setAdapter(cityPhotoAdapter);
    }

}
