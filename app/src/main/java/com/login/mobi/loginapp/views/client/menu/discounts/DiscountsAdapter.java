package com.login.mobi.loginapp.views.client.menu.discounts;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;


public class DiscountsAdapter extends RecyclerView.Adapter<DiscountsAdapter.ViewHolder>{

    private Context context;
    private List<Discount> list;

    DiscountsAdapter(Context context, List<Discount> list){
        this.context = context;
        this.list = list;
    }

    // Чтобы при поиске отобразить новый список найденных акций
    public void arrayChanged(List<Discount> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiscountsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.menu_discounts_item, viewGroup, false);

        return new DiscountsAdapter.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull DiscountsAdapter.ViewHolder viewHolder, final int i) {
        Discount discount = list.get(i);
        Log.d("1 Discount", discount.toString());
        viewHolder.name.setText(discount.getTitle());
        viewHolder.description.setText(discount.getDescription());
        viewHolder.date.setText("С " + discount.getFromDate() + " до " + discount.getToDate());

        String filePath = "http://5.23.55.101/Files/";  // berikkadan.kz домен просрочен
        String fileName = discount.getFileName();
        if (fileName != null) {
            String image = filePath.concat(fileName);
            Glide.with(viewHolder.iv.getContext()).load(image).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    viewHolder.progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    viewHolder.progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).apply(new RequestOptions().error(R.drawable.photo_food).diskCacheStrategy(DiskCacheStrategy.ALL)).into(viewHolder.iv);
        } else
            Glide.with(viewHolder.iv.getContext()).load(R.drawable.photo_food).into(viewHolder.iv);

        viewHolder.updateUI(discount);

    }

    @Override
    public int getItemCount() {     return list.size();     }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView iv;
        private ProgressBar progressBar;
        private TextView name, description, date;

        Discount discount;

        public void updateUI(Discount discount){
    this.discount = discount;
}


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            iv = itemView.findViewById(R.id.iv);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date_from_and_to);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, DiscountDetailsPage.class);
            intent.putExtra("DiscountData", new Gson().toJson(discount)); // посылаем все данные по выбранной акции
            context.startActivity(intent);
        }
    }
}
