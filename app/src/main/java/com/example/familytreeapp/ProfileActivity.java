package com.example.familytreeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    ImageView imgBack;
    CircleImageView imgProfile;
    TextView txtFirstName, txtDob;
    Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgBack = findViewById(R.id.imgBack);
        imgProfile = findViewById(R.id.imgProfile);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtDob = findViewById(R.id.txtDob);
        btnEdit = findViewById(R.id.btnEdit);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, AddNewMemberActivity.class);
                intent.putExtra("EditProfile", true);
                intent.putExtra("FirstName", txtFirstName.getText().toString());
                intent.putExtra("Dob", txtDob.getText().toString());
                startActivity(intent);
            }
        });
    }
}