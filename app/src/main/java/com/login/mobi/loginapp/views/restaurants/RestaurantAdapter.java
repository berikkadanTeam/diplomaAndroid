package com.login.mobi.loginapp.views.restaurants;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.login.mobi.loginapp.DetailsActivity;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.views.authorization.SignUpPage;
import com.login.mobi.loginapp.views.authorization.WelcomePage;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RestaurantAdapter extends RecyclerView.Adapter<com.login.mobi.loginapp.views.restaurants.RestaurantAdapter.ViewHolder>{

    private Context context;
    private List<Restaurant> list;

    RestaurantAdapter(Context context, List<Restaurant> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public com.login.mobi.loginapp.views.restaurants.RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.restaurant_fragment_item, viewGroup, false);

        return new com.login.mobi.loginapp.views.restaurants.RestaurantAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull com.login.mobi.loginapp.views.restaurants.RestaurantAdapter.ViewHolder viewHolder, final int i) {
        Restaurant restaurant = list.get(i);
        String filePath = "http://berikkadan.kz/Files/";
        String fileName = list.get(i).getFileName();
        String image = filePath.concat(fileName);
        Picasso.get().load(image).into(viewHolder.iv);
        // TO-DO
        //Picasso.get().load(list.get(i).getFilePath()).into(viewHolder.iv);

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
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, RestaurantInformationPage.class);
//          intent.putExtra("RestaurantID", list.get(i).getId());
            intent.putExtra("restaurantData", new Gson().toJson(restaurant)); // посылаем все данные по выбранному ресторану
            context.startActivity(intent);
        }
    }
}
