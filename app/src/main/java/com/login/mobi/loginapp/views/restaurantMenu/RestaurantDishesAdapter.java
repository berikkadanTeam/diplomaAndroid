package com.login.mobi.loginapp.views.restaurantMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.booking.Menu;
import com.login.mobi.loginapp.network.model.restaurantMenu.RestaurantDishes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RestaurantDishesAdapter extends RecyclerView.Adapter<RestaurantDishesAdapter.ViewHolder>{

    private Context context;
    private List<RestaurantDishes> list;
    private int dishTypeID;
    private List<Menu> chosenDishList = new ArrayList<>();//new CopyOnWriteArrayList<>();
    AppCompatActivity activity;

    RestaurantDishesAdapter(Context context, List<RestaurantDishes> list, int dishTypeID){
        this.context = context;
        this.list = list;
        this.dishTypeID = dishTypeID;


    }
//    private RestaurantDishesPage mainActivity;
//    public RestaurantDishesAdapter(RestaurantDishesPage activity){
//        this.mainActivity = activity;
//    }

//    final View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
//    LinearLayout btn = rootView.findViewById(R.id.open_preorder_menu_total);
//    LinearLayout b = (LinearLayout) getView().findViewById(R.id.button1);
//    btn.setVisibility(View.VISIBLE);



    public List<Menu> getSelected(){
        return chosenDishList;

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

        public void updateUI(RestaurantDishes restaurant){

            this.dishes = restaurant;
            //final Menu m = new Menu();

            plusDish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (amountOfDish < 10) { // TODO: Change max count
                        amountOfDish++;
                        amountOfDishTextView.setText(String.valueOf(amountOfDish));
//                        mainActivity.displayButton(amountOfDish);
                        if (!chosenDishList.isEmpty()){
                            for (Menu aud : chosenDishList) {
                                if (dishes.getId().equals(aud.getId())) {
                                    Log.d("DISHHSHSHHSSHHS", "EQUALLLLLLLLL" + dishes.getId() + " - " + aud.getId());
                                    aud.setDishCount(amountOfDish);
                                    break;
                                }
                                else {
                                    Menu m = new Menu();
                                    m.setId(dishes.getId());
                                    m.setDishCount(amountOfDish);
                                    chosenDishList.add(m);
                                    break;
                                }
                            }
//                            Iterator<Menu> iter = chosenDishList.iterator();
//                            while(iter.hasNext()){
//                                Menu str = iter.next();
//                                if( dishes.getId().equals(str.getId())){
//                                    iter.remove();
//                                    Menu m = new Menu();
//                                    m.setId(dishes.getId());
//                                    m.setDishCount(amountOfDish);
//                                    chosenDishList.add(m);
//                                    break;
//                                }
//                                else{
//                                    Menu m = new Menu();
//                                    m.setId(dishes.getId());
//                                    m.setDishCount(amountOfDish);
//                                    chosenDishList.add(m);
//                                    break;
//                                }
//                            }
                        }
                        else {
                            Menu m = new Menu();
                            m.setId(dishes.getId());
                            m.setDishCount(amountOfDish);
                            chosenDishList.add(m);
                        }



                     Log.d("chosenDishList22222ADD", chosenDishList.toString());
//                        for (int i = 0; i < chosenDishList.size(); i++) {
//                            Log.d("chosenDishList111111", chosenDishList.get(i).toString() + "\n");
//                        }
                        //Log.d("chosenDishList", Arrays.toString(chosenDishList.toArray()));
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
                                        break;
                                    }
                                    else {
                                        aud.setDishCount(amountOfDish);
                                        break;
                                    }
                                }
                            }

                        }
                        Log.d("chosenDishList22222REMOVE", chosenDishList.toString());

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
            Intent intent = new Intent(context, RestaurantDishInformationPage.class);
            intent.putExtra("DishInformation", new Gson().toJson(dishes)); // посылаем все данные по выбранному блюду
            context.startActivity(intent);

        }

//        public void putList(){
//            Gson gson = new Gson();
//            String fs = gson.toJson(chosenDishList);
//        }
//
//        public void getList(){
//            Gson gson = new Gson();
//            chosenDishList = gson.fromJson(getIntent().getStringExtra(""), new TypeToken<List<Menu>>(){}.getType());
//        }


    }
}
