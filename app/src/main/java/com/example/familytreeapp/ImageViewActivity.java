package com.example.familytreeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class ImageViewActivity extends AppCompatActivity {

    ImageView imgProfilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        imgProfilePhoto = findViewById(R.id.imgProfilePhoto);

        Tools.DisplayImage(ImageViewActivity.this,imgProfilePhoto,getIntent().getStringExtra("profileImage"));
    }
}