package com.login.mobi.loginapp.views.client.order;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.booking.Menu;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishes;
import com.login.mobi.loginapp.views.client.restaurantMenu.RestaurantDishInformationPage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class TabsDishesAdapter extends RecyclerView.Adapter<TabsDishesAdapter.ViewHolder>{

    private Context context;
    private List<RestaurantDishes> list;
    private int dishTypeID;
    private List<Menu> chosenDishList = new ArrayList<>();
    TabsMainActivity activity;

    TabsDishesAdapter(TabsMainActivity context, List<RestaurantDishes> list, int dishTypeID){
        this.activity = context;
        this.list = list;
        this.dishTypeID = dishTypeID;
    }

    public List<Menu> getSelected(){
        return chosenDishList;
    }


    @NonNull
    @Override
    public TabsDishesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(R.layout.order_tabs_dishes_item, viewGroup, false);

        return new TabsDishesAdapter.ViewHolder(v);
    }


    // Чтобы при поиске отобразить новый список найденного типа блюда
    public void arrayChanged(List<RestaurantDishes> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull TabsDishesAdapter.ViewHolder viewHolder, final int i) {
        RestaurantDishes dish = list.get(i);
        String filePath = "http://5.23.55.101/Files/menu/" + list.get(i).getFileName();
        Picasso.get().load(filePath).into(viewHolder.iv);
            // TO-DO
            //Picasso.get().load(list.get(i).getFilePath()).into(viewHolder.iv);
            //viewHolder.iv.setImageResource(R.drawable.photo_salad_2);
        viewHolder.name.setText(list.get(i).getNameOfDish());
        viewHolder.price.setText(Integer.toString(list.get(i).getPrice()) + " тг.");
        //}

        viewHolder.updateUI(dish);

    }

    @Override
    public int getItemCount() {     return list.size();     }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView iv;
        public TextView name, price;
        CardView minusDish, plusDish;
        private int amountOfDish = 0;
        private TextView amountOfDishTextView;

        RestaurantDishes dishes;

        public void updateUI(RestaurantDishes dish){

            this.dishes = dish;

            plusDish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (amountOfDish < 10) { // TODO: Change max count
                        amountOfDish++;
                        amountOfDishTextView.setText(String.valueOf(amountOfDish));
                        if (!chosenDishList.isEmpty()){
                            for (Menu aud : chosenDishList) {
                                if (dishes.getId().equals(aud.getId())) {
                                    Log.d("SUCH DISH IS ALREADY", "EXISTS IN LIST " + dishes.getId() + " - " + aud.getId());
                                    aud.setDishCount(amountOfDish);
                                    break;
                                }
                                else {
                                    Menu m = new Menu();
                                    m.setId(dishes.getId());
                                    m.setDishCount(amountOfDish);
                                    chosenDishList.add(m);
                                    activity.chosenDishList.add(m);
                                    activity.chosenDishListDishNames.add(dishes);
                                    break;
                                }
                            }
                        }
                        else {
                            Menu m = new Menu();
                            m.setId(dishes.getId());
                            m.setDishCount(amountOfDish);
                            chosenDishList.add(m);
                            activity.chosenDishList.add(m);
                            activity.chosenDishListDishNames.add(dishes);
                        }

                     Log.d("chosenDishList ADD", chosenDishList.toString());
                    }
                }
            });
            minusDish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (amountOfDish != 0) {
                        amountOfDish--;
                        amountOfDishTextView.setText(String.valueOf(amountOfDish));

                        if (!chosenDishList.isEmpty()){
                            for (Menu aud : chosenDishList) {
                                if (dishes.getId().equals(aud.getId())) {
                                    if (aud.getDishCount() == 1) {
                                        chosenDishList.remove(aud);
                                        activity.chosenDishList.remove(aud);
                                        activity.chosenDishListDishNames.remove(dishes);
                                        break;
                                    }
                                    else {
                                        aud.setDishCount(amountOfDish);
                                        break;
                                    }
                                }
                            }
                        }
                        Log.d("chosenDishList REMOVE", chosenDishList.toString());
                    }
                    if (amountOfDish == 0){
                        amountOfDishTextView.setText(String.valueOf(amountOfDish));
                    }
                }
            });

        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            iv = itemView.findViewById(R.id.iv);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.dish_price);

            /* Select amount of dishes */
            minusDish = (CardView) itemView.findViewById(R.id.minus_dish);
            plusDish = (CardView) itemView.findViewById(R.id.plus_dish);
            amountOfDishTextView = (TextView) itemView.findViewById(R.id.dish_count);

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, RestaurantDishInformationPage.class);
            intent.putExtra("DishInformation", new Gson().toJson(dishes)); // посылаем все данные по выбранному блюду
            activity.startActivity(intent);
        }


    }
}
