package com.login.mobi.loginapp.views.client.restaurants;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.login.mobi.loginapp.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class RestaurantImagesAdapter extends RecyclerView.Adapter<com.login.mobi.loginapp.views.client.restaurants.RestaurantImagesAdapter.MyViewHolder> {

    //private Context context;
    private ArrayList<String> imagesList;
    private ProgressBar progressBar;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image_item_image);
            progressBar = (ProgressBar) view.findViewById(R.id.progress);
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
        Glide.with(holder.imageView.getContext()).load(imagesList.get(position)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).apply(new RequestOptions().error(R.drawable.photo_no_photo)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

}
