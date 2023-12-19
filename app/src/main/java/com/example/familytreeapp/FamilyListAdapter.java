package com.example.familytreeapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class FamilyListAdapter extends RecyclerView.Adapter<FamilyListAdapter.FamilyListDataViewHolder> {

    //    private ArrayList<MyDbDataModelNotes> myDbDataModelNotesArrayList, searchList;
    Context context;
    ButtonClick buttonClick;
    private boolean editMode = false;


    public interface ButtonClick {
        void buttonClick(int position);
    }

    public void SetUpInterface(ButtonClick buttonClick) {
        this.buttonClick = buttonClick;
    }

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

        Tools.DisplayImage(context, holder.imgMember, "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddNewMemberActivity.class);
                intent.putExtra("EditMode", true);
                intent.putExtra("MemberName", holder.txtMemberName.getText().toString());
                intent.putExtra("MemberDob", holder.txtMemberDob.getText().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
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

        public FamilyListDataViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMember = itemView.findViewById(R.id.imgMember);
            txtMemberName = itemView.findViewById(R.id.txtMemberName);
            txtMemberDob = itemView.findViewById(R.id.txtMemberDob);
        }
    }
}
