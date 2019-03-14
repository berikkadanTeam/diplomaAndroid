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
import com.login.mobi.loginapp.network.model.booking.Menu;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishes;

import java.util.List;


public class PreorderOrderedDishesAdapter extends RecyclerView.Adapter<PreorderOrderedDishesAdapter.ViewHolder>{

    private Context context;
    private List<Menu> list;
    private List<RestaurantDishes> dishes;

    PreorderOrderedDishesAdapter(Context context, List<Menu> list, List<RestaurantDishes> dishes){
        this.context = context;
        this.list = list;
        this.dishes = dishes;
    }


    @NonNull
    @Override
    public PreorderOrderedDishesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.order_ordered_dishes_item, viewGroup, false);

        return new PreorderOrderedDishesAdapter.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull PreorderOrderedDishesAdapter.ViewHolder viewHolder, final int i) {
        RestaurantDishes dish = dishes.get(i);

        viewHolder.dishName.setText(dishes.get(i).getNameOfDish());
        viewHolder.dishPrice.setText(Integer.toString(dishes.get(i).getPrice()) + " тг.");
        viewHolder.dishAmount.setText(Integer.toString(list.get(i).getDishCount()));
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
