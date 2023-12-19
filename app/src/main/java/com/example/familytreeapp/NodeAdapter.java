package com.example.familytreeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.NodeDataViewHolder> {

    Context context;

    public NodeAdapter(Context context) {
        this.context = context;
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
        holder.txtName.setText("Aziz");
        holder.txtDob.setText("2001/04/24");
    }

    @Override
    public int getItemCount() {
        return 6;
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
