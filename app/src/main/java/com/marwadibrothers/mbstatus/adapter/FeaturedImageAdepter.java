package com.marwadibrothers.mbstatus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.activity.BannerEditingActivity;
import com.marwadibrothers.mbstatus.models.home.FearuredImageData;
import com.marwadibrothers.mbstatus.utils.Config;

import java.util.ArrayList;
import java.util.List;

public class FeaturedImageAdepter extends RecyclerView.Adapter<FeaturedImageAdepter.ViewHolder>{

    private final int width;
    Context context;
    int layoutFile;
    List<FearuredImageData> fearuredImageList;
    // RecyclerView recyclerView;
    public FeaturedImageAdepter(Context context, int layoutFile,List<FearuredImageData> fearuredImageList) {
        this.context = context;
        this.layoutFile = layoutFile;
        this.fearuredImageList = fearuredImageList;

        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        WindowManager wmanager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wmanager.getDefaultDisplay().getMetrics(displaymetrics);
        width = (int) (displaymetrics.widthPixels*0.15);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(layoutFile, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(context).load(Config.IMG_PATH + fearuredImageList.get(position).getProduct_image()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, BannerEditingActivity.class)
                        .putExtra(Config.IMAGE_URL, Config.IMG_PATH + fearuredImageList.get(position).getProduct_image())
                        .putExtra(Config.SUB_CAT_ID_1, fearuredImageList.get(position).getSub_category_id())
                        .putExtra(Config.PRODUCT_ID, fearuredImageList.get(position).getProduct_id()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return fearuredImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtRawSubCat;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
