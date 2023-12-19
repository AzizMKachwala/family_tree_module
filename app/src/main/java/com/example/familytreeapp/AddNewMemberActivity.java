package com.example.familytreeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddNewMemberActivity extends BaseClass {

    CircleImageView imgProfile;
    EditText etvFirstName, etvFamilyName, etvFatherName, etvMotherName, etvSpouseName, etvDob;
    Button btnAdd;
    ImageView imgBack, imgEdit;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;
    private static final int CAMERA_PERMISSION_REQUEST = 101;
    Bitmap imageBitmap;
    DatePickerFragment datePickerFragment;
    String profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_member);

        imgProfile = findViewById(R.id.imgProfile);
        etvFirstName = findViewById(R.id.etvFirstName);
        etvFamilyName = findViewById(R.id.etvFamilyName);
        etvFatherName = findViewById(R.id.etvFatherName);
        etvMotherName = findViewById(R.id.etvMotherName);
        etvSpouseName = findViewById(R.id.etvSpouseName);
        etvDob = findViewById(R.id.etvDob);
        btnAdd = findViewById(R.id.btnAdd);
        imgBack = findViewById(R.id.imgBack);
        imgEdit = findViewById(R.id.imgEdit);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (checkAndRequestPermission(AddNewMemberActivity.this)) {
                        openImageDialog(AddNewMemberActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (checkAndRequestPermission(AddNewMemberActivity.this)) {
                        openImageDialog(AddNewMemberActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        etvDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerFragment = new DatePickerFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                datePickerFragment.show(fragmentTransaction, "#tag");
                datePickerFragment.setCancelable(false);

                datePickerFragment.setUpInterface(new DatePickerFragment.ButtonClick() {
                    @Override
                    public void saveClick(String date, String day, String month, String year) {
                        etvDob.setText(date);
                    }
                });
            }
        });

        boolean editMode = getIntent().getBooleanExtra("EditMode", false);

        if (editMode) {
            btnAdd.setText("Edit Member");
            etvFirstName.setText(getIntent().getStringExtra("MemberName"));
            etvDob.setText(getIntent().getStringExtra("MemberDob"));
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etvFirstName.getText().toString().isEmpty()) {
                    etvFirstName.setError("Enter First Name");
                    etvFirstName.requestFocus();
                } else if (etvFatherName.getText().toString().isEmpty()) {
                    etvFatherName.setError("Enter Father's Name");
                    etvFatherName.requestFocus();
                } else if (etvFamilyName.getText().toString().isEmpty()) {
                    etvFamilyName.setError("Enter Family Name");
                    etvFamilyName.requestFocus();
                } else if (etvMotherName.getText().toString().isEmpty()) {
                    etvMotherName.setError("Enter Mother's Name");
                    etvMotherName.requestFocus();
                } else if (etvDob.getText().toString().isEmpty()) {
                    Toast.makeText(AddNewMemberActivity.this, "Select Date Of Birth", Toast.LENGTH_SHORT).show();
                } else {
                    //Add Data to the Database
                    Toast.makeText(AddNewMemberActivity.this, etvFirstName.getText().toString() + " "
                            + etvFatherName.getText().toString() + " "
                            + etvFamilyName.getText().toString() + " "
                            + etvMotherName.getText().toString() + " "
                            + etvSpouseName.getText().toString() + " "
                            + etvDob.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            assert selectedImage != null;
            profileImage = selectedImage.toString();
            Tools.DisplayImage(AddNewMemberActivity.this, imgProfile, profileImage);

            // Convert the selected image URI to a Bitmap
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                imageBitmap = (Bitmap) extras.get("data");
                // Convert the Bitmap to a URI
                assert imageBitmap != null;
                Uri imageUri = bitmapToUri(AddNewMemberActivity.this, imageBitmap);
                profileImage = imageUri.toString();
                Tools.DisplayImage(this, imgProfile, profileImage);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    boolean cameraPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean mediaImagesPermissionGranted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraPermissionGranted && mediaImagesPermissionGranted) {
                        openImageDialog(AddNewMemberActivity.this);
                    } else {
                        if (!cameraPermissionGranted) {
                            Toast.makeText(AddNewMemberActivity.this, "Please grant permission to access the camera.", Toast.LENGTH_SHORT).show();
                        }
                        if (!mediaImagesPermissionGranted) {
                            Toast.makeText(AddNewMemberActivity.this, "Please grant permission to access media images.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    boolean cameraPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean externalStoragePermissionGranted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraPermissionGranted && externalStoragePermissionGranted) {
                        openImageDialog(AddNewMemberActivity.this);
                    } else {
                        if (!cameraPermissionGranted) {
                            Toast.makeText(AddNewMemberActivity.this, "Please grant permission to access media images.", Toast.LENGTH_SHORT).show();
                        }
                        if (!externalStoragePermissionGranted) {
                            Toast.makeText(AddNewMemberActivity.this, "Please grant permission to access external storage.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}