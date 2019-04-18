package com.login.mobi.loginapp.views.client.restaurants;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;
import com.login.mobi.loginapp.views.client.Booking;

import java.util.List;


public class RestaurantAdapter extends RecyclerView.Adapter<com.login.mobi.loginapp.views.client.restaurants.RestaurantAdapter.ViewHolder>{

    private Context context;
    private List<Restaurant> list;

    RestaurantAdapter(Context context, List<Restaurant> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public com.login.mobi.loginapp.views.client.restaurants.RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.restaurant_fragment_item, viewGroup, false);

        return new com.login.mobi.loginapp.views.client.restaurants.RestaurantAdapter.ViewHolder(v);
    }


    // Чтобы при поиске отобразить новый список найденных ресторанов
    public void arrayChanged(List<Restaurant> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull com.login.mobi.loginapp.views.client.restaurants.RestaurantAdapter.ViewHolder viewHolder, final int i) {
        Restaurant restaurant = list.get(i);
        String filePath = "http://5.23.55.101/Files/";  // berikkadan.kz домен просрочен
        String fileName = list.get(i).getFileName();
        if (fileName != null) {
            String image = filePath.concat(fileName);
            Glide.with(viewHolder.iv.getContext()).load(image).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    viewHolder.progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    viewHolder.progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).apply(new RequestOptions().error(R.drawable.photo_no_photo).diskCacheStrategy(DiskCacheStrategy.ALL)).into(viewHolder.iv);
            //Picasso.get().load(image).into(viewHolder.iv);
            // TO-DO
            //Picasso.get().load(list.get(i).getFilePath()).into(viewHolder.iv);
        } else
            Glide.with(viewHolder.iv.getContext()).load(R.drawable.photo_no_photo).into(viewHolder.iv);

        Log.d("FUN", "onBindViewHolder: "+list.get(i).getFilePath());
        viewHolder.name.setText(list.get(i).getName());
        viewHolder.description.setText(list.get(i).getDescription());
        Log.d("adapter", list.get(i).getName());
        viewHolder.updateUI(restaurant);

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, RestaurantInformationPage.class);
//                intent.putExtra("RestaurantID", list.get(i).getId());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView iv;
        public TextView name;
        public TextView description;
        public ProgressBar progressBar;

        Restaurant restaurant;
        public void updateUI(Restaurant restaurant){
            this.restaurant = restaurant;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            iv = itemView.findViewById(R.id.iv);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
        }

        @Override
        public void onClick(View v) {
            Booking.clear();
            Intent intent = new Intent(context, RestaurantInformationPage.class);
//          intent.putExtra("RestaurantID", list.get(i).getId());
            intent.putExtra("restaurantData", new Gson().toJson(restaurant)); // посылаем все данные по выбранному ресторану
            context.startActivity(intent);
        }
    }
}
