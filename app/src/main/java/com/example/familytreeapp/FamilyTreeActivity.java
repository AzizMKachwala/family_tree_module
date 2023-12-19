package com.example.familytreeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FamilyTreeActivity extends AppCompatActivity {

    ImageView imgHome, imgUserProfile;
    ViewPager2 viewPager2;
    TabLayout tabMode;
    FloatingActionButton btnFloatingAdd;
    FamilyListFragment familyListFragment;
    FamilyTreeFragment familyTreeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_tree);

        imgHome = findViewById(R.id.imgHome);
        imgUserProfile = findViewById(R.id.imgUserProfile);
        viewPager2 = findViewById(R.id.viewPager2);
        tabMode = findViewById(R.id.tabMode);
        btnFloatingAdd = findViewById(R.id.btnFloatingAdd);

        familyListFragment = new FamilyListFragment();
        familyTreeFragment = new FamilyTreeFragment();

        viewPager2.setAdapter(new MVPAdapter(FamilyTreeActivity.this));

        new TabLayoutMediator(tabMode, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0) {
                    tab.setText("Family List");
                } else if (position == 1) {
                    tab.setText("Family Tree");
                }
            }
        }).attach();

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnFloatingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FamilyTreeActivity.this, AddNewMemberActivity.class));
            }
        });

    }

    private class MVPAdapter extends FragmentStateAdapter {

        public MVPAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 1:
                    return new FamilyTreeFragment();
                case 0:
                default:
                    return new FamilyListFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}