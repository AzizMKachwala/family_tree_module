package com.example.familytreeapp;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class Tools extends AppCompatActivity {

    Context context;
    private final Dialog dialog;

    public Tools(Context context) {
        this.context = context;
        dialog = new Dialog(context);
    }

    public void showLoading() {
        try {
            if (dialog != null) {
                dialog.setContentView(R.layout.loadingdialog);
                dialog.setCancelable(false);
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopLoading() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void DisplayImage(Context context, ImageView img, String urlImg) {
        Glide.with(context)
                .load(urlImg)
                .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground))
                .into(img);
    }

}
