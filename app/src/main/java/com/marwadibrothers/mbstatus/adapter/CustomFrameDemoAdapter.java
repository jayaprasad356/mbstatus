package com.marwadibrothers.mbstatus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.models.customFrameDemo.CustomFrameDemoItem;
import com.marwadibrothers.mbstatus.utils.Config;

import java.util.List;

public class CustomFrameDemoAdapter extends RecyclerView.Adapter<CustomFrameDemoAdapter.MyViewHolder> {
    private List<CustomFrameDemoItem> mDataset;
    private Context context;

    //shadow
//    implementation 'com.loopeer.lib:shadow:0.0.4-beta3'

    // Provide a suitable constructor (depends on the kind of dataset)
    public CustomFrameDemoAdapter(Context context, List<CustomFrameDemoItem> mDataset) {
        this.mDataset = mDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_frame_demo, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        /*Glide.with(holder.itemView.getContext())
                .load(mDataset.get(position).getFlag())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.ivFlag);*/
        CustomFrameDemoItem item = mDataset.get(position);
        if (item.getImage().length() > 0) {
            Glide.with(context).load(Config.IMG_PATH + item.getImage())
                    .placeholder(R.drawable.placeholderlogo)
                    .error(R.drawable.placeholderlogo)
                    .into(holder.ivCustomFrameDemoImg);
            holder.ivCustomFrameDemoImg.setVisibility(View.VISIBLE);
        } else {
            holder.ivCustomFrameDemoImg.setVisibility(View.GONE);
        }

        if (item.getImage().length() > 0) {
            holder.tvCustomFrameDemoCaption.setText(item.getCaption());
            holder.tvCustomFrameDemoCaption.setVisibility(View.VISIBLE);
        } else {
            holder.tvCustomFrameDemoCaption.setVisibility(View.GONE);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView ivCustomFrameDemoImg;
        public TextView tvCustomFrameDemoCaption;

        public MyViewHolder(View v) {
            super(v);
            ivCustomFrameDemoImg = v.findViewById(R.id.ivCustomFrameDemoImg);
            tvCustomFrameDemoCaption = v.findViewById(R.id.tvCustomFrameDemoCaption);
        }
    }

    public OnItemClickListner onItemClickListner;

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface OnItemClickListner {
        void onItemClick(int position);
    }
}