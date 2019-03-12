package com.login.mobi.loginapp.views.menu.bookings;

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

        viewHolder.bookingNumber.setText("Бронирование №" + booking.getId() + " в " + booking.getName());
        viewHolder.dateAndTime.setText(booking.getDate() + " в " + booking.getTime());
        if (booking.getMenu() != null || !booking.getMenu().isEmpty())
            viewHolder.hasPreorderOrNot.setText("Есть");
        else
            viewHolder.hasPreorderOrNot.setText("Нет");
        viewHolder.status.setText("ПОКА ТАКОЙ ИНФЫ НЕТ");

        viewHolder.updateUI(booking);

    }

    @Override
    public int getItemCount() {     return list.size();     }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView bookingNumber, dateAndTime, hasPreorderOrNot, status;
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
            status = itemView.findViewById(R.id.booking_status);


        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, BookingDetailsPage.class);
            intent.putExtra("BookingData", new Gson().toJson(booking)); // посылаем все данные по выбранному бронированию
            context.startActivity(intent);
        }
    }
}
