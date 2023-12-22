package com.example.familytreeapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FamilyListAdapter extends RecyclerView.Adapter<FamilyListAdapter.FamilyListDataViewHolder> {

    private List<MyDbDataModelFamily> myDbDataModelFamilyArrayList, searchList;
    Context context;
    MyDataBaseHandler myDataBaseHandler;

    private boolean editMode = false;

    public FamilyListAdapter(Context context, ArrayList<MyDbDataModelFamily> myDbDataModelFamilyArrayList) {
        this.myDbDataModelFamilyArrayList = myDbDataModelFamilyArrayList;
        this.searchList = myDbDataModelFamilyArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public FamilyListDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.family_list_item, parent, false);
        return new FamilyListDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyListDataViewHolder holder, @SuppressLint("RecyclerView") int position) {

        myDataBaseHandler = new MyDataBaseHandler(holder.itemView.getContext());
        holder.txtMemberName.setSelected(true);
        holder.txtMemberName.setText(searchList.get(position).getUserName());
        holder.txtMemberDob.setText(searchList.get(position).getUserDob());

        Glide.with(context).load(searchList.get(position).getUserImage()).into(holder.imgMember);

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddNewMemberActivity.class);
                intent.putExtra("EditMode", true);
                intent.putExtra("MemberId", searchList.get(position).getUserId());
                intent.putExtra("MemberName", searchList.get(position).getUserName());
                intent.putExtra("MemberDob", searchList.get(position).getUserDob());
                intent.putExtra("MemberImage", searchList.get(position).getUserImage());
                intent.putExtra("MemberParentId", searchList.get(position).getUserParentId());
                context.startActivity(intent);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are You Sure You Want To Delete This Node Along With its Child Nodes ?");
                builder.setCancelable(false);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Delete Node API
                        myDataBaseHandler.deleteMemberById(myDataBaseHandler.getAllMembers().get(position).getUserId());
                        updateData(myDataBaseHandler.getAllMembers());
                        Toast.makeText(context, "Delete Toast", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (searchList.size() > 0 && searchList != null) {
            return searchList.size();
        } else {
            return 0;
        }
    }

    public void updateData(ArrayList<MyDbDataModelFamily> myDbDataModelFamilies) {
        if (myDbDataModelFamilies != null && myDbDataModelFamilies.size() > 0) {
            myDbDataModelFamilyArrayList = myDbDataModelFamilies;
            searchList = myDbDataModelFamilies;
            notifyDataSetChanged();
        }

    }

    public void search(CharSequence charSequence, TextView txtNoData, RecyclerView recyclerViewFamilyList) {

        String charString = charSequence.toString().trim();
        if (charString.isEmpty()) {
            searchList = myDbDataModelFamilyArrayList;
            recyclerViewFamilyList.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
        } else {
            int Flag = 0;
            List<MyDbDataModelFamily> list = new ArrayList<>();
            for (MyDbDataModelFamily single : myDbDataModelFamilyArrayList) {
                if (single.getUserName().toLowerCase().contains(charString.toLowerCase())) {
                    list.add(single);
                    Flag = 1;
                }
            }
            if (Flag == 1) {
                myDbDataModelFamilyArrayList = list;
                recyclerViewFamilyList.setVisibility(View.VISIBLE);
                txtNoData.setVisibility(View.GONE);
            } else {
                recyclerViewFamilyList.setVisibility(View.GONE);
                txtNoData.setVisibility(View.VISIBLE);
            }
        }
        notifyDataSetChanged();
    }

    public static class FamilyListDataViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imgMember;
        TextView txtMemberName, txtMemberDob;
        ImageView imgEdit, imgDelete;

        public FamilyListDataViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMember = itemView.findViewById(R.id.imgMember);
            txtMemberName = itemView.findViewById(R.id.txtMemberName);
            txtMemberDob = itemView.findViewById(R.id.txtMemberDob);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
