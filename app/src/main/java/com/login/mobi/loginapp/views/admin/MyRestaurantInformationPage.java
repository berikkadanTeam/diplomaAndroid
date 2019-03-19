package com.login.mobi.loginapp.views.admin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurantInformation.RestaurantInformation;
import com.login.mobi.loginapp.network.model.restaurantInformation.WorkDay;
import com.login.mobi.loginapp.network.requests.restaurantInformation.GetRestaurantInformation;
import com.login.mobi.loginapp.singleton.SingletonSharedPref;
import com.login.mobi.loginapp.views.client.restaurants.ExpandableListAdapter;
import com.login.mobi.loginapp.views.client.restaurants.PhotoViewFragment;
import com.login.mobi.loginapp.views.client.restaurants.RecyclerItemClickListener;
import com.login.mobi.loginapp.views.client.restaurants.RestaurantImagesAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MyRestaurantInformationPage extends AppCompatActivity implements GetRestaurantInformation.GetRestaurantInformationInterface{

    // variables
    String[] arr;
    private String phoneNumber, directionAddress;
    RestaurantInformation restaurant = new RestaurantInformation();

    // xml elements: texts, buttons
    private TextView name, address, cuisine, averageCheck, delivery, seats, description;
    private LinearLayout callBtn, directionBtn;

    // xml elements: images
    private ImageView mainImageView;
    private RelativeLayout photoViewSection;        // section with photos list
    private ViewPager photoViewPager;               // photos in a row
    private int currentSection = 0;
    MyRestaurantInformationPage.PhotoViewPagerAdapter photoViewPagerAdapter;    // adapter to open these photos when clicked on photo

    // xml elements: expandable listView
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListViewAdapter;
    private List<String> listDataGroup;
    private HashMap<String, List<String>> listDataChild;

    // Shared Preferences
    SingletonSharedPref sharedPref;
    private String userID, token;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_information_page_test);

        progressDialog = new ProgressDialog(this, R.style.ProgressDialogInCenter);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.show();

        sharedPref = SingletonSharedPref.getInstance(this);
        userID = sharedPref.getString(SingletonSharedPref.USER_ID);
        token = sharedPref.getString(SingletonSharedPref.TOKEN);
        GetRestaurantInformation getRestaurantInformation = new GetRestaurantInformation(this, userID, "Bearer " + token);
        getRestaurantInformation.getRestaurantInformation();

        // texts
        name = (TextView) findViewById(R.id.restaurant_page_name);
        address = (TextView) findViewById(R.id.restaurant_page_location);
        cuisine = (TextView) findViewById(R.id.restaurant_page_cuisines);
        averageCheck = (TextView) findViewById(R.id.restaurant_page_average_check);
        delivery = (TextView) findViewById(R.id.restaurant_page_delivery);
        seats = (TextView) findViewById(R.id.restaurant_page_seats);
        description = (TextView) findViewById(R.id.restaurant_page_description);


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


        /* Вписывание данных ресторана в поля */
        phoneNumber = restaurant.getNumber();
        directionAddress = restaurant.getAddres();
        name.setText(restaurant.getName());
        address.setText(restaurant.getAddres() + "\n" + restaurant.getCity());
        cuisine.setText(restaurant.getKitchen());
        //Log.d("AVG CHECK", Integer.toString(restaurant.getAvgCheck()));
        //averageCheck.setText(Integer.toString(restaurant.getAvgCheck()));
        //seats.setText(Integer.toString(restaurant.getSeats()));
        description.setText(restaurant.getDescription());
//        boolean hasDelivery = restaurant.getDelivery();
//        if (hasDelivery)
//            delivery.setText("Есть");
//        else
//            delivery.setText("Нет");

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
        Glide.with(this).load(image).into(mainImageView);

        // пока что для примера я добавляю в list картинку которая главная, позже заменить на
        ArrayList<String> list = new ArrayList<String>();
        list.add(image);
        list.add(image);
        photoViewPagerAdapter = new MyRestaurantInformationPage.PhotoViewPagerAdapter(getSupportFragmentManager(), list);
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


    }



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
    private class PhotoViewPagerAdapter extends FragmentStatePagerAdapter {
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


    @Override
    public void getRestaurantInformation(RestaurantInformation response) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if(response != null){
            Toast.makeText(this,"Info response not NULL", Toast.LENGTH_LONG).show();
            restaurant = response;
            Log.d("AVG CHECK RES", Integer.toString(response.getAvgCheck()));
        }else{
            Toast.makeText(this,"NULL", Toast.LENGTH_LONG).show();
        }
    }

}
