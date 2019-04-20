package com.login.mobi.loginapp.views.admin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.booking.MyRestaurantBookings;

import java.util.List;


public class MyRestaurantBookingsAdapter extends RecyclerView.Adapter<MyRestaurantBookingsAdapter.ViewHolder>{

    private Context context;
    private List<MyRestaurantBookings> list;

    public MyRestaurantBookingsAdapter(Context context, List<MyRestaurantBookings> list){
        this.context = context;
        this.list = list;
    }

    // Чтобы при поиске отобразить новый список найденных бронирований
    public void arrayChanged(List<MyRestaurantBookings> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyRestaurantBookingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.menu_bookings_item, viewGroup, false);

        return new MyRestaurantBookingsAdapter.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull MyRestaurantBookingsAdapter.ViewHolder viewHolder, final int i) {
        MyRestaurantBookings booking = list.get(i);

        viewHolder.bookingNumber.setText("Бронирование №" + booking.getNumberOfBooking());
        viewHolder.dateAndTime.setText(booking.getGetDate() + " в " + booking.getTime());
        if (booking.getMenu().size() > 0 || booking.getMenu().isEmpty() == false)   // booking.getMenu() != null из-за этого, когда menu пустой, выходило "Есть"
            viewHolder.hasPreorderOrNot.setText("Есть");
        else
            viewHolder.hasPreorderOrNot.setText("Нет");

        switch(booking.getReserveStatus()){
            case 1:
                viewHolder.statusExpecting.setVisibility(View.GONE);
                viewHolder.statusConfirmed.setVisibility(View.VISIBLE);
                viewHolder.statusRejected.setVisibility(View.GONE);
                break;
            case 2:
                viewHolder.statusExpecting.setVisibility(View.GONE);
                viewHolder.statusConfirmed.setVisibility(View.GONE);
                viewHolder.statusRejected.setVisibility(View.VISIBLE);
                break;
            case 3:
                viewHolder.statusExpecting.setVisibility(View.VISIBLE);
                viewHolder.statusConfirmed.setVisibility(View.GONE);
                viewHolder.statusRejected.setVisibility(View.GONE);
                break;
        }

        viewHolder.updateUI(booking);

    }

    @Override
    public int getItemCount() {     return list.size();     }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView bookingNumber, dateAndTime, hasPreorderOrNot, statusConfirmed, statusRejected, statusExpecting;
        private LinearLayout removePreorderDishBtn;

        MyRestaurantBookings booking;

        public void updateUI(MyRestaurantBookings booking){
            this.booking = booking;
        }


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            bookingNumber = itemView.findViewById(R.id.booking_number);
            dateAndTime = itemView.findViewById(R.id.booking_date_and_time);
            hasPreorderOrNot = itemView.findViewById(R.id.booking_preorder);
            //status = itemView.findViewById(R.id.booking_status);
            statusConfirmed = itemView.findViewById(R.id.booking_status_confirmed);
            statusRejected = itemView.findViewById(R.id.booking_status_rejected);
            statusExpecting = itemView.findViewById(R.id.booking_status_expecting);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, MyRestaurantBookingDetailsPage.class);
            intent.putExtra("BookingData", new Gson().toJson(booking)); // посылаем все данные по выбранному бронированию
            context.startActivity(intent);
        }
    }
}
