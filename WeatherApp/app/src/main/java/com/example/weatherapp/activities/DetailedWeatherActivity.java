package com.example.weatherapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.weatherapp.R;
import com.example.weatherapp.adaptors.DetailViewPagerAdapter;
import com.example.weatherapp.external.TwitterAPI;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.weatherapp.appDataHandler.DetailedWeather;
import com.google.android.material.tabs.TabLayout;

public class DetailedWeatherActivity extends AppCompatActivity {

    private Toolbar toolbar;
//    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar myToolbar;

    private DetailedWeather detailedWeather;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_weather);
        detailedWeather = (DetailedWeather) getIntent().getSerializableExtra("DetailedWeather");
        address = getIntent().getStringExtra("address");

        myToolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(myToolbar);

        viewPager = (ViewPager) findViewById(R.id.detail_viewpager);
        DetailViewPagerAdapter adapter = new DetailViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.detail_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.calendar_today);
        tabLayout.getTabAt(1).setIcon(R.drawable.trending_up);
        tabLayout.getTabAt(2).setIcon(R.drawable.google_photos);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.twitter_bar, menu);
        myToolbar.setTitle(address);
        myToolbar.setBackgroundColor(Color.BLACK);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.twitter_button:
                // share to twitter
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(TwitterAPI.setTwitterSharedContent(detailedWeather.getCity(), detailedWeather.getTemperature())));
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public DetailedWeather getDetailedWeather() {
        return detailedWeather;
    }

    public String getAddress() { return address; }
}
