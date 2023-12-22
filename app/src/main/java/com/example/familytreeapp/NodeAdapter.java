package com.example.familytreeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.NodeDataViewHolder> {

    Context context;
    private ArrayList<MyDbDataModelFamily> myDbDataModelFamilyArrayList, searchList;
    MyDataBaseHandler myDataBaseHandler;

    public NodeAdapter(Context context, ArrayList<MyDbDataModelFamily> myDbDataModelFamilyArrayList) {
        this.context = context;
        this.myDbDataModelFamilyArrayList = myDbDataModelFamilyArrayList;
        this.searchList = myDbDataModelFamilyArrayList;
    }

    @NonNull
    @Override
    public NodeDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.node_item, parent, false);
        return new NodeDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NodeDataViewHolder holder, int position) {

        myDataBaseHandler = new MyDataBaseHandler(holder.itemView.getContext());
        holder.txtName.setText(searchList.get(position).getUserName());
        holder.txtDob.setText(searchList.get(position).getUserDob());
        Tools.DisplayImage(context, holder.imgProfile, searchList.get(position).getUserImage());
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public static class NodeDataViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtDob;
        ImageView imgProfile;

        public NodeDataViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtDob = itemView.findViewById(R.id.txtDob);
            imgProfile = itemView.findViewById(R.id.imgProfile);
        }
    }
}
