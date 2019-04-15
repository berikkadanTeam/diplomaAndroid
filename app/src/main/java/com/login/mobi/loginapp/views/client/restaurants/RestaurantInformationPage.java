package com.login.mobi.loginapp.views.client.restaurants;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.ApiInterface;
import com.login.mobi.loginapp.network.model.restaurantInformation.Image;
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
    String[] arr;
    String jsonData;
    private String phoneNumber, directionAddress;

    private List<Restaurant> list;
    private ArrayList<RestaurantInformation> restaurantDataList;
    private boolean open = true;

    // xml elements: texts, buttons
    private TextView name, address, cuisine, averageCheck, delivery, seats, description, noPhotosAvailable;
    private Spinner spinner;
    private LinearLayout bookBtn, callBtn, directionBtn;

    // xml elements: images
    private ImageView mainImageView;
    private RelativeLayout photoViewSection;        // section with photos list
    private ViewPager photoViewPager;               // photos in a row
    private int currentSection = 0;
    PhotoViewPagerAdapter photoViewPagerAdapter;    // adapter to open these photos when clicked on photo

    // xml elements: expandable listView
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListViewAdapter;
    private List<String> listDataGroup;
    private HashMap<String, List<String>> listDataChild;

    // Api
    private ApiInterface apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_information_page_test);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);

        // texts
        name = (TextView) findViewById(R.id.restaurant_page_name);
        address = (TextView) findViewById(R.id.restaurant_page_location);
        cuisine = (TextView) findViewById(R.id.restaurant_page_cuisines);
        averageCheck = (TextView) findViewById(R.id.restaurant_page_average_check);
        delivery = (TextView) findViewById(R.id.restaurant_page_delivery);
        seats = (TextView) findViewById(R.id.restaurant_page_seats);
        description = (TextView) findViewById(R.id.restaurant_page_description);
        noPhotosAvailable = (TextView) findViewById(R.id.no_photos_available);

        // buttons
        bookBtn = findViewById(R.id.call_to_action_button);
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantInformationPage.this, RestaurantWebViewPage.class); // == startActivity(new Intent(RestaurantInformationPage.this, RestaurantWebViewPage.class));
                intent.putExtra("RestaurantData", jsonData);
                startActivity(intent);
                //startActivity(new Intent(RestaurantInformationPage.this, RestaurantTableBookingPage.class));   убирала для теста, чтобы постоянно не проходить через WebView
            }
        });

        callBtn = (LinearLayout) findViewById(R.id.call_button);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askPermissionToCall();
            }
        });
        directionBtn = (LinearLayout) findViewById(R.id.direction_button);
        directionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + directionAddress);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

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


        /* Получение данных ресторана с фрагмента Restaurant */
        Intent intent = getIntent();
        jsonData = intent.getStringExtra("restaurantData");
        Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
        Log.d("RestaurantData", jsonData);


        /* Вписывание данных ресторана в поля */
        restaurantId = restaurant.getId();
        Log.d("RestaurantID", restaurantId);
        phoneNumber = restaurant.getNumber();
        directionAddress = restaurant.getAddres();
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

        /* Вписывание данных для времени работы */
        List<WorkDay> workDayList = restaurant.getWorkDay();
        arr = new String[workDayList.size()];
        for (int i=0; i<workDayList.size(); i++){
            arr[i] = workDayList.get(i).getDayName() + " " + workDayList.get(i).getStartTime() + " - " + workDayList.get(i).getEndTime();
        }
        Log.d("WorkDay", Arrays.deepToString(arr));
        expandableListView = findViewById(R.id.expandableListView);
        initExpandableListViewListeners();
        initExpandableListViewObjects();
        initExpandableListViewData();


        /* Вставка фото */
        String filePath = "http://5.23.55.101/Files/";  // berikkadan.kz домен просрочен
        String fileName = restaurant.getFileName();
        String image = filePath.concat(fileName);
        Glide.with(this).load(image).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).apply(new RequestOptions().error(R.drawable.photo_no_photo).diskCacheStrategy(DiskCacheStrategy.ALL)).into(mainImageView);

        List<Image> imagesList = restaurant.getImages();

        // добавляю в list картинки, которые находятся в image в response
        ArrayList<String> list = new ArrayList<String>();
        if (restaurant.getImages().size() == 0){
            noPhotosAvailable.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < imagesList.size(); i++) {
            list.add(filePath.concat(imagesList.get(i).getName()));
        }
        // пока что для примера я добавляю в list картинку которая главная, позже заменить на
        //ArrayList<String> list = new ArrayList<String>();
        //list.add(image);
        //list.add(image);
        photoViewPagerAdapter = new PhotoViewPagerAdapter(getSupportFragmentManager(), list);
        RestaurantImagesAdapter imagesAdapter = new RestaurantImagesAdapter(list);
        RecyclerView imagesRecyclerView = (RecyclerView) findViewById(R.id.images_recycler_view);
        imagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imagesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        imagesRecyclerView.setAdapter(imagesAdapter);
        imagesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                photoViewSection.setVisibility(View.VISIBLE);
                photoViewPager.setAdapter(photoViewPagerAdapter);
                photoViewPager.setCurrentItem(position);
                currentSection = 1;
            }
        }));


