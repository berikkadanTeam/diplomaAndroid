package com.login.mobi.loginapp.views.order;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Version1_QrCodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    // Snackbar
    View parentLayout;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        // Programmatically initialize the scanner view
        mScannerView = new ZXingScannerView(this);
        parentLayout = findViewById(android.R.id.content);
        // Set the scanner view as the content view
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register ourselves as a handler for scan results.
        mScannerView.setResultHandler(this);
        // Start camera on resume
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop camera on pause
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        // Prints scan results
        Log.d("result", rawResult.getText());
        // Prints the scan format (qrcode, pdf417 etc.)
        Log.d("result", rawResult.getBarcodeFormat().toString());
        //If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
        Intent intent = new Intent();
        intent.putExtra("KEY", rawResult.getText());
        setResult(RESULT_OK, intent);
        Snackbar.make(parentLayout, rawResult.getText(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        //finish();
    }
}