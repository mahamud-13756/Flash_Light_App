package com.example.flash_light_app;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {
    ImageButton btn;
    boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn_torch);

        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                runFlashlight();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this, "Camera permission required.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();


    }

    private void runFlashlight() {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!state)
                {
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try {
                        String cameraId = cameraManager.getCameraIdList()[0];
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            cameraManager.setTorchMode(cameraId, true);
                        }
                        state = true;
                        btn.setImageResource(R.drawable.img_1);
                    }
                    catch (CameraAccessException e)
                    {}
                }
                else
                {
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try {
                        String cameraId = cameraManager.getCameraIdList()[0];
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            cameraManager.setTorchMode(cameraId, false);
                        }
                        state = false;
                        btn.setImageResource(R.drawable.img);
                    }
                    catch (CameraAccessException e)
                    {}
                }
            }
        });
    }
}