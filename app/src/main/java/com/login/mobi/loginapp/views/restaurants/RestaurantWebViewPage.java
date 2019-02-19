package com.login.mobi.loginapp.views.restaurants;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.network.model.restaurants.Restaurant;


public class RestaurantWebViewPage extends AppCompatActivity {
    private WebView webView;
    private LinearLayout bookingDetailsBtn;

    private String tableID;
    private ProgressDialog progressDialog;
    private String restaurantID;
    private String jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_web_view_page);

        /* Получение данных ресторана с RestaurantInformationPage */
        Intent intent = getIntent();
        jsonData = intent.getStringExtra("RestaurantData");
        Restaurant restaurant = new Gson().fromJson(jsonData, Restaurant.class);
        restaurantID = restaurant.getId();

        webView = findViewById(R.id.webView);
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //webView.addJavascriptInterface(new WebViewInterface(this), "Android");        // старый способ через WebViewInterface класс
        webView.addJavascriptInterface(this, "Android");


        progressDialog = new ProgressDialog(RestaurantWebViewPage.this);
        progressDialog.setMessage("Идёт загрузка схемы ресторана...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        //webView.loadUrl("file:///android_asset/android.html");                                                или "http://berikkadan.kz"
        //webView.loadUrl("http://www.berikkadan.kz/#/restaurantid/f64782ee-a4aa-41ed-adfa-a9358bb8a437");
        //webView.loadUrl("http://www.berikkadan.kz/#/restaurantid/" + restaurantID);                           Домен просрочился
        webView.loadUrl("http://5.23.55.101/#/restaurantid/" + restaurantID);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });




        bookingDetailsBtn = findViewById(R.id.call_to_action_button);
        bookingDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tableID != null) {
                    Intent intent = new Intent(RestaurantWebViewPage.this, RestaurantTableBookingPage.class);
                    intent.putExtra("TableID", tableID);
                    intent.putExtra("RestaurantData", jsonData);
                    startActivity(intent);
                    //startActivity(new Intent(RestaurantWebViewPage.this, RestaurantTableBookingPage.class));
                }
                else {
                    //Toast.makeText(getApplicationContext(), "ITS NULL" + tableID, Toast.LENGTH_LONG).show();
                    //showAlertWithOneButton(v);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RestaurantWebViewPage.this);
                    alertDialogBuilder.setIcon(R.drawable.icon_error);
                    alertDialogBuilder.setTitle("Выберите стол для бронирования");
                    //alertDialogBuilder.setMessage("Вы не можете перейти дальше, так как не выбрали стол для бронирования");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("ОК",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            //RestaurantWebViewPage.this.finish();      // close current activity
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

    }


    @JavascriptInterface
    public void showToast(String data) {                // В веб-приложении функция так называется Android.showToast();
        tableID = data;
        Log.d("TableID from WebView", data);
        //Toast.makeText(this, "TableID from WEBVIEW: " + tableID, Toast.LENGTH_LONG).show();
    }



}



