package com.example.familytreeapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class FamilyListAdapter extends RecyclerView.Adapter<FamilyListAdapter.FamilyListDataViewHolder> {

    //    private ArrayList<MyDbDataModelNotes> myDbDataModelNotesArrayList, searchList;
    Context context;
    private boolean editMode = false;

    public FamilyListAdapter(Context context) {
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
        holder.txtMemberName.setText("Aziz");
        holder.txtMemberDob.setText("2001/04/24");

        Glide.with(context).load(R.drawable.photo_final).into(holder.imgMember);

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddNewMemberActivity.class);
                intent.putExtra("EditMode", true);
                intent.putExtra("MemberName", holder.txtMemberName.getText().toString());
                intent.putExtra("MemberDob", holder.txtMemberDob.getText().toString());
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
        return 10;
    }

    public void search(CharSequence charSequence, TextView txtNoData, RecyclerView recyclerViewFamilyList) {

        String charString = charSequence.toString().trim();
        if (charString.isEmpty()) {
            recyclerViewFamilyList.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
        } else {

        }
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
