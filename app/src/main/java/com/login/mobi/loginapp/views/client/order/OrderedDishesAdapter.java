package com.login.mobi.loginapp.views.client.order;

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


public class OrderedDishesAdapter extends RecyclerView.Adapter<OrderedDishesAdapter.ViewHolder>{

    private Context context;
    //private List<RestaurantDishes> list;
    private List<Menu> list;
    private List<RestaurantDishes> dishes;

    OrderedDishesAdapter(Context context, List<Menu> list, List<RestaurantDishes> dishes){
        this.context = context;
        this.list = list;
        this.dishes = dishes;
    }


    @NonNull
    @Override
    public OrderedDishesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.order_ordered_dishes_item, viewGroup, false);

        return new OrderedDishesAdapter.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull OrderedDishesAdapter.ViewHolder viewHolder, final int i) {
        RestaurantDishes dish = dishes.get(i);
        Menu m = list.get(i);

//        for (int k=0; k<list.size(); k++) {
//            for (int j=0; j<dishes.size(); j++){
//                if (dishes.get(j).getId() == list.get(k).getId()) {
//                    viewHolder.dishName.setText(dishes.get(j).getNameOfDish());
//                    viewHolder.dishPrice.setText(Integer.toString(dishes.get(j).getPrice()) + " тг.");
//                    viewHolder.dishAmount.setText(Integer.toString(list.get(k).getDishCount()));
//                }
//            }
//        }

//for (int j = 0; j<dishes.size(); j++) {
//    if (dishes.get(j).getId() == list.get(i).getId()) {
//        viewHolder.dishName.setText(dishes.get(j).getNameOfDish());
//        viewHolder.dishPrice.setText(Integer.toString(dishes.get(j).getPrice()) + " тг.");
//        viewHolder.dishAmount.setText(Integer.toString(list.get(i).getDishCount()));
//    }
//}
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
