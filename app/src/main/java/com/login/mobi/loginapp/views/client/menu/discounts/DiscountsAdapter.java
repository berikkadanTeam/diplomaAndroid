package com.login.mobi.loginapp.views.client.menu.discounts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.views.client.menu.MenuItem;

import java.util.ArrayList;


public class DiscountsAdapter extends RecyclerView.Adapter<DiscountsAdapter.ViewHolder>{

    private Context context;
    //private List<MyOrders> list;
    private ArrayList<MenuItem> menuItems;

    DiscountsAdapter(Context context, ArrayList<MenuItem> menuItems){ //List<MyOrders> list){
        this.context = context;
        this.menuItems = menuItems;
    }

    // Чтобы при поиске отобразить новый список найденных заказов
    public void arrayChanged(ArrayList<MenuItem> menuItems){ //List<MyOrders> list){
        this.menuItems = menuItems;
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
        //MyOrders order = list.get(i);
        MenuItem s = menuItems.get(i);
        Log.d("lala", s.toString());
        //viewHolder.orderNumber.setText("Заказ №" + s.getName());

//        if (booking.getMenu() != null || !booking.getMenu().isEmpty())
//            viewHolder.hasPreorderOrNot.setText("Есть");
//        else
//            viewHolder.hasPreorderOrNot.setText("Нет");
//        viewHolder.status.setText("ПОКА ТАКОЙ ИНФЫ НЕТ");

        viewHolder.updateUI(s); //order);

    }

    @Override
    public int getItemCount() {     return menuItems.size();     }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView orderNumber, tableNumber, waiter, status;
        private LinearLayout removePreorderDishBtn;

        //MyOrders order;
        MenuItem ss;
//        public void updateUI(MyOrders order){
//            this.order = order;
//        }
public void updateUI(MenuItem menuItems){
    this.ss = menuItems;
}


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            orderNumber = itemView.findViewById(R.id.order_number);



        }


        @Override
        public void onClick(View v) {
            //Intent intent = new Intent(context, BookingDetailsPage.class);
            //intent.putExtra("OrderData", new Gson().toJson(order)); // посылаем все данные по выбранному бронированию
            //context.startActivity(intent);
        }
    }
}
