package com.example.tugrulbayrak.fennerv1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button powerButton;
    private Camera cam1=null;
    Camera.Parameters params;
    private boolean isOn=false;
    static final Integer CAMERA = 0x5;
    ConstraintLayout layout1;
    TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        powerButton =(Button)findViewById(R.id.button);
        layout1 = (ConstraintLayout) findViewById(R.id.layout1);
        txt = (TextView) findViewById(R.id.testFlash);
        powerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                askForPermission(Manifest.permission.CAMERA,CAMERA);

                cam1 = Camera.open();


                if(isOn) {
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    cam1.setParameters(params);
                    cam1.stopPreview();
                    isOn=false;
                    powerButton.setBackgroundResource(R.drawable.poweroff);
                    layout1.setBackgroundColor(Color.parseColor("#ffffbb33"));
                    txt.setTextColor(Color.parseColor("#ffcc0000"));
                    Toast.makeText(MainActivity.this, "Flash OFF!", Toast.LENGTH_SHORT).show();

                }
                else {

                    params=cam1.getParameters();
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    cam1.setParameters(params);
                    cam1.startPreview();
                    isOn=true;
                    powerButton.setBackgroundResource(R.drawable.poweron);
                    layout1.setBackgroundColor(Color.parseColor("#ffcc0000"));
                    txt.setTextColor(Color.parseColor("#ffffbb33"));
                    Toast.makeText(MainActivity.this, "Flash ON!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {

        }
    }



}
