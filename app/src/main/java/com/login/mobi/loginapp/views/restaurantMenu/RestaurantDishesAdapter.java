package com.login.mobi.loginapp.views.restaurantMenu;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishes;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RestaurantDishesAdapter extends RecyclerView.Adapter<RestaurantDishesAdapter.ViewHolder>{

    private Context context;
    private List<RestaurantDishes> list;
    private int dishTypeID;
    private int amountOfDish = 0;
    private TextView amountOfDishTextView;

    RestaurantDishesAdapter(Context context, List<RestaurantDishes> list, int dishTypeID){
        this.context = context;
        this.list = list;
        this.dishTypeID = dishTypeID;
    }


    @NonNull
    @Override
    public RestaurantDishesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.restaurant_dishes_item, viewGroup, false);

        return new RestaurantDishesAdapter.ViewHolder(v);
    }


    // Чтобы при поиске отобразить новый список найденного типа блюда
    public void arrayChanged(List<RestaurantDishes> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull RestaurantDishesAdapter.ViewHolder viewHolder, final int i) {
        RestaurantDishes dish = list.get(i);
        //if (list.get(i).getTypeId() == dishTypeID) {
            String filePath = "http://5.23.55.101/Files/menu/" + list.get(i).getFileName();
            Picasso.get().load(filePath).into(viewHolder.iv);
            // TO-DO
            //Picasso.get().load(list.get(i).getFilePath()).into(viewHolder.iv);
            //viewHolder.iv.setImageResource(R.drawable.photo_salad_2);
            viewHolder.name.setText(list.get(i).getNameOfDish());
        //}

        viewHolder.updateUI(dish);

    }

    @Override
    public int getItemCount() {     return list.size();     }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView iv;
        public TextView name;

        RestaurantDishes dishes;
        public void updateUI(RestaurantDishes restaurant){
            this.dishes = restaurant;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            iv = itemView.findViewById(R.id.iv);
            name = itemView.findViewById(R.id.name);

            /* Select amount of dishes */
            CardView minusDish = (CardView) itemView.findViewById(R.id.minus_dish);
            CardView plusDish = (CardView) itemView.findViewById(R.id.plus_dish);
            amountOfDishTextView = (TextView) itemView.findViewById(R.id.dish_count);
            plusDish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (amountOfDish < 10) { // TODO: Change max count
                        amountOfDish++;
                        amountOfDishTextView.setText(String.valueOf(amountOfDish));
                    }
                }
            });
            minusDish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (amountOfDish != 1) {
                        amountOfDish--;
                        amountOfDishTextView.setText(String.valueOf(amountOfDish));
                    }
                    if (amountOfDish == 1){
                        amountOfDishTextView.setText(String.valueOf(amountOfDish));
                    }
                }
            });

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, RestaurantDishInformationPage.class);
            //intent.putExtra("DishID", dishes.getId());
            //intent.putExtra("DishID", new Gson().toJson(dishType)); // посылаем все данные по выбранному ресторану
            context.startActivity(intent);
        }
    }
}
