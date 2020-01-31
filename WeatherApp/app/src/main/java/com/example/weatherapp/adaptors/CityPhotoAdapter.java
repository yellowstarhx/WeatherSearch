package com.example.weatherapp.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CityPhotoAdapter extends RecyclerView.Adapter<CityPhotoAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cityView;

        public ViewHolder(View view) {
            super(view);
            cityView = (ImageView) view.findViewById(R.id.cityView);
        }
    }

    private List<String> imageURLs;

    public CityPhotoAdapter(List<String> imageURLs) {
        this.imageURLs = imageURLs;
    }

    @Override
    public CityPhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item, parent, false);
        CityPhotoAdapter.ViewHolder holder = new CityPhotoAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CityPhotoAdapter.ViewHolder holder, int position) {
        String url = imageURLs.get(position);
        Glide.with(holder.cityView.getContext()).load(imageURLs.get(position)).into(holder.cityView);
    }


    @Override
    public int getItemCount() {
        return imageURLs.size();
    }
}
