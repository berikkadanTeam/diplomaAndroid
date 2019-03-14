package com.login.mobi.loginapp.views.client.restaurants;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.login.mobi.loginapp.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class RestaurantImagesAdapter extends RecyclerView.Adapter<com.login.mobi.loginapp.views.client.restaurants.RestaurantImagesAdapter.MyViewHolder> {

    //private Context context;
    private ArrayList<String> imagesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image_item_image);
        }
    }

    public RestaurantImagesAdapter(ArrayList<String> imagesList) {
        //this.context = context;
        this.imagesList = imagesList;
    }

    @Override
    public RestaurantImagesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_image_item_card, parent, false);

        return new RestaurantImagesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantImagesAdapter.MyViewHolder holder, int position) {
        Glide.with(holder.imageView.getContext()).load(imagesList.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

}
