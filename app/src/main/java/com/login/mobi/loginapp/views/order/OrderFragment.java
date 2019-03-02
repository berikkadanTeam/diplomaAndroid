package com.login.mobi.loginapp.views.order;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.login.mobi.loginapp.R;
import com.login.mobi.loginapp.views.menu.profile.ProfilePage;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
//import me.dm7.barcodescanner.zxing.ZXingScannerView;



// Camera permission from: https://stackoverflow.com/questions/9028735/getting-camera-error-in-zxing-barcode-application/9028986
public class OrderFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private static final int PERMISSION_REQUEST_CAMERA = 1;

    private TextView scanDataTextView;
    private Button startScanButton;
    View parentLayout;

    public OrderFragment(){}


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        View rootView = inflater.inflate(R.layout.welcome_page, container, false);
//
//        return rootView;
//    }


    TextView IDEditText;
//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_start_scanning, container, false);
        IDEditText = (TextView) rootView.findViewById(R.id.start_scanning_text_scan_data);
        Button scanBarcode = (Button) rootView.findViewById(R.id.start_scanning_button_start_scan);

        parentLayout = getActivity().findViewById(android.R.id.content);

        mScannerView = new ZXingScannerView(getActivity());
        // Set the scanner view as the content view
        //setContentView(mScannerView);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            Toast.makeText(getActivity(),"camera permission granted",Toast.LENGTH_LONG).show();


        } else {
            // Permission is missing and must be requested.
            requestCameraPermission();
        }


        return mScannerView;
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
        mScannerView.stopCamera();
    }

    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with cda button to request the missing permission.
            Toast.makeText(getActivity(), "Camera access is required to Scan The Barcode.",
                    Toast.LENGTH_LONG).show();


            // Request the permission
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);



        } else {
            Toast.makeText(getActivity(),
                    "<b>Camera could not be opened.</b>\\nThis occurs when the camera is not available (for example it is already in use) or if the system has denied access (for example when camera access has been disabled).", Toast.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        // Prints scan results
        Log.d("result", rawResult.getText());
        // Prints the scan format (qrcode, pdf417 etc.)
        Log.d("result", rawResult.getBarcodeFormat().toString());
        //If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
        Intent intent = new Intent();
        intent.putExtra("KEY", rawResult.getText());
        Toast.makeText(getActivity(), rawResult.getText(), Toast.LENGTH_LONG).show();
        getActivity().setResult(Activity.RESULT_OK, intent);
        //getActivity().finish();
        startActivity(new Intent(getActivity(), ProfilePage.class));
    }


}
