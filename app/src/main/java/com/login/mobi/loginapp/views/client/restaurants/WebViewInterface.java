package com.login.mobi.loginapp.views.client.restaurants;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;


// CODE FROM HERE: https://stackoverflow.com/questions/40889152/is-it-possible-to-get-data-from-html-forms-into-android-while-using-webview/41034219

public class WebViewInterface {
    Context mContext;
    String data;

    WebViewInterface(Context ctx){
        this.mContext=ctx;
    }


    @JavascriptInterface
    public void showToast(String data) {  // В веб-приложении функция так называется Android.showToast();
        //Get the string value to process
        this.data = data;
        Log.d("TableID from WebView", data);
        Toast.makeText(mContext, "TableID from WebView in WebViewInterface" + data, Toast.LENGTH_LONG).show();
    }
}