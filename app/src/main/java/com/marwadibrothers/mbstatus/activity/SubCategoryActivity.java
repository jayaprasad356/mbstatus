package com.marwadibrothers.mbstatus.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
//import com.facebook.ads.Ad;
//import com.facebook.ads.AdSettings;
//import com.facebook.ads.AudienceNetworkAds;
//import com.facebook.ads.InterstitialAdListener;
import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.activity.editing.GreetingBannerEditingActivity;
import com.marwadibrothers.mbstatus.adapter.MyMasterRecyclerAdapter;
import com.marwadibrothers.mbstatus.databinding.ActivitySubCategoryBinding;
import com.marwadibrothers.mbstatus.databinding.ItemSubcategoryBinding;
import com.marwadibrothers.mbstatus.models.product.ProductResponse;
import com.marwadibrothers.mbstatus.models.product.ProductResponseData;
import com.marwadibrothers.mbstatus.sevices.ApiInterface;
import com.marwadibrothers.mbstatus.sevices.RetrofitClient;
import com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper;
import com.marwadibrothers.mbstatus.utils.Config;
import com.marwadibrothers.mbstatus.utils.Helper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwadibrothers.mbstatus.utils.Config.SUB_CAT_NAME;
import static com.marwadibrothers.mbstatus.utils.Helper.HideActionBar;

public class SubCategoryActivity extends AppCompatActivity {
    private MyMasterRecyclerAdapter recyclerAdapter;
    private ActivitySubCategoryBinding binding;
    private RecyclerView rvSubCategrory;
    private List<ProductResponseData> arrayList = new ArrayList<>();
    private Context context;
    private ApiInterface apiInterface;
    PopupWindow mPopupWindow;
    String subCatID;

//    public com.facebook.ads.InterstitialAd interstitialFacbookAd;
//    public InterstitialAdListener interstitialAdListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_category);
        initviews();
        CLickEvents();
        //loadAd();
//        initAD();
        Log.d("fdnsjfjd", "djfdjfkj");


    }
