package com.login.mobi.loginapp.views.client.order;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


// Camera permission from: https://stackoverflow.com/questions/9028735/getting-camera-error-in-zxing-barcode-application/9028986

public class OrderFragment extends Fragment implements ZXingScannerView.ResultHandler {

    // QR Code
    private ZXingScannerView mScannerView;
    private static final int PERMISSION_REQUEST_CAMERA = 1;

    // Snackbar
    View parentLayout;

    public OrderFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentLayout = getActivity().findViewById(android.R.id.content);

        mScannerView = new ZXingScannerView(getActivity());
//        setContentView(mScannerView);  // Set the scanner view as the content view
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(),"Доступ к камере для сканирования QR кода разрешен",Toast.LENGTH_LONG).show();
            //Snackbar.make(parentLayout, "Доступ к камере для сканирования QR кода разрешен", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        } else {
            requestCameraPermission();
        }


        return mScannerView;
    }



    @Override
    public void onResume() {
        super.onResume();
        // Register ourselves as a handler for scan results.
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted and the user would benefit from additional context for the use of the permission.
            Toast.makeText(getActivity(), "Необходимо разрешить доступ к камере для сканирования QR кода", Toast.LENGTH_LONG).show();
            //Snackbar.make(parentLayout, "Необходимо разрешить доступ к камере для сканирования QR кода", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Request the permission
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                }
            }, 2800);

        } else {
            Toast.makeText(getActivity(),
                    "<b>Camera could not be opened.</b>\\nThis occurs when the camera is not available (for example it is already in use) or if the system has denied access (for example when camera access has been disabled).", Toast.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.d("result", rawResult.getText());   // Prints the scan format (qrcode, pdf417 etc.)
        Log.d("result", rawResult.getBarcodeFormat().toString());
        //If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
        Intent intent = new Intent();
        intent.putExtra("KEY", rawResult.getText());
        Toast.makeText(getActivity(), rawResult.getText(), Toast.LENGTH_LONG).show();

        getActivity().setResult(Activity.RESULT_OK, intent);
        //getActivity().finish();
        startActivity(new Intent(getActivity(), TabsMainActivity.class));
    }


}
