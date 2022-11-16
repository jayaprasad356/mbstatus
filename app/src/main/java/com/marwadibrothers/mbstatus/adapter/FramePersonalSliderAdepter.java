package com.marwadibrothers.mbstatus.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.activity.BannerEditingActivity;
import com.marwadibrothers.mbstatus.activity.editing.GreetingBannerEditingActivity;
import com.marwadibrothers.mbstatus.activity.editing.ImageEditingActivity;
import com.marwadibrothers.mbstatus.base.App;
import com.marwadibrothers.mbstatus.models.buiness.BusinessResponseData;
import com.marwadibrothers.mbstatus.models.profille.MyProfileResponseData;
import com.marwadibrothers.mbstatus.utils.Config;

import java.util.List;

public class FramePersonalSliderAdepter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<Integer> imgarrayList;
    MyProfileResponseData responseData;
    TextView tvSlash, txtMail, txtAddres, txtWebsite, txtMobile, txtBusinessName2, txtBusinessName, tv_name, txtName, txtDesignation, txtAddress;
    ImageView iv_f2_fb, iv_f2_inst, iv_f2_wa, iv_f2_wt;
    LinearLayout ll_social, llSocial, ll_fb, ll_insta, ll_yt, ll_wa, ll_tw;
    ImageView ivfooterLogo, profile_image;
    String comeFrom;

    public FramePersonalSliderAdepter(Context context, List<Integer> imgarrayList, MyProfileResponseData responseData, String comeFrom) {
        this.context = context;
        this.imgarrayList = imgarrayList;
        this.responseData = responseData;
        this.comeFrom = comeFrom;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 14 + App.getUserCustomFrameList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout;
        /*ImageView myImage = myImageLayout.findViewById(R.id.iv_frame);
        Log.d("dfjdf","jfdjfk"+imgarrayList.get(position));
        myImage.setImageDrawable(context.getResources().getDrawable(R.drawable.frame_14));*/
        Log.d("TAG", "instantiateItem: =-=-=-=->>>1>>" + position);
        Log.d("TAG", "instantiateItem: =-=-=-=->>>2>>" + App.getUserCustomFrameList.size());
        if (position < App.getUserCustomFrameList.size()) {
            Log.d("TAG", "instantiateItem: =-=-=-=->>>3>>" + position);
            myImageLayout = inflater.inflate(R.layout.foolter_cutome_frame_style, view, false);
            ImageView ivCustomFrame = myImageLayout.findViewById(R.id.ivCustomFrame);
            /*Glide.with(context).load(Config.IMG_PATH + App.getUserCustomFrameList.get(position).getFrame())
                    .placeholder(R.drawable.custom_frame)
                    .error(R.drawable.custom_frame)
                    .into(ivCustomFrame);*/

            Glide.with(context).load(Config.IMG_PATH + App.getUserCustomFrameList.get(position).getFrame()).into(ivCustomFrame);
        } else {
            Log.d("TAG", "instantiateItem: =-=-=-=->>>4>>" + (position - App.getUserCustomFrameList.size()));
            switch (position - App.getUserCustomFrameList.size()) {
                case 0:
                    myImageLayout = inflater.inflate(R.layout.foolter_style_nine, view, false);
                    txtBusinessName = myImageLayout.findViewById(R.id.txtBusinessName);
                    txtDesignation = myImageLayout.findViewById(R.id.txtDesignation);
                    txtAddress = myImageLayout.findViewById(R.id.txtAddress);
                    profile_image = myImageLayout.findViewById(R.id.profile_image);
                    txtMobile = myImageLayout.findViewById(R.id.txtMobile);
                    txtBusinessName.setText(responseData.getFullName());
                    txtAddress.setText(responseData.getCompanyOrgName());
                    txtDesignation.setText(responseData.getDesignation());
                    txtMobile.setText(responseData.getMobileNumber());
                    Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic())
                            .placeholder(R.drawable.placeholderlogo)
                            .error(R.drawable.placeholderlogo)
                            .into(profile_image);
                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    break;
                case 1:
                    myImageLayout = inflater.inflate(R.layout.foolter_style_seven, view, false);
                    txtMobile = myImageLayout.findViewById(R.id.txtMobile);
                    tv_name = myImageLayout.findViewById(R.id.tv_name);
                    txtMobile.setText(responseData.getMobileNumber());
                    tv_name.setText(responseData.getFullName());
                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    break;

                case 2:
                    myImageLayout = inflater.inflate(R.layout.foolter_style_15, view, false);
                    txtBusinessName = myImageLayout.findViewById(R.id.txtBusinessName);
                    txtAddres = myImageLayout.findViewById(R.id.txtAddress);
                    ll_social = myImageLayout.findViewById(R.id.ll_social);
                    ll_fb = myImageLayout.findViewById(R.id.ll_fb);
                    ll_insta = myImageLayout.findViewById(R.id.ll_insta);
                    ll_yt = myImageLayout.findViewById(R.id.ll_yt);
                    ll_wa = myImageLayout.findViewById(R.id.ll_wa);
                    profile_image = myImageLayout.findViewById(R.id.profile_image);
                    txtMobile = myImageLayout.findViewById(R.id.txtMobile);
                    txtAddres.setText(responseData.getAddress());
                    txtMobile.setText(responseData.getMobileNumber());
                    txtBusinessName.setText(responseData.getFullName());
                    Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic())
                            .placeholder(R.drawable.placeholderlogo)
                            .error(R.drawable.placeholderlogo)
                            .into(profile_image);
                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (Config.isYoutube || Config.isFb || Config.isInsta || Config.isWhatsapp) {
                        ll_social.setVisibility(View.VISIBLE);
                    } else {
                        ll_social.setVisibility(View.GONE);
                    }
                    if (Config.isYoutube) {
                        ll_yt.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        ll_fb.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        ll_insta.setVisibility(View.VISIBLE);
                    }
                    if (Config.isWhatsapp) {
                        ll_wa.setVisibility(View.VISIBLE);
                    }
                    break;

                case 3:
                    myImageLayout = inflater.inflate(R.layout.foolter_style_13, view, false);
                    txtBusinessName = myImageLayout.findViewById(R.id.txtBusinessName);
                    txtAddres = myImageLayout.findViewById(R.id.txtAddres);
                    ll_social = myImageLayout.findViewById(R.id.ll_social);
                    ll_fb = myImageLayout.findViewById(R.id.ll_fb);
                    ll_insta = myImageLayout.findViewById(R.id.ll_insta);
                    ll_yt = myImageLayout.findViewById(R.id.ll_yt);
                    ll_wa = myImageLayout.findViewById(R.id.ll_wa);
                    profile_image = myImageLayout.findViewById(R.id.profile_image);
                    txtMobile = myImageLayout.findViewById(R.id.txtMobile);

                    txtAddres.setText(responseData.getAddress());
                    txtMobile.setText(responseData.getMobileNumber());
                    txtBusinessName.setText(responseData.getFullName());
                    Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic())
                            .placeholder(R.drawable.placeholderlogo)
                            .error(R.drawable.placeholderlogo)
                            .into(profile_image);
                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (Config.isYoutube || Config.isFb || Config.isInsta || Config.isWhatsapp) {
                        ll_social.setVisibility(View.VISIBLE);
                    } else {
                        ll_social.setVisibility(View.GONE);
                    }
                    if (Config.isYoutube) {
                        ll_yt.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        ll_fb.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        ll_insta.setVisibility(View.VISIBLE);
                    }
                    if (Config.isWhatsapp) {
                        ll_wa.setVisibility(View.VISIBLE);
                    }
                    break;

                case 4:
                    myImageLayout = inflater.inflate(R.layout.foolter_style_eight, view, false);
                    txtName = myImageLayout.findViewById(R.id.txtName);
                    txtDesignation = myImageLayout.findViewById(R.id.txtDesignation);
                    txtAddress = myImageLayout.findViewById(R.id.txtAddress);
                    txtMobile = myImageLayout.findViewById(R.id.txtMobile);
                    profile_image = myImageLayout.findViewById(R.id.profile_image);
                    Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic())
                            .placeholder(R.drawable.placeholderlogo)
                            .error(R.drawable.placeholderlogo)
                            .into(profile_image);
                    txtName.setText(responseData.getFullName());
                    txtDesignation.setText(responseData.getDesignation());
                    txtAddress.setText(responseData.getCompanyOrgName());
                    txtMobile.setText(responseData.getMobileNumber());
                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    break;

                case 5:
                    myImageLayout = inflater.inflate(R.layout.foolter_style_ten, view, false);
                    txtBusinessName = myImageLayout.findViewById(R.id.txtBusinessName);
                    txtDesignation = myImageLayout.findViewById(R.id.txtDesignation);
                    txtAddress = myImageLayout.findViewById(R.id.txtAddress);
                    profile_image = myImageLayout.findViewById(R.id.profile_image);
                    txtMobile = myImageLayout.findViewById(R.id.txtMobile);
                    txtBusinessName.setText(responseData.getFullName());
                    txtDesignation.setText(responseData.getDesignation());
                    txtAddress.setText(responseData.getAddress());
                    txtMobile.setText(responseData.getMobileNumber());
                    Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic())
                            .placeholder(R.drawable.placeholderlogo)
                            .error(R.drawable.placeholderlogo)
                            .into(profile_image);
                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(false);

                    }

                    break;

                case 6:
                    myImageLayout = inflater.inflate(R.layout.foolter_style_eleven, view, false);
                    txtBusinessName = myImageLayout.findViewById(R.id.txtBusinessName);
                    txtMobile = myImageLayout.findViewById(R.id.txtMobile);
                    txtAddress = myImageLayout.findViewById(R.id.txtAddress);
                    profile_image = myImageLayout.findViewById(R.id.profile_image);
                    txtBusinessName.setText(responseData.getFullName());
                    txtMobile.setText(responseData.getMobileNumber());
                    txtAddress.setText(responseData.getAddress());
                    Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic())
                            .placeholder(R.drawable.placeholderlogo)
                            .error(R.drawable.placeholderlogo)
                            .into(profile_image);
                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    break;

                case 7:
                    myImageLayout = inflater.inflate(R.layout.foolter_style_12, view, false);
                    txtBusinessName = myImageLayout.findViewById(R.id.txtBusinessName);
                    txtDesignation = myImageLayout.findViewById(R.id.txtDesignation);
                    txtAddres = myImageLayout.findViewById(R.id.txtAddres);
                    ll_social = myImageLayout.findViewById(R.id.ll_social);
                    ll_fb = myImageLayout.findViewById(R.id.ll_fb);
                    ll_insta = myImageLayout.findViewById(R.id.ll_insta);
                    ll_yt = myImageLayout.findViewById(R.id.ll_yt);
                    ll_wa = myImageLayout.findViewById(R.id.ll_wa);
                    txtMobile = myImageLayout.findViewById(R.id.txtMobile);
                    profile_image = myImageLayout.findViewById(R.id.profile_image);

                    txtBusinessName.setText(responseData.getFullName());
                    txtDesignation.setText(responseData.getDesignation());
                    txtAddres.setText(responseData.getAddress());
                    txtMobile.setText(responseData.getMobileNumber());
                    Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic())
                            .placeholder(R.drawable.placeholderlogo)
                            .error(R.drawable.placeholderlogo)
                            .into(profile_image);
                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (Config.isYoutube || Config.isFb || Config.isInsta || Config.isWhatsapp) {
                        ll_social.setVisibility(View.VISIBLE);
                    } else {
                        ll_social.setVisibility(View.GONE);
                    }
                    if (Config.isYoutube) {
                        ll_yt.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        ll_fb.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        ll_insta.setVisibility(View.VISIBLE);
                    }
                    if (Config.isWhatsapp) {
                        ll_wa.setVisibility(View.VISIBLE);
                    }
                    break;


                case 8:
                    myImageLayout = inflater.inflate(R.layout.foolter_style_16, view, false);
                    txtBusinessName = myImageLayout.findViewById(R.id.txtBusinessName);
                    txtAddres = myImageLayout.findViewById(R.id.txtAddress);

                    profile_image = myImageLayout.findViewById(R.id.profile_image);
                    txtMobile = myImageLayout.findViewById(R.id.txtMobile);
                    txtAddres.setText(responseData.getAddress());
                    txtMobile.setText(responseData.getMobileNumber());
                    txtBusinessName.setText(responseData.getFullName());
                    Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic())
                            .placeholder(R.drawable.placeholderlogo)
                            .error(R.drawable.placeholderlogo)
                            .into(profile_image);

                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(false);

                    }

                    break;

                case 9:
                    myImageLayout = inflater.inflate(R.layout.foolter_style_17, view, false);
                    txtBusinessName = myImageLayout.findViewById(R.id.txtBusinessName);
                    txtBusinessName2 = myImageLayout.findViewById(R.id.txtBusinessName2);
                    tvSlash = myImageLayout.findViewById(R.id.tvSlash);
                    txtDesignation = myImageLayout.findViewById(R.id.txtDesignation);
                    ll_fb = myImageLayout.findViewById(R.id.ll_fb);
                    ll_insta = myImageLayout.findViewById(R.id.ll_insta);
                    ll_tw = myImageLayout.findViewById(R.id.ll_tw);
                    ll_social = myImageLayout.findViewById(R.id.ll_social);

                    profile_image = myImageLayout.findViewById(R.id.profile_image);
                    txtBusinessName2.setText(responseData.getFullName());
                    txtBusinessName.setText(responseData.getFullName());
                    txtDesignation.setText("( " + responseData.getDesignation() + " )");
                    Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic())
                            .placeholder(R.drawable.placeholderlogo)
                            .error(R.drawable.placeholderlogo)
                            .into(profile_image);
                    txtBusinessName2.setVisibility(View.VISIBLE);
                    tvSlash.setVisibility(View.GONE);

                    if (Config.isTwitter) {
                        ll_tw.setVisibility(View.VISIBLE);
                        tvSlash.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        ll_fb.setVisibility(View.VISIBLE);
                        tvSlash.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        ll_insta.setVisibility(View.VISIBLE);
                        tvSlash.setVisibility(View.VISIBLE);
                    }

                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(false);

                    }

                    break;


                case 10:
                    myImageLayout = inflater.inflate(R.layout.foolter_style_18, view, false);
                    txtBusinessName = myImageLayout.findViewById(R.id.txtBusinessName);
                    txtBusinessName2 = myImageLayout.findViewById(R.id.txtBusinessName2);
                    tvSlash = myImageLayout.findViewById(R.id.tvSlash);
                    txtDesignation = myImageLayout.findViewById(R.id.txtDesignation);
                    ll_fb = myImageLayout.findViewById(R.id.ll_fb);
                    ll_insta = myImageLayout.findViewById(R.id.ll_insta);
                    ll_tw = myImageLayout.findViewById(R.id.ll_tw);
                    ll_social = myImageLayout.findViewById(R.id.ll_social);

                    profile_image = myImageLayout.findViewById(R.id.profile_image);
                    txtBusinessName2.setText(responseData.getFullName());
                    txtBusinessName.setText(responseData.getFullName());
                    txtDesignation.setText("( " + responseData.getDesignation() + " )");
                    Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic())
                            .placeholder(R.drawable.placeholderlogo)
                            .error(R.drawable.placeholderlogo)
                            .into(profile_image);
                    txtBusinessName2.setVisibility(View.VISIBLE);
                    tvSlash.setVisibility(View.GONE);

                    if (Config.isTwitter) {
                        ll_tw.setVisibility(View.VISIBLE);
                        tvSlash.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        ll_fb.setVisibility(View.VISIBLE);
                        tvSlash.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        ll_insta.setVisibility(View.VISIBLE);
                        tvSlash.setVisibility(View.VISIBLE);
                    }

                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(false);

                    }

                    break;

                case 11:
                    myImageLayout = inflater.inflate(R.layout.foolter_style_19, view, false);
                    txtBusinessName = myImageLayout.findViewById(R.id.txtBusinessName);
                    txtDesignation = myImageLayout.findViewById(R.id.txtDesignation);
                    txtAddress = myImageLayout.findViewById(R.id.txtAddress);

                    profile_image = myImageLayout.findViewById(R.id.profile_image);
                    txtAddress.setText(responseData.getAddress());
                    txtBusinessName.setText(responseData.getFullName());
                    txtDesignation.setText(responseData.getDesignation());

                    Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic())
                            .placeholder(R.drawable.placeholderlogo)
                            .error(R.drawable.placeholderlogo)
                            .into(profile_image);

                    txtBusinessName2.setVisibility(View.VISIBLE);

                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(false);
                    }
                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(false);
                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(false);
                    }
                    break;

                case 12:
                    myImageLayout = inflater.inflate(R.layout.foolter_style_21_new, view, false);
                    txtBusinessName = myImageLayout.findViewById(R.id.txtBusinessName);
