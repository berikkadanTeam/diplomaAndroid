package com.login.mobi.loginapp.views.kitchen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.views.admin.MyRestaurantBookingsPage;
import com.login.mobi.loginapp.views.admin.MyRestaurantInformationPage;
import com.login.mobi.loginapp.views.client.menu.profile.ProfilePage;
import com.login.mobi.loginapp.views.waiter.orders.OrdersPage;

import java.util.ArrayList;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyHolder> {

    private ArrayList<MenuItem> menuItems;
    private Context context;


    public MenuAdapter(Context c, ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
        this.context = c;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.menu_item,parent,false);
        return new MyHolder(v);
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        MenuItem s = menuItems.get(position);

        holder.menuItemName.setText(s.getName());
        holder.menuItemIcon.setImageResource(s.getImage());

    }


    @Override
    public int getItemCount() {
        return menuItems.size();
    }




    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener { // implements View.OnClickListener
        TextView menuItemName;
        ImageView menuItemIcon;

        public MyHolder(View itemView) {
            super(itemView);

            menuItemName = (TextView) itemView.findViewById(R.id.menu_item_textView);
            menuItemIcon = (ImageView) itemView.findViewById(R.id.menu_item_imageView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            switch (getLayoutPosition()){
                case 0:
                    view.getContext().startActivity(new Intent(view.getContext(), ProfilePage.class));
                    break;
                case 1:
                    view.getContext().startActivity(new Intent(view.getContext(), MyRestaurantInformationPage.class));
                    break;
                case 2:
                    view.getContext().startActivity(new Intent(view.getContext(), MyRestaurantBookingsPage.class));
                    break;
                case 3:
                    view.getContext().startActivity(new Intent(view.getContext(), OrdersPage.class));
                    break;
            }
//          if (getLayoutPosition()==0){  view.getContext().startActivity(new Intent(view.getContext(), ProfilePage.class));   }
//          else if(getLayoutPosition()==1){  view.getContext().startActivity(new Intent(view.getContext(), BookingsPage.class));     }
        }

    }



}