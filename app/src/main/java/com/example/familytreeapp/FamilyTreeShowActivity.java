package com.example.familytreeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.otaliastudios.zoom.ZoomLayout;

public class FamilyTreeShowActivity extends AppCompatActivity {

    RecyclerView recyclerViewNode;
    ZoomLayout zoomLayout;
    NodeAdapter nodeAdapter;
    ImageView imgBack;
    MyDataBaseHandler myDataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_tree_show);

        recyclerViewNode = findViewById(R.id.recyclerViewNode);
        zoomLayout = findViewById(R.id.zoomLayout);
        imgBack = findViewById(R.id.imgBack);

        zoomLayout.setMinZoom(1.0f);
        zoomLayout.setMaxZoom(5.0f);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        myDataBaseHandler = new MyDataBaseHandler(FamilyTreeShowActivity.this);
        nodeAdapter = new NodeAdapter(FamilyTreeShowActivity.this, myDataBaseHandler.getAllMembers());
        recyclerViewNode.setLayoutManager(new LinearLayoutManager(FamilyTreeShowActivity.this));
        recyclerViewNode.setAdapter(nodeAdapter);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}