package com.example.tugrulbayrak.fennerv1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    Button powerButton;
    private Camera cam1 = null;
    Camera.Parameters params;
    private boolean isOn = false;
    static final int CAMERA =1;
    ConstraintLayout layout1;
    TextView txt;
    private AdView adView;
    AdRequest adRequest;
    int MyVersion;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();



        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA);
        }
        else {
            allCameraSituations();
        }

    }




    public void init() {

        powerButton = (Button) findViewById(R.id.button);
        layout1 = (ConstraintLayout) findViewById(R.id.layout1);
        txt = (TextView) findViewById(R.id.testFlash);
        adView = (AdView) findViewById(R.id.reklam);
        adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("B041E6C01582277D0CAF3334DD2A2B5B")
                .build();
        adView.loadAd(adRequest);
        MyVersion = Build.VERSION.SDK_INT;
    }


    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    allCameraSituations();

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    public void allCameraSituations(){



        cam1 = Camera.open();

        powerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                if (isOn) {
                    params = cam1.getParameters();
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    cam1.setParameters(params);
                    cam1.stopPreview();
                    isOn = false;
                    powerButton.setBackgroundResource(R.drawable.poweroff);
                    layout1.setBackgroundColor(Color.parseColor("#ffffbb33"));
                    txt.setTextColor(Color.parseColor("#ffcc0000"));
                    Toast.makeText(MainActivity.this, "Flash OFF!", Toast.LENGTH_SHORT).show();
                } else {

                    params = cam1.getParameters();
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    cam1.setParameters(params);
                    cam1.startPreview();
                    isOn = true;
                    powerButton.setBackgroundResource(R.drawable.poweron);
                    layout1.setBackgroundColor(Color.parseColor("#ffcc0000"));
                    txt.setTextColor(Color.parseColor("#ffffbb33"));
                    Toast.makeText(MainActivity.this, "Flash ON!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}










