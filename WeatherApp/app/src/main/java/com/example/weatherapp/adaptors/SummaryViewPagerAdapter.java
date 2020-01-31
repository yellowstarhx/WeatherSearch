package com.example.weatherapp.adaptors;

import com.example.weatherapp.fragments.SummaryFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SummaryViewPagerAdapter extends FragmentPagerAdapter {
    List<SummaryFragment> childFragments;

    public SummaryViewPagerAdapter(FragmentManager manager) {
        super(manager);
        childFragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return childFragments.get(position);
    }

    @Override
    public int getCount() {
        return childFragments.size();
    }

    public void add(SummaryFragment summaryFragment) {
        childFragments.add(summaryFragment);
    }

}
