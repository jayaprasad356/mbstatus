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
import com.marwadibrothers.mbstatus.activity.editing.TextEditingActivity;
import com.marwadibrothers.mbstatus.base.App;
import com.marwadibrothers.mbstatus.models.buiness.BusinessResponseData;
import com.marwadibrothers.mbstatus.models.profille.MyProfileResponseData;
import com.marwadibrothers.mbstatus.utils.Config;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FrameSliderAdepter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<Integer> imgarrayList;
    BusinessResponseData responseData;
    TextView txtContact, txtMail, txtAddres, txtWebsite,txtMobile,textWebsite,txtCompanyName,tv_name,txtBusinessName;
    ImageView iv_f2_yt,iv_f2_fb,iv_f2_inst,iv_f2_wa,iv_f2_wt;
    LinearLayout ll_social,llSocial;
    ImageView ivfooterLogo;
    String comeFrom;


    public FrameSliderAdepter(Context context, List<Integer> imgarrayList, BusinessResponseData responseData,String comeFrom) {
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
        return 8 + App.getUserCustomFrameList.size();
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
           /* Glide.with(context).load(Config.IMG_PATH + App.getUserCustomFrameList.get(position).getFrame())
                    .placeholder(R.drawable.custom_frame)
                    .error(R.drawable.custom_frame)
                    .into(ivCustomFrame);*/

            Glide.with(context).load(Config.IMG_PATH + App.getUserCustomFrameList.get(position).getFrame()).into(ivCustomFrame);
        } else {
            Log.d("TAG", "instantiateItem: =-=-=-=->>>4>>" + (position - App.getUserCustomFrameList.size()));
            switch (position - App.getUserCustomFrameList.size()) {
                case 0:

                    myImageLayout = inflater.inflate(R.layout.footer_style_three, view, false);
                    txtCompanyName = myImageLayout.findViewById(R.id.txtCompanyName);
                    txtMobile = myImageLayout.findViewById(R.id.txtMobile);
                    txtMail = myImageLayout.findViewById(R.id.txtMail);
                    textWebsite = myImageLayout.findViewById(R.id.textWebsite);
//                txtCompanyName.setText(responseData.getBusinessName());
                    txtMobile.setText(responseData.getBusinessPhoneNo());
                    textWebsite.setText(responseData.getBusinessWebsite());
                    txtMail.setText(responseData.getBusinessEmail());

                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(true);
                    }

                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(true);
                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(true);
                    }

                    break;
                case 1:
                    myImageLayout = inflater.inflate(R.layout.footer_style_five, view, false);
                    txtContact = myImageLayout.findViewById(R.id.txtContact);
                    txtMail = myImageLayout.findViewById(R.id.txtMail);
                    txtAddres = myImageLayout.findViewById(R.id.txtAddres);
                    txtWebsite = myImageLayout.findViewById(R.id.txtWebsite);
                    txtMail.setText(responseData.getBusinessEmail());
                    txtContact.setText(responseData.getBusinessPhoneNo());
                    txtAddres.setText(responseData.getBusinessAddress());
                    txtWebsite.setText(responseData.getBusinessWebsite());

                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(true);
                    }

                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(true);
                    }

                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(true);
                    }

                    break;
                case 2:
                    myImageLayout = inflater.inflate(R.layout.footer_style_four, view, false);
                    txtMail = myImageLayout.findViewById(R.id.txtMail);
                    txtMobile = myImageLayout.findViewById(R.id.txtMobile);
                    textWebsite = myImageLayout.findViewById(R.id.textWebsite);
                    txtAddres = myImageLayout.findViewById(R.id.txtAddres);
                    iv_f2_yt = myImageLayout.findViewById(R.id.iv_f2_yt);
                    iv_f2_fb = myImageLayout.findViewById(R.id.iv_f2_fb);
                    iv_f2_inst = myImageLayout.findViewById(R.id.iv_f2_inst);
                    iv_f2_wa = myImageLayout.findViewById(R.id.iv_f2_wa);
                    ll_social = myImageLayout.findViewById(R.id.ll_social);
                    txtMail.setText(responseData.getBusinessEmail());
                    txtMobile.setText(responseData.getBusinessPhoneNo());
                    textWebsite.setText(responseData.getBusinessWebsite());
                    txtAddres.setText(responseData.getBusinessAddress());
                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(true);

                    }
                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(true);

                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(true);

                    }
                    if (Config.isYoutube || Config.isFb || Config.isInsta || Config.isWhatsapp) {
                        ll_social.setVisibility(View.VISIBLE);
                    } else {
                        ll_social.setVisibility(View.GONE);
                    }
                    if (Config.isYoutube) {
                        iv_f2_yt.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        iv_f2_fb.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        iv_f2_inst.setVisibility(View.VISIBLE);
                    }
                    if (Config.isWhatsapp) {
                        iv_f2_wa.setVisibility(View.VISIBLE);
                    }
                    break;

                case 3:
                    myImageLayout = inflater.inflate(R.layout.footer_style_two, view, false);
                    iv_f2_yt = myImageLayout.findViewById(R.id.iv_f2_yt);
                    iv_f2_fb = myImageLayout.findViewById(R.id.iv_f2_fb);
                    iv_f2_inst = myImageLayout.findViewById(R.id.iv_f2_inst);
                    iv_f2_wt = myImageLayout.findViewById(R.id.iv_f2_wt);
                    llSocial = myImageLayout.findViewById(R.id.ll_social);
                    txtCompanyName = myImageLayout.findViewById(R.id.txtCompanyName);
                    txtCompanyName.setText(responseData.getBusinessName());
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
                        llSocial.setVisibility(View.VISIBLE);
                    } else {
                        llSocial.setVisibility(View.GONE);
                    }
                    if (Config.isYoutube) {
                        iv_f2_yt.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        iv_f2_fb.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        iv_f2_inst.setVisibility(View.VISIBLE);
                    }
                    if (Config.isWhatsapp) {
                        iv_f2_wt.setVisibility(View.VISIBLE);
                    }
                    break;

                case 4:
                    myImageLayout = inflater.inflate(R.layout.foolter_style_one, view, false);
                    txtMail = myImageLayout.findViewById(R.id.txtMail);
                    txtMobile = myImageLayout.findViewById(R.id.txtMobile);
                    txtMail.setText(responseData.getBusinessEmail());
                    txtMobile.setText(responseData.getBusinessPhoneNo());
                    if (comeFrom.equals("imageEdit")) {
                        ImageEditingActivity.getInstance().setLogoVisibility(true);
                    }
                    if (comeFrom.equals("bannerEdit")) {
                        BannerEditingActivity.getInstance().setLogoVisibility(true);
                    }
                    if (comeFrom.equals("greetingEdit")) {
                        GreetingBannerEditingActivity.getInstance().setLogoVisibility(true);
                    }
                    break;

                case 5:
                    myImageLayout = inflater.inflate(R.layout.foolter_style_six, view, false);
                    ivfooterLogo = myImageLayout.findViewById(R.id.ivfooterLogo);
                    tv_name = myImageLayout.findViewById(R.id.tv_name);
                    tv_name.setText(responseData.getBusinessName());
                    Glide.with(context).load(Config.IMG_PATH + responseData.getBusinessLogo())
                            .placeholder(R.drawable.placeholderlogo)
                            .error(R.drawable.placeholderlogo)
                            .into(ivfooterLogo);
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
                    myImageLayout = inflater.inflate(R.layout.footer_style_14, view, false);
                    txtMobile = myImageLayout.findViewById(R.id.txtMobile);
                    txtMail = myImageLayout.findViewById(R.id.txtMail);
                    textWebsite = myImageLayout.findViewById(R.id.textWebsite);
                    txtMobile.setText(responseData.getBusinessPhoneNo());
                    txtMail.setText(responseData.getBusinessEmail());
                    textWebsite.setText(responseData.getBusinessWebsite());
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
                    myImageLayout = inflater.inflate(R.layout.foolter_style_20, view, false);
                    txtCompanyName = myImageLayout.findViewById(R.id.txtCompanyName);
                    txtMail = myImageLayout.findViewById(R.id.txtMail);
                    txtMobile = myImageLayout.findViewById(R.id.txtMobile);
                    textWebsite = myImageLayout.findViewById(R.id.textWebsite);
                    txtMobile.setText(responseData.getBusinessPhoneNo());
                    txtMail.setText(responseData.getBusinessEmail());
                    textWebsite.setText(responseData.getBusinessWebsite());
                    txtCompanyName.setText(responseData.getBusinessName());
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