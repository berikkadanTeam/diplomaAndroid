package com.login.mobi.loginapp.views.client.restaurants;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;
import com.login.mobi.loginapp.network.requests.restaurants.GetRestaurants;

import java.util.List;


public class RestaurantFragment extends Fragment implements GetRestaurants.GetRestaurantsInterface,
                                                            AdapterView.OnItemSelectedListener{

    private RecyclerView rv;
    private RestaurantAdapter adapter;

    private Spinner filterSpinner;
    private ImageButton filterBtn;

    public RestaurantFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.restaurant_fragment, container, false);

        rv = rootView.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        //setHasOptionsMenu(true);
        filterSpinner = (Spinner) rootView.findViewById(R.id.filterSpinner);
        filterBtn = (ImageButton) rootView.findViewById(R.id.filterBtn);

        GetRestaurants getRestaurants = new GetRestaurants(this);
        getRestaurants.getRestaurants();


        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //filterSpinner.performClick();
                Toast.makeText(getContext(),"search button is Clicked", Toast.LENGTH_LONG).show();
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.restaurant_filter_options, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Applying the adapter to our spinner
                filterSpinner.setAdapter(adapter);
                //filterSpinner.setOnItemSelectedListener(this);
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            }
        });

        return rootView;
    }
    


    @Override
    public void getRestaurants(List<Restaurant> response) {
        if(response != null){
            Toast.makeText(getContext(),"Restaurants displayed (name + description)", Toast.LENGTH_LONG).show();

//            String[] arrayOfRestaurants = new String[response.size()];
//            for (int i = 0; i < response.size(); i++) {
//                Restaurant element = response.get(i);
//                arrayOfRestaurants[i] = response.get(i).getName();
//                //response.get(i).getName();
//            }

            adapter = new RestaurantAdapter(getContext(), response);
            rv.setAdapter(adapter);
        }else{
            Toast.makeText(getContext(),"NULL", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        switch (selectedItem) {
            case "Select one Item":
                break;
            case "Samsung":
                Toast.makeText(getContext(), selectedItem + " in Korean means 3 Stars!", Toast.LENGTH_SHORT).show();
                break;
            case "Foxconn":
                Toast.makeText(getContext(), selectedItem + " is world's largest contract electronics manufacturer", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}
