package com.example.familytreeapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FamilyListFragment extends Fragment {

    RecyclerView recyclerViewFamilyList;
    SwipeRefreshLayout refreshLayout;
    FamilyListAdapter familyListAdapter;
    TextView txtNoData;
    EditText etvSearch;
    ImageView imgClose;
    MyDataBaseHandler myDataBaseHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_family_list, container, false);

        recyclerViewFamilyList = view.findViewById(R.id.recyclerViewFamilyList);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        txtNoData = view.findViewById(R.id.txtNoData);
        etvSearch = view.findViewById(R.id.etvSearch);
        imgClose = view.findViewById(R.id.imgClose);

        imgClose.setVisibility(View.GONE);
        txtNoData.setVisibility(View.GONE);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgClose.setVisibility(View.GONE);
                etvSearch.setText("");
            }
        });

        myDataBaseHandler = new MyDataBaseHandler(getContext());
        familyListAdapter = new FamilyListAdapter(getContext(), myDataBaseHandler.getAllMembers());

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (myDataBaseHandler.getAllMembers().size() > 0 && myDataBaseHandler.getAllMembers() != null) {
                    familyListAdapter = new FamilyListAdapter(getContext(), myDataBaseHandler.getAllMembers());
                    recyclerViewFamilyList.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerViewFamilyList.setAdapter(familyListAdapter);
                    txtNoData.setVisibility(View.GONE);
                } else {
                    txtNoData.setVisibility(View.VISIBLE);
                }
                refreshLayout.setRefreshing(false);
            }
        });

        if (myDataBaseHandler.getAllMembers().size() > 0 && myDataBaseHandler.getAllMembers() != null) {
            recyclerViewFamilyList.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewFamilyList.setAdapter(familyListAdapter);
            txtNoData.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }

        etvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (familyListAdapter != null) {
                    if (!etvSearch.getText().toString().isEmpty()) {
                        imgClose.setVisibility(View.VISIBLE);
                    } else {
                        imgClose.setVisibility(View.GONE);
                    }
                    familyListAdapter.search(charSequence, txtNoData, recyclerViewFamilyList);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (myDataBaseHandler.getAllMembers().size() > 0 && myDataBaseHandler.getAllMembers() != null) {
            familyListAdapter.updateData(myDataBaseHandler.getAllMembers());
            txtNoData.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }
    }
}