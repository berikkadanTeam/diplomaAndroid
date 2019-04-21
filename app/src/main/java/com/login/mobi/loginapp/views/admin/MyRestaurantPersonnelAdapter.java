package com.login.mobi.loginapp.views.admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.personnel.Personnel;

import java.util.List;


public class MyRestaurantPersonnelAdapter extends RecyclerView.Adapter<MyRestaurantPersonnelAdapter.ViewHolder>{

    private Context context;
    private List<Personnel> list;

    public MyRestaurantPersonnelAdapter(Context context, List<Personnel> list){
        this.context = context;
        this.list = list;
    }

    // Чтобы при поиске отобразить новый список найденных сотрудников
    public void arrayChanged(List<Personnel> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyRestaurantPersonnelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.admin_menu_personnel_item, viewGroup, false);

        return new MyRestaurantPersonnelAdapter.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull MyRestaurantPersonnelAdapter.ViewHolder viewHolder, final int i) {
        Personnel personnel = list.get(i);

        viewHolder.surnameAndName.setText(personnel.getLastName() + " " + personnel.getFirstName());
        viewHolder.email.setText(personnel.getEmail());
        viewHolder.role.setText("ПОКА ТАКОЙ ИНФЫ НЕТ");
        viewHolder.phone.setText("ПОКА ТАКОЙ ИНФЫ НЕТ");

        // TODO place img accroding to role of personnel
//        switch(){
//            case 1:
//                viewHolder.statusExpecting.setVisibility(View.GONE);
//                viewHolder.statusConfirmed.setVisibility(View.VISIBLE);
//                viewHolder.statusRejected.setVisibility(View.GONE);
//                break;
//            case 2:
//                viewHolder.statusExpecting.setVisibility(View.GONE);
//                viewHolder.statusConfirmed.setVisibility(View.GONE);
//                viewHolder.statusRejected.setVisibility(View.VISIBLE);
//                break;
//            case 3:
//                viewHolder.statusExpecting.setVisibility(View.VISIBLE);
//                viewHolder.statusConfirmed.setVisibility(View.GONE);
//                viewHolder.statusRejected.setVisibility(View.GONE);
//                break;
//        }

        viewHolder.updateUI(personnel);

    }

    @Override
    public int getItemCount() {     return list.size();     }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView surnameAndName, email, role, phone;

        Personnel personnel;

        public void updateUI(Personnel personnel){
            this.personnel = personnel;
        }


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            surnameAndName = itemView.findViewById(R.id.surname_and_name);
            email = itemView.findViewById(R.id.email);
            role = itemView.findViewById(R.id.role);
            phone = itemView.findViewById(R.id.phone);

        }



    }
}
