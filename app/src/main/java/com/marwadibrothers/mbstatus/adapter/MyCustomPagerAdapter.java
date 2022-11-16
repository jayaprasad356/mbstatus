package com.marwadibrothers.mbstatus.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.activity.SubCategoryActivity;
import com.marwadibrothers.mbstatus.models.home.BannerSection;
import com.marwadibrothers.mbstatus.utils.Config;

import java.util.List;

import static com.marwadibrothers.mbstatus.utils.Config.NORMAL;
import static com.marwadibrothers.mbstatus.utils.Config.SUB_CAT_ID;
import static com.marwadibrothers.mbstatus.utils.Config.SUB_CAT_NAME;


public class MyCustomPagerAdapter extends PagerAdapter {
    Context context;
    private List<BannerSection> banner_list;
    LayoutInflater layoutInflater;

    public MyCustomPagerAdapter(Context context, List<BannerSection> banner_list) {
        this.context = context;
        this.banner_list = banner_list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return banner_list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item_banner, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        LinearLayout llView = itemView.findViewById(R.id.llView);

        Glide.with(context).load(Config.IMG_PATH + banner_list.get(position).getBannerImage()).into(imageView);
        container.addView(itemView);
        imageView.setOnClickListener(v -> {
            Log.e("IMAGE", Config.IMG_PATH + banner_list.get(position).getBannerImage());
            if (banner_list.get(position).getSubCategoryId().equals("0")) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(banner_list.get(position).getExternal_link()));
                context.startActivity(browserIntent);
            } else {
                context.startActivity(new Intent(context, SubCategoryActivity.class).putExtra(SUB_CAT_ID, banner_list.get(position).getSubCategoryId()).putExtra(SUB_CAT_NAME, banner_list.get(position).getSub_category_name()).putExtra(Config.FROM, NORMAL));

            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}