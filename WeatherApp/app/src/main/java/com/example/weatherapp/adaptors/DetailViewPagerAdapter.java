package com.example.weatherapp.adaptors;

import com.example.weatherapp.fragments.PhotosFragment;
import com.example.weatherapp.fragments.TodayFragment;
import com.example.weatherapp.fragments.WeeklyFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class DetailViewPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] childFragments;
    private static final String[] title = {"Today", "Weekly", "Photos"};

    public DetailViewPagerAdapter(FragmentManager manager) {
        super(manager);
        childFragments = new Fragment[] {
                new TodayFragment(), //0
                new WeeklyFragment(), //1
                new PhotosFragment() //2
        };
    }

    @Override
    public Fragment getItem(int position) {
        return childFragments[position];
    }

    @Override
    public int getCount() {
        return childFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
