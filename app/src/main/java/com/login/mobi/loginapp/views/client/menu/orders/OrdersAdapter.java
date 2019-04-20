package com.login.mobi.loginapp.views.client.menu.orders;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.order.MyOrders;

import java.util.List;


public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder>{

    private Context context;
    private List<MyOrders> list;

    OrdersAdapter(Context context, List<MyOrders> list){
        this.context = context;
        this.list = list;
    }

    // Чтобы при поиске отобразить новый список найденных заказов
    public void arrayChanged(List<MyOrders> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.menu_orders_item, viewGroup, false);

        return new OrdersAdapter.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder viewHolder, final int i) {
        MyOrders order = list.get(i);

        viewHolder.orderNumber.setText("Заказ №" + order.getNumberOfOrder());
        viewHolder.tableNumber.setText("ПОКА ТАКОЙ ИНФЫ НЕТ");
        viewHolder.waiter.setText("ПОКА ТАКОЙ ИНФЫ НЕТ");
        viewHolder.status.setText("ПОКА ТАКОЙ ИНФЫ НЕТ");
//        if (booking.getMenu() != null || !booking.getMenu().isEmpty())
//            viewHolder.hasPreorderOrNot.setText("Есть");
//        else
//            viewHolder.hasPreorderOrNot.setText("Нет");
//        viewHolder.status.setText("ПОКА ТАКОЙ ИНФЫ НЕТ");

        viewHolder.updateUI(order);

    }

    @Override
    public int getItemCount() {     return list.size();     }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView orderNumber, tableNumber, waiter, status;
        private LinearLayout removePreorderDishBtn;

        MyOrders order;

        public void updateUI(MyOrders order){
            this.order = order;
        }


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            orderNumber = itemView.findViewById(R.id.order_number);
            tableNumber = itemView.findViewById(R.id.order_table_number);
            waiter = itemView.findViewById(R.id.order_waiter);
            status = itemView.findViewById(R.id.order_status);


        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, OrderDetailsPage.class);
            intent.putExtra("OrderData", new Gson().toJson(order)); // посылаем все данные по выбранному заказу
            Log.d("SENDED OrderData", order.toString());
            context.startActivity(intent);
        }
    }
}
