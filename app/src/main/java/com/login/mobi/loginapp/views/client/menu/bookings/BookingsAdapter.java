package com.login.mobi.loginapp.views.client.menu.bookings;

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
import com.login.mobi.loginapp.network.model.booking.MyBookings;

import java.util.List;


public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder>{

    private Context context;
    private List<MyBookings> list;

    BookingsAdapter(Context context, List<MyBookings> list){
        this.context = context;
        this.list = list;
    }

    // Чтобы при поиске отобразить новый список найденных бронирований
    public void arrayChanged(List<MyBookings> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.menu_bookings_item, viewGroup, false);

        return new BookingsAdapter.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull BookingsAdapter.ViewHolder viewHolder, final int i) {
        MyBookings booking = list.get(i);

        viewHolder.bookingNumber.setText("Бронирование №" + booking.getNumberOfBooking() + " в " + booking.getName());
        viewHolder.dateAndTime.setText(booking.getGetDate() + " в " + booking.getTime());
        //Log.d("BOOKING.MENU", String.valueOf(booking.getMenu().size()));
        if (booking.getMenu().size() > 0 || booking.getMenu().isEmpty() == false) {    // booking.getMenu() != null из-за этого, когда menu пустой, выходило "Есть"
            viewHolder.hasPreorderOrNot.setText("Есть");
        } else {
            viewHolder.hasPreorderOrNot.setText("Нет");
        }

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
        //else
            //viewHolder.status.setText("Не подтверждено");

        viewHolder.updateUI(booking);

    }

    @Override
    public int getItemCount() {     return list.size();     }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView bookingNumber, dateAndTime, hasPreorderOrNot, statusConfirmed, statusRejected, statusExpecting;
        private LinearLayout removePreorderDishBtn;

        MyBookings booking;

        public void updateUI(MyBookings booking){
            this.booking = booking;
        }


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            bookingNumber = itemView.findViewById(R.id.booking_number);
            dateAndTime = itemView.findViewById(R.id.booking_date_and_time);
            hasPreorderOrNot = itemView.findViewById(R.id.booking_preorder);
            statusConfirmed = itemView.findViewById(R.id.booking_status_confirmed);
            statusRejected = itemView.findViewById(R.id.booking_status_rejected);
            statusExpecting = itemView.findViewById(R.id.booking_status_expecting);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, BookingDetailsPage.class);
            intent.putExtra("BookingData", new Gson().toJson(booking)); // посылаем все данные по выбранному бронированию
            context.startActivity(intent);
        }
    }
}
