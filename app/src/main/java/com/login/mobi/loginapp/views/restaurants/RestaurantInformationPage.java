package com.login.mobi.loginapp.views.restaurants;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.model.restaurantInformation.RestaurantInformation;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;
import com.login.mobi.loginapp.network.model.restaurants.WorkDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class RestaurantInformationPage extends AppCompatActivity {  //implements GetRestaurants.GetRestaurantsInterface, GetRestaurantInformation.GetRestaurantInformationInterface {

    // variables
    String restaurantId;

    private List<Restaurant> list;
    private ArrayList<RestaurantInformation> restaurantDataList;

    // xml elements: texts
    private TextView name, address, cuisine, averageCheck, delivery, seats, description;
    private Spinner spinner;

    // xml elements: images
    private ImageView mainImageView;
    private RelativeLayout photoViewSection; // section with photos list
    private ViewPager photoViewPager;
    private int currentSection = 0;

    // Api
    private ApiInterface apiService;


    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListViewAdapter;
    private List<String> listDataGroup;
    private HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_information_page_test);

        // texts
        name = (TextView) findViewById(R.id.restaurant_page_name);
        address = (TextView) findViewById(R.id.restaurant_page_location);
        cuisine = (TextView) findViewById(R.id.restaurant_page_cuisines);
        averageCheck = (TextView) findViewById(R.id.restaurant_page_average_check);
        delivery = (TextView) findViewById(R.id.restaurant_page_delivery);
        seats = (TextView) findViewById(R.id.restaurant_page_seats);
        description = (TextView) findViewById(R.id.restaurant_page_description);
        spinner = (Spinner) findViewById(R.id.spinner);


        // images
        mainImageView = (ImageView) findViewById(R.id.restaurant_page_image);
        photoViewSection = (RelativeLayout) findViewById(R.id.photo_view_section);
        photoViewPager = (ViewPager) findViewById(R.id.image_view_pager);
        ImageView photoViewCloseButton = (ImageView) findViewById(R.id.photo_view_section_close_button);
        photoViewSection.setVisibility(View.GONE);
        photoViewCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoViewSection.setVisibility(View.GONE);
                currentSection = 0;
            }
        });

        Intent intent = getIntent();
        String jsonData = intent.getStringExtra("restaurantData");
        //Tvoi dannyi
        Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
        Log.d("RestaurantData", jsonData);

        restaurantId = restaurant.getId();
        name.setText(restaurant.getName());
        address.setText(restaurant.getAddres() + "\n" + restaurant.getCity());
        cuisine.setText(restaurant.getKitchen());
        averageCheck.setText(Integer.toString(restaurant.getAvgCheck()));
        seats.setText(Integer.toString(restaurant.getSeats()));
        description.setText(restaurant.getDescription());
        boolean hasDelivery = restaurant.getDelivery();
        if (hasDelivery)
            delivery.setText("Есть");
        else
            delivery.setText("Нет");
        String filePath = "http://berikkadan.kz/Files/";
        String fileName = restaurant.getFileName();
        String image = filePath.concat(fileName);
        Glide.with(this).load(image).into(mainImageView);

        // пока что для примера я добавляю в list картинку которая главная
        ArrayList<String> list = new ArrayList<String>();
        list.add(image);
        list.add(image);
        RestaurantImagesAdapter imagesAdapter = new RestaurantImagesAdapter(list);
        RecyclerView imagesRecyclerView = (RecyclerView) findViewById(R.id.images_recycler_view);
        imagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imagesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        imagesRecyclerView.setAdapter(imagesAdapter);
        imagesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                photoViewSection.setVisibility(View.VISIBLE);
                //photoViewPager.setAdapter(photoViewPagerAdapter);
                photoViewPager.setCurrentItem(position);
                currentSection = 1;
            }
        }));


        List<WorkDay> workDayList = restaurant.getWorkDay();
        String[] arr = new String[workDayList.size()];
        for (int i=0; i<workDayList.size(); i++){
            arr[i] = workDayList.get(i).getDayName() + " " + workDayList.get(i).getStartTime() + " - " + workDayList.get(i).getEndTime();
        }
        Log.d("workday", Arrays.deepToString(arr));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.restaurant_work_time_spinner_item, arr){
            @Override
            public boolean isEnabled(int position){
                if (position < 1 && position > 7)
                    return true;
                else
                    return false;
            }



//            @Override
//            public View getDropDownView(int position, View convertView, ViewGroup parent) {
//                View view = super.getDropDownView(position, convertView, parent);
//                TextView tv = (TextView) view;
//                if(position==1) {
//                    // Set the disable item text color
//                    tv.setTextColor(Color.GRAY);
//                }
//                else {
//                    tv.setTextColor(Color.BLACK);
//                }
//                return view;
//            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.restaurant_work_time_spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);



        expandableListView = findViewById(R.id.expandableListView);
        // initializing the listeners
        initListeners();
        // initializing the objects
        initObjects();
        // preparing list data
        initListData();


        //fetchRestaurantData();
        //name.setText(list.get(restaurantId).getName());
        //name.setText(restaurantId);


//        GetRestaurantInformation getRestaurantInformation = new GetRestaurantInformation(this);
//        getRestaurantInformation.getRestaurantInformation();
    }



    // Эти два метода уже не нужны, потому что передали всю информацию через intent

