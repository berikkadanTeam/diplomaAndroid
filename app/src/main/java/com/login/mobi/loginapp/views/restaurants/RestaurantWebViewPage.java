package com.login.mobi.loginapp.views.restaurants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.login.mobi.loginapp.R;


public class RestaurantWebViewPage extends AppCompatActivity {
    WebView webView;
    private LinearLayout bookingDetailsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_web_view_page);

        webView = findViewById(R.id.webView);
        //webView.loadUrl("https://www.google.kz");


        Log.d("fun", "onCreate: ");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebViewInterface(this), "Android");


        webView.loadUrl("file:///android_asset/android.html");




        bookingDetailsBtn = findViewById(R.id.call_to_action_button);
        bookingDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RestaurantWebViewPage.this, RestaurantTableBookingPage.class));
            }
        });
    }
}
