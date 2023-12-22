package com.example.familytreeapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FamilyTreeFragment extends Fragment {

    Button btnGenerateTree;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_family_tree, container, false);

        btnGenerateTree = view.findViewById(R.id.btnGenerateTree);

        btnGenerateTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FamilyTreeShowActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}