//                    txtBusinessName2 = myImageLayout.findViewById(R.id.txtBusinessName2);
//                    tvSlash = myImageLayout.findViewById(R.id.tvSlash);
                    txtDesignation = myImageLayout.findViewById(R.id.txtDesignation);
                    ll_fb = myImageLayout.findViewById(R.id.ll_fb);
                    ll_insta = myImageLayout.findViewById(R.id.ll_insta);
                    ll_tw = myImageLayout.findViewById(R.id.ll_tw);
                    ll_social = myImageLayout.findViewById(R.id.ll_social);

                    profile_image = myImageLayout.findViewById(R.id.profile_image);
                    txtBusinessName.setText(responseData.getFullName());
//                    txtBusinessName2.setText(responseData.getFullName());
                    txtDesignation.setText(responseData.getDesignation());
                    Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic())
                            .placeholder(R.drawable.placeholderlogo)
                            .error(R.drawable.placeholderlogo)
                            .into(profile_image);
//                    txtBusinessName2.setVisibility(View.VISIBLE);
//                    tvSlash.setVisibility(View.GONE);

//                    if (Config.isTwitter) {
                    ll_tw.setVisibility(View.VISIBLE);
//                        tvSlash.setVisibility(View.VISIBLE);
//                    }
//                    if (Config.isFb) {
                    ll_fb.setVisibility(View.VISIBLE);
