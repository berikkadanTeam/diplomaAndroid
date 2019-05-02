package com.login.mobi.loginapp.views.client.menu.orders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.booking.MyBookingsMenu;

import java.util.List;


public class OrderDetailsOrderedDishesAdapter extends RecyclerView.Adapter<OrderDetailsOrderedDishesAdapter.ViewHolder>{

    private Context context;
    private List<MyBookingsMenu> list;

    public OrderDetailsOrderedDishesAdapter(Context context, List<MyBookingsMenu> list){
        this.context = context;
        this.list = list;
    }

    // Чтобы при поиске отобразить новый список найденных заказов
    public void arrayChanged(List<MyBookingsMenu> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderDetailsOrderedDishesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.order_ordered_dishes_item, viewGroup, false);

        return new OrderDetailsOrderedDishesAdapter.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull OrderDetailsOrderedDishesAdapter.ViewHolder viewHolder, final int i) {
        MyBookingsMenu dish = list.get(i);

        viewHolder.dishName.setText(dish.getNameOfDish());
        viewHolder.dishPrice.setText(Integer.toString(dish.getPrice()) + " ₸");
        viewHolder.dishAmount.setText(Integer.toString(dish.getDishCount()));

        viewHolder.updateUI(dish);

    }

    @Override
    public int getItemCount() {     return list.size();     }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView dishName, dishPrice, dishAmount;

        MyBookingsMenu booking;

        public void updateUI(MyBookingsMenu booking){
            this.booking = booking;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dishName = itemView.findViewById(R.id.preoder_dish_name);
            dishPrice = itemView.findViewById(R.id.preorder_dish_price);
            dishAmount = itemView.findViewById(R.id.preoder_dish_amount);
        }


    }
}
