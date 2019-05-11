package com.login.mobi.loginapp.views.client.menu.discounts;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.discounts.Discount;

public class DiscountDetailsPage extends AppCompatActivity{

    // xml elements: recyclerView, buttons
    private ImageView iv;
    private ProgressBar progressBar;
    private TextView name, description, date, descriptionSectionTitle;

    // variables
    String jsonData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_dish_information_page);

        /* Получение данных ресторана с фрагмента Restaurant */
        Intent intent = getIntent();
        jsonData = intent.getStringExtra("DiscountData");
        Discount discount = new Gson().fromJson(jsonData, Discount.class);
        Log.d("DiscountData", jsonData);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        iv = (ImageView) findViewById(R.id.dish_photo);
        String filePath = "http://5.23.55.101/Files/"; // berikkadan.kz домен просрочен
        //Glide.with(this).load(filePath).into(iv);
        if (discount.getFileName() != null) {
            String image = filePath.concat(discount.getFileName());
            Glide.with(this).load(image).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).apply(new RequestOptions().error(R.drawable.photo_food).diskCacheStrategy(DiskCacheStrategy.ALL)).into(iv);
        } else
            Glide.with(this).load(R.drawable.photo_food).into(iv);


        name = (TextView) findViewById(R.id.dish_name);
        name.setText(discount.getTitle());
        date = (TextView) findViewById(R.id.dish_type);
        date.setText("С " + discount.getFromDateEx() + " до " + discount.getToDateEx());
        descriptionSectionTitle = (TextView) findViewById(R.id.description);
        descriptionSectionTitle.setText("Описание акции");
        description = (TextView) findViewById(R.id.dish_composition);
        description.setText(discount.getDescription());
    }

}