//                        tvSlash.setVisibility(View.VISIBLE);
//                    }
//                    if (Config.isInsta) {
                    ll_insta.setVisibility(View.VISIBLE);
//                        tvSlash.setVisibility(View.VISIBLE);
//                    }

                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(false);
                    }
                    break;

                case 13:
                    myImageLayout = inflater.inflate(R.layout.foolter_style_22_new, view, false);
                    txtBusinessName = myImageLayout.findViewById(R.id.txtBusinessName);
                    txtBusinessName2 = myImageLayout.findViewById(R.id.txtBusinessName2);
                    LinearLayout llWhatsapp = myImageLayout.findViewById(R.id.llWhatsapp);
                    TextView tvWhatsappNo = myImageLayout.findViewById(R.id.tvWhatsappNo);
//                    tvSlash = myImageLayout.findViewById(R.id.tvSlash);
//                    txtDesignation = myImageLayout.findViewById(R.id.txtDesignation);
                    ll_fb = myImageLayout.findViewById(R.id.ll_fb);
                    ll_insta = myImageLayout.findViewById(R.id.ll_insta);
                    ll_tw = myImageLayout.findViewById(R.id.ll_twitter);
                    ll_social = myImageLayout.findViewById(R.id.ll_social);

                    profile_image = myImageLayout.findViewById(R.id.profile_image);
                    txtBusinessName.setText(responseData.getFullName());
                    txtBusinessName2.setText(responseData.getCompanyOrgName());
                    tvWhatsappNo.setText(responseData.getMobileNumber());
//                    txtDesignation.setText(responseData.getDesignation());
                    Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic())
                            .placeholder(R.drawable.placeholderlogo)
                            .error(R.drawable.placeholderlogo)
                            .into(profile_image);
                    txtBusinessName2.setVisibility(View.GONE);
//                    tvSlash.setVisibility(View.GONE);

//                    if (Config.isTwitter) {
                    ll_tw.setVisibility(View.VISIBLE);
//                        tvSlash.setVisibility(View.VISIBLE);
//                    }
//                    if (Config.isFb) {
                    ll_fb.setVisibility(View.VISIBLE);
//                        tvSlash.setVisibility(View.VISIBLE);
//                    }
//                    if (Config.isInsta) {
                    ll_insta.setVisibility(View.VISIBLE);
//                        tvSlash.setVisibility(View.VISIBLE);
//                    }

                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(false);

                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(false);
                    }

                    break;

                default:
                    myImageLayout = inflater.inflate(R.layout.frame_list_raw, view, false);
            }
        }
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}