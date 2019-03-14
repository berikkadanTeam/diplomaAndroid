package com.login.mobi.loginapp.views.client.restaurantMenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishes;

import java.util.List;


public class RestaurantDishesPreorderAdapter extends RecyclerView.Adapter<RestaurantDishesPreorderAdapter.ViewHolder>{

    private Context context;
    private List<RestaurantDishes> list;


    RestaurantDishesPreorderAdapter(Context context, List<RestaurantDishes> list){
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public RestaurantDishesPreorderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.restaurant_dishes_preorder_item, viewGroup, false);

        return new RestaurantDishesPreorderAdapter.ViewHolder(v);
    }


    // Чтобы при поиске отобразить новый список найденного типа блюда
    public void arrayChanged(List<RestaurantDishes> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull RestaurantDishesPreorderAdapter.ViewHolder viewHolder, final int i) {
        RestaurantDishes dish = list.get(i);

        viewHolder.dishName.setText(list.get(i).getNameOfDish());
        viewHolder.dishPrice.setText(Integer.toString(list.get(i).getPrice()) + " тг.");
        viewHolder.dishPrice.setText("4");

        viewHolder.updateUI(dish);

    }

    @Override
    public int getItemCount() {     return list.size();     }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView dishName, dishPrice, dishAmount;
        private LinearLayout removePreorderDishBtn;

        RestaurantDishes dishes;

        public void updateUI(RestaurantDishes restaurant){

            this.dishes = restaurant;


        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dishName = itemView.findViewById(R.id.preoder_dish_name);
            dishPrice = itemView.findViewById(R.id.preorder_dish_price);
            dishAmount = itemView.findViewById(R.id.preoder_dish_amount);
            removePreorderDishBtn = itemView.findViewById(R.id.preorder_dish_remove_button);


        }





    }
}