/*

    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder()

                .build();

        binding.adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                // Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        binding.adView.loadAd(adRequest);
    }
*/

    private void CLickEvents() {
        binding.toolbarSupport.ivBack.setOnClickListener(v -> finish());
    }

    private void initviews() {
        context = this;
        HideActionBar(this);
        apiInterface = RetrofitClient.getRetrofitInstance(this).create(ApiInterface.class);
        rvSubCategrory = binding.rvSubCategrory;
        binding.toolbarSupport.txttitle.setText(getIntent().getStringExtra(SUB_CAT_NAME));
        setRecyclerAdapter();
        initAdapter();
        subCatID = getIntent().getStringExtra(Config.SUB_CAT_ID);
        if (Helper.checkInternet(context)) {
            GetProductData();
        }
        openSubCat();
    }


    private void openSubCat() {

        RequestBody subCatId = RequestBody.create(subCatID, MediaType.parse("text/plain"));
        Call<ResponseBody> call = apiInterface.openSubCat(subCatId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Helper.cancel_loader();
                if (response.isSuccessful()) {
                    try {
                        if (response.isSuccessful()) {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getBoolean("status")) {

                                Log.d("djsdjasdjksaj", "jdsjdsj" + jsonObject.getString("message"));
                                // Toast.makeText(context,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(context, getString(R.string.Somethingswrong), Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        Helper.cancel_loader();
                        e.printStackTrace();
                    }

                } else {
                    Helper.cancel_loader();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });
    }

    private void setRecyclerAdapter() {
        rvSubCategrory.setLayoutManager(new GridLayoutManager(this, 2));
        rvSubCategrory.setAdapter(recyclerAdapter);
    }

    private void updateProductlist(List<ProductResponseData> list) {
        arrayList = list;
        if (recyclerAdapter == null) {
            initAdapter();
        } else {
            recyclerAdapter.updateNewList(list);
        }
        rvSubCategrory.setAdapter(recyclerAdapter);
    }

    private void initAdapter() {
        recyclerAdapter = new MyMasterRecyclerAdapter(arrayList, R.layout.item_subcategory);
        rvSubCategrory.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnBind((viewDataBinding, position) -> {
            ItemSubcategoryBinding binding = (ItemSubcategoryBinding) viewDataBinding;
            ProductResponseData model = arrayList.get(position);
            binding.setModel(model);

            Glide.with(context).load(Config.IMG_PATH + model.getProductImage()).placeholder(R.drawable.placeholderlogo).into(binding.imageView);

            if (model.getProduct_type().equals("PAID")) {
                if (new SharedPreferencesHelper(context).getBoolean(Config.Buy_Plan)) {
                    binding.rlPremium.setVisibility(View.GONE);

                } else {
                    binding.rlPremium.setVisibility(View.VISIBLE);
                }
            } else {
                binding.rlPremium.setVisibility(View.GONE);
            }

            binding.getRoot().setOnClickListener(v -> {
                // showDialog();
                recyclerAdapter.notifyDataSetChanged();
                if (!new SharedPreferencesHelper(context).getBoolean(Config.Buy_Plan)) {
                    MainActivity.instance.stopTask();
                    diplayFacebookad();
                }

                Log.d("fjdfjdfjd", "fjdjfdj---------------" + model.getProduct_type());
                if (model.getProduct_type().equalsIgnoreCase("FREE")) {
                    if (getIntent().getStringExtra((Config.FROM)).equals(Config.GREETING) || model.getDisplay_section().equalsIgnoreCase("GREETINGS")) {
                        startActivity(new Intent(context, GreetingBannerEditingActivity.class)
                                .putExtra(Config.IMAGE_URL, Config.IMG_PATH + model.getProductImage())
                                .putExtra(Config.PRODUCT_ID, model.getProductId())
                                .putExtra(Config.SUB_CAT_ID_1, subCatID)
                                .putExtra(Config.FORGROUND_IMAGE, Config.IMG_PATH + model.getForeground_image()));
                    } else {
                        startActivity(new Intent(context, BannerEditingActivity.class)
                                .putExtra(Config.IMAGE_URL, Config.IMG_PATH + model.getProductImage())
                                .putExtra(Config.SUB_CAT_ID_1, subCatID)
                                .putExtra(Config.PRODUCT_ID, model.getProductId()));
                    }
                } else {
                    if (new SharedPreferencesHelper(context).getBoolean(Config.Buy_Plan)) {
                        if (getIntent().getStringExtra((Config.FROM)).equals(Config.GREETING) || model.getDisplay_section().equalsIgnoreCase("GREETINGS")) {
                            startActivity(new Intent(context, GreetingBannerEditingActivity.class)
                                    .putExtra(Config.IMAGE_URL, Config.IMG_PATH + model.getProductImage())
                                    .putExtra(Config.PRODUCT_ID, model.getProductId())
                                    .putExtra(Config.SUB_CAT_ID_1, subCatID)
                                    .putExtra(Config.FORGROUND_IMAGE, Config.IMG_PATH + model.getForeground_image()));
                        } else {
                            startActivity(new Intent(context, BannerEditingActivity.class)
                                    .putExtra(Config.IMAGE_URL, Config.IMG_PATH + model.getProductImage())
                                    .putExtra(Config.SUB_CAT_ID_1, subCatID)
                                    .putExtra(Config.PRODUCT_ID, model.getProductId()));
                        }
                    } else {
                        startActivity(new Intent(context, PackageActivity.class).putExtra(Config.IMAGE_URL, Config.IMG_PATH + model.getProductImage()).putExtra(Config.PRODUCT_ID, model.getProductId()));

                    }
                }
            });
        });

    }

    void initAD() {


        /*AudienceNetworkAds.initialize(this);


        //AdSettings.setIntegrationErrorMode(INTEGRATION_ERROR_CRASH_DEBUG_MODE);
        // Toast.makeText(ActivityNewsPapersCategory.this,"id"+getResources().getString(R.string.facebook_interstitial_Ad),Toast.LENGTH_LONG).show();
        interstitialFacbookAd = new com.facebook.ads.InterstitialAd(this, getResources().getString(R.string.facebook_interstitial_Ad));
        //AdSettings.setDebugBuild(true);
        AdSettings.clearTestDevices();
        // AdSettings.addTestDevice("HASHED ID");

        interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback

                MainActivity.instance.StartTimer();

            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                // Ad error callback
                MainActivity.instance.StartTimer();

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                // Show the ad
                interstitialFacbookAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };

        interstitialFacbookAd.buildLoadAdConfig()
                .withAdListener(interstitialAdListener)
                .build();*/
    }

    public void diplayFacebookad() {
        /*if (interstitialFacbookAd != null) {
            if (!interstitialFacbookAd.isAdLoaded()) {

                // AdSettings.setDebugBuild(true);
                AdSettings.clearTestDevices();
                interstitialFacbookAd.loadAd();
            } else {

            }
        } else {

        }*/
    }



    /*public void showDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.profile_alert_dialog);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        // lp.windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setAttributes(lp);
        ImageView ivPAClose;
        TextView tvPAProfile,tvPABusiness;
        ivPAClose = dialog.findViewById(R.id.ivPAClose);
        tvPAProfile = dialog.findViewById(R.id.tvPAProfile);
        tvPABusiness = dialog.findViewById(R.id.tvPABusiness);

        ivPAClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }*/

    private void GetProductData() {
        Helper.Show_loader(context, false, false);
        RequestBody sub_category_id = RequestBody.create(getIntent().getStringExtra(Config.SUB_CAT_ID), MediaType.parse("text/plain"));

        Call<ProductResponse> call = apiInterface.get_product_list(sub_category_id);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        if (!(response.body().getData().size() == 0)) {
                            binding.txtCommingSoon.setVisibility(View.GONE);
                            updateProductlist(response.body().getData());
                        } else {
                            binding.txtCommingSoon.setVisibility(View.VISIBLE);
                            updateProductlist(response.body().getData());

                        }

                    } catch (Exception e) {
                        Helper.cancel_loader();
                        e.printStackTrace();
                        Log.d("djsfjsj", "djhfd" + e.getMessage());
                    }

                } else {
                    Helper.cancel_loader();
                    Log.d("djsfjsj", "else");
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("djsfjsj", "hh" + t.getMessage());
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });

    }
}