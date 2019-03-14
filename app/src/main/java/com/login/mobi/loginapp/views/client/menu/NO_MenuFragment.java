package com.login.mobi.loginapp.views.client.menu;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.login.mobi.loginapp.R;

import java.util.ArrayList;
import java.util.HashMap;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.order_fragment, container, false);
//        return rootView;
//      }


// from:     https://camposha.info/source/android-listfragment-images-text-source
public class NO_MenuFragment extends ListFragment {

    private String[] menuItemNames = {"Мой профиль", "Мои бронирования", "Настройки"};
    private int[] menuItemImages = {R.drawable.icon_profile_rounded, R.drawable.icon_clock, R.drawable.icon_calendar};
    private RecyclerView rv;

    ArrayList<HashMap<String, String>> data=new ArrayList<HashMap<String,String>>();
    SimpleAdapter adapter;

    public NO_MenuFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //MAP
        HashMap<String, String> map = new HashMap<String, String>();
        for(int i = 0; i < menuItemNames.length; i++) {
            map = new HashMap<String, String>();
            map.put("Menu Item", menuItemNames[i]);
            map.put("Image", Integer.toString(menuItemImages[i]));

            data.add(map);
        }


        String[] from = {"Menu Item", "Image"};
        int[] to = {R.id.menu_item_textView, R.id.menu_item_imageView};



        //adapter = new SimpleAdapter(getActivity(), data, R.layout.version_1_menu, from, to);
        //setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), data.get(pos).get("Menu Item"), Toast.LENGTH_SHORT).show();

            }
        });
    }


}