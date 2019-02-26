package com.login.mobi.loginapp.views.restaurantMenu;

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

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishTypes;

import java.util.List;


public class RestaurantDishTypesAdapter extends RecyclerView.Adapter<RestaurantDishTypesAdapter.ViewHolder>{

    private Context context;
    private List<RestaurantDishTypes> list;
    String restaurantID;

    RestaurantDishTypesAdapter(Context context, List<RestaurantDishTypes> list){
        this.context = context;
        this.list = list;
    }

    public void setRestaurantID(String restId){
        this.restaurantID = restId;
    }


    @NonNull
    @Override
    public RestaurantDishTypesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.restaurant_dish_types_item, viewGroup, false);

        return new RestaurantDishTypesAdapter.ViewHolder(v);
    }


    // Чтобы при поиске отобразить новый список найденного типа блюда
    public void arrayChanged(List<RestaurantDishTypes> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull RestaurantDishTypesAdapter.ViewHolder viewHolder, final int i) {
        RestaurantDishTypes dishType = list.get(i);

        viewHolder.iv.setImageResource(R.drawable.photo_salad_2);
        viewHolder.name.setText(list.get(i).getTitle());

        viewHolder.updateUI(dishType,list.get(i).getId());

    }

    @Override
    public int getItemCount() {     return list.size();     }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView iv;
        public TextView name;

        RestaurantDishTypes dishType;
        public void updateUI(RestaurantDishTypes restaurant, final int i){
            this.dishType = restaurant;

            // ВТОРОЙ СПОСОБ СДЕЛАТЬ onClick на View
//            View v;
//            v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("TEST", "onClick: ");
//                    Intent intent = new Intent(context, RestaurantDishesPage.class);
//                    intent.putExtra("DishTypeID", dishType.getId());
//                    intent.putExtra("ResID", resId);
//                    context.startActivity(intent);
//                }
//            });
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            v = itemView;
            itemView.setOnClickListener(this);
            iv = itemView.findViewById(R.id.iv);
            name = itemView.findViewById(R.id.name);
        }


        @Override
        public void onClick(View v) {
                Log.d("RestDishTypesAdapter", restaurantID + " - " + dishType.getId());
                Intent intent = new Intent(context, RestaurantDishesPage.class);
                intent.putExtra("DishTypeID", dishType.getId());
                intent.putExtra("RestaurantID", restaurantID);
                context.startActivity(intent);
            }



    }
}