/*  SPINNER WITH WORKDAY SCHEDULE
        spinner = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.restaurant_work_time_spinner_item, arr){
            @Override
            public boolean isEnabled(int position){
                if (position < 1 && position > 7)
                    return true;
                else
                    return false;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position==1) {
                    // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.restaurant_work_time_spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
*/


        //fetchRestaurantData();

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

    private void initExpandableListViewListeners() {
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                //Toast.makeText(getApplicationContext(), listDataGroup.get(groupPosition) + " : " + listDataChild.get(listDataGroup.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //Toast.makeText(getApplicationContext(),listDataGroup.get(groupPosition) + " Expanded ", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setExpandableListViewHeight(parent, groupPosition);

//                if (parent.isGroupExpanded(groupPosition)) {
//                    ImageView imageView = v.findViewById(R.id.expandable_icon);
//                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_up));
//                } else {
//                    ImageView imageView = v.findViewById(R.id.expandable_icon);
//                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_down));
//                }

    // Способ, где использовали layout_weight="1" для группы, делали groupIndicator=transparent и с помощью boolean меняем стрелки
//                if (open) {
//                    ImageView imageView = v.findViewById(R.id.expandable_icon);
//                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_up));
//                } else {
//                    ImageView imageView = v.findViewById(R.id.expandable_icon);
//                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_down));
//                }
//                open = !open;
                return false;
            }
        });
    }

    private void initExpandableListViewObjects() {
        listDataGroup = new ArrayList<>();
        listDataChild = new HashMap<>();
        expandableListViewAdapter = new ExpandableListAdapter(this, listDataGroup, listDataChild);
        expandableListView.setAdapter(expandableListViewAdapter);
    }

    private void initExpandableListViewData() {
        listDataGroup.add("Время работы:");
        List<String> workSchedule = new ArrayList<>();
        for (String item : arr) {
            workSchedule.add(item);
        }
        listDataChild.put(listDataGroup.get(0), workSchedule);
        expandableListViewAdapter.notifyDataSetChanged();
    }


    /*  Функция, чтобы высчитать размер expandableListView, без нее он не расширяется вниз */
    private void setExpandableListViewHeight(ExpandableListView listView, int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    /* Функция, чтобы переместить иконку expandableListView вправо */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        expandableListView.setIndicatorBounds(expandableListView.getRight()- 40, expandableListView.getWidth());
    }



    /* Фукция, чтобы позвонить в ресторан */
    public void askPermissionToCall(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CALL_PHONE}, 169);

            } else {
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CALL_PHONE}, 169);
            }
        } else {
            callPhoneNumber(phoneNumber);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 169: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhoneNumber(phoneNumber);
                } else {
                    dialPhoneNumber(phoneNumber);
                }
                return;
            }
        }
    }

    public void callPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intent);
        }
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /* PhotoViewPager Adapter */
    public class PhotoViewPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<String> imageList;

        public PhotoViewPagerAdapter(FragmentManager fm, ArrayList<String> imageList) {
            super(fm);
            this.imageList = imageList;
        }

        @Override
        public Fragment getItem(int position) {
            return PhotoViewFragment.newInstance(imageList.get(position));
        }

        @Override
        public int getCount() {
            return imageList.size();
        }
    }

}