//    @Override
//    public void getRestaurantInformation(List<RestaurantInformation> response) {
//        if(response != null){
//            Toast.makeText(this,"Info response not NULL", Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(this,"NULL", Toast.LENGTH_LONG).show();
//        }
//    }


//    private void fetchRestaurantData(){
//        Call<RestaurantInformation> call = apiService.getRestaurantInformation(restaurantId);
//        call.enqueue(new Callback<RestaurantInformation>() {
//            @Override
//            public void onResponse(Call<RestaurantInformation> call, Response<RestaurantInformation> response) {
//                if (response.isSuccessful()) {
//                    restaurantDataList = new ArrayList<>();
//                    //restaurantDataList = response.body().getSpotList();
//                    //String dateToFormat = response.body().getTime();
//                    Log.e("info","info" + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RestaurantInformation> call, Throwable t) {
//                Log.e("info","Error Fetching Spots from DO:" + t);
//            }
//        });
//    }

    private void initListeners() {
        // ExpandableListView on child click listener
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), listDataGroup.get(groupPosition)
                                + " : "
                                + listDataChild.get(listDataGroup.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // ExpandableListView Group expanded listener
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),listDataGroup.get(groupPosition) + " Expanded ", Toast.LENGTH_SHORT).show();
            }
        });

        // ExpandableListView Group collapsed listener
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),listDataGroup.get(groupPosition) + " Collapsed ", Toast.LENGTH_SHORT).show();

            }
        });
    }

        private void initObjects() {
            // initializing the list of groups
            listDataGroup = new ArrayList<>();
            // initializing the list of child
            listDataChild = new HashMap<>();
            // initializing the adapter object
            expandableListViewAdapter = new ExpandableListAdapter(this, listDataGroup, listDataChild);
            // setting list adapter
            expandableListView.setAdapter(expandableListViewAdapter);
        }


        private void initListData() {
            // Adding group data
            listDataGroup.add("TEST");
            listDataGroup.add("TEST 2");

            // array of strings
            String[] array;

            // list of alcohol
            List<String> alcoholList = new ArrayList<>();
            array = getResources().getStringArray(R.array.restaurant_filter_options);
            for (String item : array) {
                alcoholList.add(item);
            }

            // Adding child data
            listDataChild.put(listDataGroup.get(0), alcoholList);
            listDataChild.put(listDataGroup.get(1), alcoholList);

            // notify the adapter
            expandableListViewAdapter.notifyDataSetChanged();
        }

}
