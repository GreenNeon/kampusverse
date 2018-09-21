package com.kampusverse.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.kampusverse.R;

public class Camera extends AppCompatActivity {

    private android.hardware.Camera mCamera=null;
    private CameraView mCameraView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        try {
            mCamera= android.hardware.Camera.open();
        }
        catch (Exception e)
        {
            Log.e("ERROR","Failed to get Camera"+e.getMessage());
        }

        if (mCamera!=null){
            mCameraView=new CameraView(this,mCamera);
            FrameLayout camera_view=(FrameLayout) findViewById(R.id.FLCamera);
            camera_view.addView(mCameraView);
        }

        ImageButton imgClose=(ImageButton)findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
}