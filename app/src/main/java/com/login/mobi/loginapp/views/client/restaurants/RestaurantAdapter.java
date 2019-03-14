package com.login.mobi.loginapp.views.client.restaurants;

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
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;
import com.squareup.picasso.Picasso;

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
