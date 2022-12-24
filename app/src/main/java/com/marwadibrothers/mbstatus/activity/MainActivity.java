package com.marwadibrothers.mbstatus.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.bumptech.glide.Glide;
//import com.facebook.ads.Ad;
//import com.facebook.ads.AdError;
//import com.facebook.ads.AdSettings;
//import com.facebook.ads.AudienceNetworkAds;
//import com.facebook.ads.InterstitialAdListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.activity.editing.GreetingBannerEditingActivity;
import com.marwadibrothers.mbstatus.activity.editing.ImageEditingActivity;
import com.marwadibrothers.mbstatus.activity.editing.TextEditingActivity;
import com.marwadibrothers.mbstatus.adapter.FeaturedImageAdepter;
import com.marwadibrothers.mbstatus.adapter.MyCustomPagerAdapter;
import com.marwadibrothers.mbstatus.adapter.MyMasterRecyclerAdapter;
import com.marwadibrothers.mbstatus.apicallback.GetAppSetting;
import com.marwadibrothers.mbstatus.apicallback.GetAppSettingApi;
import com.marwadibrothers.mbstatus.databinding.ActivityMainBinding;
import com.marwadibrothers.mbstatus.databinding.ItemBuisnessListBinding;
import com.marwadibrothers.mbstatus.databinding.ItemDimgDataBinding;
import com.marwadibrothers.mbstatus.databinding.ItemGreetingBinding;
import com.marwadibrothers.mbstatus.databinding.ItemHomeListBinding;

import com.marwadibrothers.mbstatus.databinding.ItemSubDimgListBinding;
import com.marwadibrothers.mbstatus.databinding.ItemSubGreetingBinding;
import com.marwadibrothers.mbstatus.databinding.ItemSubHomeListBinding;
import com.marwadibrothers.mbstatus.fragment.MultipleAccountBottom;
import com.marwadibrothers.mbstatus.models.PopUpModel;
import com.marwadibrothers.mbstatus.models.UpdateAppModel;
import com.marwadibrothers.mbstatus.models.buiness.BusinessExtraData;
import com.marwadibrothers.mbstatus.models.buiness.BusinessResponse;
import com.marwadibrothers.mbstatus.models.buiness.BusinessResponseData;
import com.marwadibrothers.mbstatus.models.home.BannerSection;
import com.marwadibrothers.mbstatus.models.home.FearuredImage;
import com.marwadibrothers.mbstatus.models.home.FearuredImageData;
import com.marwadibrothers.mbstatus.models.home.GreetingListResponse;
import com.marwadibrothers.mbstatus.models.home.GreetingReponse;

import com.marwadibrothers.mbstatus.models.home.HomeResponse;
import com.marwadibrothers.mbstatus.models.home.HomeResponseData;
import com.marwadibrothers.mbstatus.sevices.ApiInterface;
import com.marwadibrothers.mbstatus.apicallback.GetUserCustomFramesApi;
import com.marwadibrothers.mbstatus.sevices.RetrofitClient;
import com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper;
import com.marwadibrothers.mbstatus.utils.Config;
import com.marwadibrothers.mbstatus.utils.Conts;
import com.marwadibrothers.mbstatus.utils.Helper;
import com.marwadibrothers.mbstatus.weidget.SweetAlert.SweetAlertDialog;
import com.marwadibrothers.mbstatus.weidget.autoviewpager.AutoScrollViewPager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwadibrothers.mbstatus.activity.SplashScreenActivity.FCM_TOKEN;
import static com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper.STORAGE_BUSINESS_ACCOUNT;
import static com.marwadibrothers.mbstatus.utils.Config.GREETING;
import static com.marwadibrothers.mbstatus.utils.Config.NORMAL;
import static com.marwadibrothers.mbstatus.utils.Config.SELECT_ACCOUNT;
import static com.marwadibrothers.mbstatus.utils.Config.SELECT_ACCOUNT_ID;
import static com.marwadibrothers.mbstatus.utils.Config.SUB_CAT_ID;
import static com.marwadibrothers.mbstatus.utils.Config.SUB_CAT_NAME;
import static com.marwadibrothers.mbstatus.utils.Helper.HideActionBar;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    //    public com.facebook.ads.InterstitialAd interstitialFacbookAd;
//    public InterstitialAdListener interstitialAdListener;
    Button btn;
    TimerTask hourlyTask;
    Timer timer;
    private Context context;
    private List<BannerSection> banner_list = new ArrayList<>();
    private AutoScrollViewPager viewPager;
    private MyMasterRecyclerAdapter recyclerAdapter, recyclerFIAdapter, GreetingTopRecyclerAdapter, GreetingrecyclerAdapter, BuisnessListAdapter;
    private RecyclerView rvHome, rvDirectImage, rvGreetingTopProduct, rvGreeting, rvBuinnessList;
    private List<HomeResponse> homeResponseList = new ArrayList<>();
    private List<FearuredImage> fearuredImageData = new ArrayList<>();
    private List<FearuredImage> GreetingTopProductList = new ArrayList<>();
    private List<FearuredImageData> fearuredImageList = new ArrayList<>();
    private List<GreetingReponse> GreetingarrayList = new ArrayList<>();
    private List<BusinessResponseData> businessarrayList = new ArrayList<>();
    private BusinessExtraData businessExtraData = null;
    private MultipleAccountBottom multipleAccountBottom;
    private ApiInterface apiInterface;
    private SharedPreferencesHelper preferencesHelper;
    Dialog dialog;
    AlertDialog alertDialog = null, updateAlertDialog = null;
    Date last_date, current_date;
    Date currentDate, expireDate, testDate;
    int versionCode;
    public static MainActivity instance = null;

    public MainActivity() {
        instance = MainActivity.this;
    }

    public static synchronized MainActivity getInstance() {
        if (instance == null) {
            instance = new MainActivity();
        }
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initviews();
        Log.e("User_Id", new SharedPreferencesHelper(context).getString(Config.USER_ID));

        GetUserPlanData();
        new GetUserCustomFramesApi(MainActivity.this, null);
        initAcc();
        BottomClick();
        ClickEvents();
        onCall();
//        initAD();
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                displayAdMob();
                Log.d("jdsfjksdjfksdj", "0");

                StartTimer();
                diplayFacebookad();
            }
        }, 1000 * 10);
        // StartTimer();
    }

    void initAD() {


       /* AudienceNetworkAds.initialize(this);


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
                stopTask();
                StartTimer();
                Conts.AllowToOpenAdvertise = false;

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                stopTask();
                StartTimer();
                Conts.AllowToOpenAdvertise = false;

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

    public void StartTimer() {
        timer = new Timer();
        hourlyTask = new TimerTask() {
            @Override
            public void run() {
                if (!Conts.isFirstTimeOpen) {
                    Conts.AllowToOpenAdvertise = true;
                    Log.d("jdsfjksdjfksdj", "1");
                    diplayFacebookad();
                    stopTask();
                } else {
                    Conts.isFirstTimeOpen = false;
                }
            }
        };

        Conts.isFirstTimeOpen = true;
        if (timer != null) {
            timer.schedule(hourlyTask, 0, 1000 * 40);
        }
    }

    public void diplayFacebookad() {
        if (new SharedPreferencesHelper(context).getBoolean(Config.Buy_Plan)) {
            stopTask();
            StartTimer();
            Conts.AllowToOpenAdvertise = false;
        } else {
            /*if (interstitialFacbookAd != null) {
                if (!interstitialFacbookAd.isAdLoaded()) {

                    //AdSettings.setDebugBuild(true);
                    AdSettings.clearTestDevices();
                    interstitialFacbookAd.loadAd();
                } else {

                }
            } else {

            }*/
        }

    }

    public void stopTask() {
        if (hourlyTask != null) {
            Log.d("TIMER", "timer canceled");
            hourlyTask.cancel();
        }
    }

    private void PopOpen() {
        preferencesHelper.setBoolean(Config.isPopUpOpen, true);
        apiInterface = RetrofitClient.getRetrofitInstance(this).create(ApiInterface.class);

        Call<PopUpModel> call = apiInterface.popUPOpen();
        call.enqueue(new Callback<PopUpModel>() {
            @Override
            public void onResponse(Call<PopUpModel> call, Response<PopUpModel> response) {

                if (response.isSuccessful()) {
                    Log.d("cjdfjdjf", "ddddd" + response.body().getMessage());

                    showPopUpDialog(response);
                }
            }

            @Override
            public void onFailure(Call<PopUpModel> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("mvkfdsfkldf", "sgsg xx" + t.getMessage());
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });

    }

    private void showPopUpDialog(Response<PopUpModel> response) {
//        View view = View.inflate(MainActivity.this, R.layout.start_alert_raw, null);
        //showHomeScreenFacebookAds(view);
//        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
//        alertDialogBuilder.setView(view);
//        alertDialogBuilder.setCancelable(false);
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.start_alert_raw);
//        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ImageView ivImage = dialog.findViewById(R.id.ivImage);
        ImageView ivCancel = dialog.findViewById(R.id.ivCancel);

        if (response.body().getData().getImage() != null) {
            Glide.with(context).load(Config.IMG_PATH + response.body().getData().getImage()).centerCrop().into(ivImage);
        }
        ivCancel.setOnClickListener(v -> dialog.dismiss());
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (response.body().getData().getLink_type().equals("1")) {
                    startActivity(new Intent(context, SubCategoryActivity.class)
                            .putExtra(SUB_CAT_ID, response.body().getData().getSub_category_id())
                            .putExtra(SUB_CAT_NAME, response.body().getData().getSub_category_name())
                            .putExtra(Config.FROM, NORMAL));
                    dialog.dismiss();
                    // finish();
                }

                if (response.body().getData().getLink_type().equals("2")) {
                    startActivity(new Intent(context, PackageActivity.class));
                    dialog.dismiss();
                    // finish();
                }
                if (response.body().getData().getLink_type().equals("3")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.body().getData().getLink()));
                    startActivity(browserIntent);
                    dialog.dismiss();
                    // finish();
                }
            }
        });

//        alertDialog = alertDialogBuilder.create();
//        alertDialog.setCancelable(false);
//        alertDialog.show();

        dialog.show();
    }

    private void ClickEvents() {
        binding.ivNotification.setOnClickListener(v -> startActivity(new Intent(context, NotificationActivity.class)));
        binding.ivPlan.setOnClickListener(v -> startActivity(new Intent(context, PackageActivity.class)));
        binding.cardUpdateProfile.setOnClickListener(v -> startActivity(new Intent(context, UpdatProfileActivity.class)));
        binding.cardPackageSelect.setOnClickListener(v -> startActivity(new Intent(context, PackageActivity.class)));
        binding.cardLanguageSelection.setOnClickListener(v -> Helper.LanguageDialog(context, ""));
        binding.llAccview.setOnClickListener(v -> SHowAccountlist());
//        binding.fbAddBusniess.setOnClickListener(v -> startActivity(new Intent(context, BusinessProfileActivity.class).putExtra(Config.FROM, Config.ADD_BUSINESS_REGULER)));
        binding.rltbCustomeImage.setOnClickListener(v -> startActivity(new Intent(context, ImageEditingActivity.class).putExtra(Config.FROM, Config.ADD_BUSINESS_REGULER)));
        binding.rltbCustomFrameDemo.setOnClickListener(v -> startActivity(new Intent(context, CustomFrameDemoActivity.class).putExtra(Config.FROM, Config.ADD_BUSINESS_REGULER)));
        binding.rltbCustomeText.setOnClickListener(v -> startActivity(new Intent(context, TextEditingActivity.class).putExtra(Config.FROM, Config.ADD_BUSINESS_REGULER)));
        binding.cardShareSelection.setOnClickListener(v -> Helper.ShareApp(context));
        binding.cardRateUsSelection.setOnClickListener(v -> Helper.rateApp(context));
        binding.cardContactSelection.setOnClickListener(v -> showDialog());
        binding.cardLogoutSelection.setOnClickListener(v -> {
            preferencesHelper.clearData(context);
            preferencesHelper.clearLoginData(context);
            finish();
            startActivity(new Intent(context, OtpActivity.class));
        });

        binding.fbAddBusniess.setOnClickListener(v -> {
            if (businessarrayList != null) {
                if (businessExtraData.getPlan_status())
                    if (businessExtraData.getBusiness_added() < Integer.parseInt(businessExtraData.getBusiness_limit()))
                        startActivity(new Intent(context, BusinessProfileActivity.class).putExtra(Config.FROM, Config.ADD_BUSINESS_REGULER));
                    else
                        Toast.makeText(getApplicationContext(), "your business add limit is over!", Toast.LENGTH_LONG).show();
                else
                    startActivity(new Intent(context, PackageActivity.class));
            } else
                startActivity(new Intent(context, PackageActivity.class));
        });
    }

    String whatsAppNo = "";
    String emailId = "";

    public void showDialog() {

        Log.d("bsxs", "sjasj1");
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.contact_alert_dialog);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        // lp.windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setAttributes(lp);
        ImageView ivPAClose;
        TextView tvContactEmail, tvContactCall;
        ivPAClose = dialog.findViewById(R.id.ivPAClose);
        tvContactEmail = dialog.findViewById(R.id.tvContactEmail);
        tvContactCall = dialog.findViewById(R.id.tvContactCall);
        new GetAppSettingApi(MainActivity.this, new GetAppSetting() {
            @Override
            public void onSuccessResponse(String appNm, String appEmail, String appVersion, String whatsAppNumber) {
                emailId = appEmail;
                whatsAppNo = whatsAppNumber;
            }
        });

        tvContactEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.OpenMailIntentview(context, emailId);
                dialog.dismiss();
            }
        });

        tvContactCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + getString(R.string.mobile_no)));
                startActivity(intent);*/
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/" + whatsAppNo));
                if (whatsAppNo.length() == 10)
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/91" + whatsAppNo));
                context.startActivity(browserIntent);
                dialog.dismiss();
            }
        });

        ivPAClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }

    private void initviews() {
        context = this;
        HideActionBar(this);
        preferencesHelper = new SharedPreferencesHelper(context);
        apiInterface = RetrofitClient.getRetrofitInstance(this).create(ApiInterface.class);
        viewPager = binding.viewPager;
        rvHome = binding.rvHome;
        rvDirectImage = binding.rvDirectImage;
        rvGreetingTopProduct = binding.rvGreetingTopProduct;
        rvGreeting = binding.rvGreeting;
        rvBuinnessList = binding.rvBuinnessList;

        setRecyclerAdapter();
        setRecyclerFImgAdapter();
        initAdapter();
        initFeaturedImageAdapter();
        /*----------------*/
        setGreetingTopProductAdapter();
        initGreetingTopProductAdapter();
        setGreetingRecyclerAdapter();
        GreetinginitAdapter();
        /*----------------*/
        setBusinessRecyclerAdapter();
        BusinessinitAdapter();
        if (Helper.checkInternet(context)) {
            GetHomeData();
        }
        getUpdateAppPopup();
    }

    private void getUpdateAppPopup() {
        apiInterface = RetrofitClient.getRetrofitInstance(this).create(ApiInterface.class);

        Call<UpdateAppModel> call = apiInterface.updateApp();
        call.enqueue(new Callback<UpdateAppModel>() {
            @Override
            public void onResponse(Call<UpdateAppModel> call, Response<UpdateAppModel> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus().equals("true")) {
                        Log.d("jsdsdjs", "okoko" + response.body().getData().getApp_version());
                        Log.d("jsdsdjs", "okoko" + versionCode);

                        if (Integer.parseInt(response.body().getData().getApp_version()) > versionCode) {
                            View view = View.inflate(MainActivity.this, R.layout.updateapp_alert_dialog, null);
                            //showHomeScreenFacebookAds(view);
                            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                            alertDialogBuilder.setView(view);
                            alertDialogBuilder.setCancelable(false);
                            TextView tvUpdateNow = view.findViewById(R.id.tvUpdateNow);
                            TextView tvUpdateLater = view.findViewById(R.id.tvUpdateLater);
                            tvUpdateNow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));

                                }
                            });

                            tvUpdateLater.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    updateAlertDialog.dismiss();
                                }
                            });

                            updateAlertDialog = alertDialogBuilder.create();
                            updateAlertDialog.setCancelable(false);
                            updateAlertDialog.show();
                        } else {
                            if (preferencesHelper.getString(Config.lastDate).matches("")) {
                                Date date = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String formattedDate = df.format(date);
                                preferencesHelper.setString(Config.lastDate, formattedDate);
                                Log.d("fdjfjdfk", "fdfhjdhfjd" + formattedDate);
                                Log.d("dshdhsjdh", "jdsjjdsj" + formattedDate);
                                PopOpen();
                            } else {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    last_date = dateFormat.parse(preferencesHelper.getString(Config.lastDate));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                current_date = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String formattedDate = df.format(current_date);

                                Log.d("dshdhsjdh", "jdsjjdsj" + current_date);
                                Log.d("dshdhsjdh", "jdsjjdsj" + last_date);
                                Log.d("dshdhsjdh", "csdsds" + last_date.after(current_date));

                                if (current_date.after(last_date)) {
                                    if (current_date.getDate() == last_date.getDate()) {

                                        if (preferencesHelper.getBoolean(Config.isPopUpOpen)) {
                                            // PopOpen();
                                            GetUserPlanList();

                                        } else {
                                            Log.d("fdjfjdfk", "yes" + current_date.getDate());
                                            preferencesHelper.setString(Config.lastDate, formattedDate);
                                            PopOpen();
                                        }

                                    } else {
                                        Log.d("fdjfjdfk", "yes" + current_date.getDate());
                                        preferencesHelper.setString(Config.lastDate, formattedDate);
                                        PopOpen();
                                    }

                                } else {
                                    Log.d("fdjfjdfk", "no");
                                    // PopOpen();
                                    GetUserPlanList();

                                }

                            }
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateAppModel> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("jsdsdjs", "sgsg xx" + t.getMessage());
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });

    }

    private void initAcc() {
        if (multipleAccountBottom == null) {
            multipleAccountBottom = new MultipleAccountBottom(binding.txtAccName, binding.profileImage);
        }
    }

    private void SHowAccountlist() {
        multipleAccountBottom.show(getSupportFragmentManager(), MultipleAccountBottom.TAG);
    }

    private void ViewPagerSetup() {
        MyCustomPagerAdapter myCustomPagerAdapter = new MyCustomPagerAdapter(context, banner_list);
        viewPager.setAdapter(myCustomPagerAdapter);
        viewPager.startAutoScroll();
        viewPager.setInterval(3000);
        viewPager.setCycle(true);
        viewPager.setStopScrollWhenTouch(true);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        binding.wormDotsIndicator.setViewPager(viewPager);
    }

    private void setRecyclerAdapter() {
        rvHome.setAdapter(recyclerAdapter);
    }

    private void updateHomelist(List<HomeResponse> list) {
        homeResponseList = list;
        if (recyclerAdapter == null) {
            initAdapter();
        } else {
            recyclerAdapter.updateNewList(list);
        }
        rvHome.setAdapter(recyclerAdapter);
    }

    private void initAdapter() {
        recyclerAdapter = new MyMasterRecyclerAdapter(homeResponseList, R.layout.item_home_list);
        rvHome.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnBind((viewDataBinding, position) -> {
            ItemHomeListBinding binding = (ItemHomeListBinding) viewDataBinding;
            HomeResponse model = homeResponseList.get(position);
            binding.setModel(model);
            SubHomeinitAdapter(model.getDataList(), binding.rvSubHomeList);
        });
    }

    private void setRecyclerFImgAdapter() {
        rvDirectImage.setAdapter(recyclerFIAdapter);
    }

    private void initFeaturedImageAdapter() {
        recyclerFIAdapter = new MyMasterRecyclerAdapter(fearuredImageData, R.layout.item_dimg_data);
        rvDirectImage.setAdapter(recyclerFIAdapter);
        recyclerFIAdapter.setOnBind((viewDataBinding, position) -> {
            ItemDimgDataBinding binding = (ItemDimgDataBinding) viewDataBinding;
            FearuredImage model = fearuredImageData.get(position);
            binding.setModel(model);
            SubFImginitAdapter(model.getDataList(), binding.rvSubHomeList);
        });
    }

    private void updateFImglist(List<FearuredImage> list) {
        fearuredImageData = list;
        if (recyclerFIAdapter == null) {
            initFeaturedImageAdapter();
        } else {
            recyclerFIAdapter.updateNewList(list);
        }
        rvDirectImage.setAdapter(recyclerFIAdapter);
    }

    private void setGreetingTopProductAdapter() {
        rvGreetingTopProduct.setAdapter(GreetingTopRecyclerAdapter);
    }

    private void initGreetingTopProductAdapter() {
        GreetingTopRecyclerAdapter = new MyMasterRecyclerAdapter(GreetingTopProductList, R.layout.item_dimg_data);
        rvGreetingTopProduct.setAdapter(GreetingTopRecyclerAdapter);
        GreetingTopRecyclerAdapter.setOnBind((viewDataBinding, position) -> {
            ItemDimgDataBinding binding = (ItemDimgDataBinding) viewDataBinding;
            FearuredImage model = GreetingTopProductList.get(position);
            binding.setModel(model);
//            SubFImginitAdapter(model.getDataList(), binding.rvSubHomeList);
            SubFImginitAdapterForGreeting(model.getDataList(), binding.rvSubHomeList);
        });
    }

    private void updateGreetingTopProductlist(List<FearuredImage> list) {
        GreetingTopProductList = list;
//        GreetingTopProductList.clear();
//        GreetingTopProductList.addAll(list);
        if (GreetingTopRecyclerAdapter == null) {
            initGreetingTopProductAdapter();
        } else {
            GreetingTopRecyclerAdapter.updateNewList(list);
        }
        rvGreetingTopProduct.setVisibility(View.VISIBLE);
        rvGreetingTopProduct.setAdapter(GreetingTopRecyclerAdapter);
    }


    public static String getCalculatedDate(Date date, String dateFormat, int days) {

      /*  Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date newDate = calendar.getTime();*/

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        try {
            Log.d("fjksjfdsfskfl", "jsdj  " + s.format(new Date(cal.getTimeInMillis())));
            return s.format(new Date(cal.getTimeInMillis()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("TAG", "Error in Parsing Date : " + e.getMessage());
        }
        return null;
    }

    private void GetUserPlanList() {

        RequestBody user_id = RequestBody.create((new SharedPreferencesHelper(context).getString(Config.USER_ID)), MediaType.parse("text/plain"));
        Call<ResponseBody> call = apiInterface.get_user_plan_list(user_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Helper.cancel_loader();
                if (response.isSuccessful()) {
                    try {
                        if (response.isSuccessful()) {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getBoolean("status")) {

                                if (jsonObject.getJSONArray("data").length() > 0) {
                                    Log.d("fsjfjsdfjdsffsd", "jjj");

                                    // new SharedPreferencesHelper(context).setBoolean(Config.Buy_Plan, true);
                                    JSONObject jsonObjects = jsonObject.getJSONArray("data").getJSONObject(0);
                                    Log.d("fjksjfdsfskfl", "fjsjfksk" + jsonObjects.getString("plan_expiry_date"));
                                    Log.d("dkskdkks", "dsjdkjsk" + new SharedPreferencesHelper(context).getBoolean(Config.Buy_Plan));

                                    //  Log.d("fjksjfdsfskfl","hjhjhjj"+getCalculatedDate(jsonObjects.getString("plan_expiry_date"),"yyyy-MM-dd",-2));

                                    currentDate = Calendar.getInstance().getTime();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        expireDate = dateFormat.parse(jsonObjects.getString("plan_expiry_date"));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    if (expireDate.after(currentDate)) {
                                        Log.d("dksdks", "jkj");
                                        testDate = dateFormat.parse(getCalculatedDate(expireDate, "yyyy-MM-dd", -3));
                                        Log.d("dskdjksdjk", "hhsdhhd" + testDate);

                                        if (currentDate.after(testDate)) {
                                            Log.d("hfjdhfh", "if");
                                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                            builder1.setMessage("Your plan is about to expired please renew it now.");
                                            builder1.setCancelable(true);

                                            builder1.setPositiveButton(
                                                    "Renew Now",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                            Config.ifFromPopup = true;
                                                            Intent i = new Intent(context, PackageActivity.class);
                                                            startActivity(i);
                                                        }
                                                    });


                                            AlertDialog alert11 = builder1.create();
                                            alert11.show();
                                        } else {
                                            Log.d("hfjdhfh", "else");
                                        }
                                    } else {
                                        Log.d("dksdks", "jkio");
                                    }

                                } else {
                                    new SharedPreferencesHelper(context).setBoolean(Config.Buy_Plan, false);

                                }
                            } else {
                                new SharedPreferencesHelper(context).setBoolean(Config.Buy_Plan, false);

                            }
                        } else {
                            Toast.makeText(context, getString(R.string.Somethingswrong), Toast.LENGTH_SHORT).show();
                            new SharedPreferencesHelper(context).setBoolean(Config.Buy_Plan, false);

                        }

                    } catch (Exception e) {
                        Helper.cancel_loader();
                        e.printStackTrace();
                        new SharedPreferencesHelper(context).setBoolean(Config.Buy_Plan, false);

                    }

                } else {
                    Helper.cancel_loader();
                    new SharedPreferencesHelper(context).setBoolean(Config.Buy_Plan, false);

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Helper.cancel_loader();
                new SharedPreferencesHelper(context).setBoolean(Config.Buy_Plan, false);

                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });
    }

    private void GetUserPlanData() {

        RequestBody user_id = RequestBody.create((new SharedPreferencesHelper(context).getString(Config.USER_ID)), MediaType.parse("text/plain"));
        Call<ResponseBody> call = apiInterface.get_user_plan_list(user_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Helper.cancel_loader();
                if (response.isSuccessful()) {
                    try {
                        if (response.isSuccessful()) {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getBoolean("status")) {
                                Log.d("fsjfjsdfjdddsffsd", "jjj" + jsonObject.getJSONArray("data").length());

                                if (jsonObject.getJSONArray("data").length() > 0) {
                                    Log.d("fsjfjsdfjdddsffsd", "jjj" + jsonObject.getJSONArray("data").length());

                                    new SharedPreferencesHelper(context).setBoolean(Config.Buy_Plan, true);
                                } else {
                                    new SharedPreferencesHelper(context).setBoolean(Config.Buy_Plan, false);

                                }
                            } else {
                                new SharedPreferencesHelper(context).setBoolean(Config.Buy_Plan, false);

                            }
                        } else {
                            Toast.makeText(context, getString(R.string.Somethingswrong), Toast.LENGTH_SHORT).show();
                            new SharedPreferencesHelper(context).setBoolean(Config.Buy_Plan, false);

                        }

                    } catch (Exception e) {
                        Helper.cancel_loader();
                        e.printStackTrace();
                        new SharedPreferencesHelper(context).setBoolean(Config.Buy_Plan, false);

                    }

                } else {
                    Helper.cancel_loader();
                    new SharedPreferencesHelper(context).setBoolean(Config.Buy_Plan, false);

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Helper.cancel_loader();
                new SharedPreferencesHelper(context).setBoolean(Config.Buy_Plan, false);

                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });
    }

    /*-----------*/
    private void SubHomeinitAdapter(List<HomeResponseData> responseData, RecyclerView recyclerView) {
        MyMasterRecyclerAdapter SubHomerecyclerAdapter = new MyMasterRecyclerAdapter(responseData, R.layout.item_sub_home_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(SubHomerecyclerAdapter);
        SubHomerecyclerAdapter.setOnBind((viewDataBinding, position) -> {
            ItemSubHomeListBinding binding = (ItemSubHomeListBinding) viewDataBinding;
            HomeResponseData model = responseData.get(position);
            binding.setModel(model);
            if (!model.getSubCategoryDate().isEmpty()) {
                if (!model.getSubCategoryDate().equals("")) {
                    binding.txtDate.setVisibility(View.VISIBLE);
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        Log.d("dsdhsh", "jdjsjdk" + model.getSub_category_status());
                        date = dateFormatter.parse(model.getSubCategoryDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat timeFormatter = new SimpleDateFormat("dd MMM");
                    binding.txtDate.setText(timeFormatter.format(date));

                } else {
                    binding.txtDate.setVisibility(View.GONE);
                }
            }
            Glide.with(context).load(Config.IMG_PATH + model.getSubCategoryImage()).into(binding.imageView);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, SubCategoryActivity.class).putExtra(SUB_CAT_ID, model.getSubCategoryId()).putExtra(SUB_CAT_NAME, model.getSubCategoryName()).putExtra(Config.FROM, NORMAL));
                }
            });
        });
    }

    private void SubFImginitAdapter(List<FearuredImageData> responseData, RecyclerView recyclerView) {
        MyMasterRecyclerAdapter SubHomerecyclerAdapter = new MyMasterRecyclerAdapter(responseData, R.layout.item_sub_dimg_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(SubHomerecyclerAdapter);

        SubHomerecyclerAdapter.setOnBind((viewDataBinding, position) -> {
            ItemSubDimgListBinding binding = (ItemSubDimgListBinding) viewDataBinding;
            FearuredImageData model = responseData.get(position);
            binding.setModel(model);
            if (model.getProduct_type().equals("FREE") || new SharedPreferencesHelper(context).getBoolean(Config.Buy_Plan)) {
                binding.rlPremium.setVisibility(View.GONE);
            } else {
                binding.rlPremium.setVisibility(View.VISIBLE);
            }
           /* if (!model.getSubCategoryDate().isEmpty()) {
                if (!model.getSubCategoryDate().equals("")) {
                    binding.txtDate.setVisibility(View.VISIBLE);
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        Log.d("dsdhsh","jdjsjdk"+model.getSub_category_status());
                        date = dateFormatter.parse(model.getSubCategoryDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat timeFormatter = new SimpleDateFormat("dd MMM");
                    binding.txtDate.setText(timeFormatter.format(date));

                } else {
                    binding.txtDate.setVisibility(View.GONE);
                }
            }*/
            Glide.with(context).load(Config.IMG_PATH + model.getProduct_image()).into(binding.imageView);
            binding.getRoot().setOnClickListener(v -> {
                if (model.getProduct_type().equals("FREE") || new SharedPreferencesHelper(context).getBoolean(Config.Buy_Plan)) {
                    /*if (getIntent().getStringExtra((Config.FROM)).equals(Config.GREETING) || model.getDisplay_section().equalsIgnoreCase("GREETINGS")) {
                        startActivity(new Intent(context, GreetingBannerEditingActivity.class)
                                .putExtra(Config.IMAGE_URL, Config.IMG_PATH + model.getProduct_image())
                                .putExtra(Config.PRODUCT_ID, model.getProduct_id())
                                .putExtra(Config.SUB_CAT_ID_1, model.getSub_category_id())
                                .putExtra(Config.FORGROUND_IMAGE, Config.IMG_PATH + model.getForeground_image()));
                    } else {*/
                    startActivity(new Intent(context, BannerEditingActivity.class)
                            .putExtra(Config.IMAGE_URL, Config.IMG_PATH + model.getProduct_image())
                            .putExtra(Config.SUB_CAT_ID_1, model.getSub_category_id())
                            .putExtra(Config.PRODUCT_ID, model.getProduct_id()));
                    /*}*/
                } else {
                    startActivity(new Intent(context, PackageActivity.class));
                }
            });

            // startActivity(new Intent(context, SubCategoryActivity.class).putExtra(SUB_CAT_ID, model.getSubCategoryId()).putExtra(SUB_CAT_NAME, model.getSubCategoryName()).putExtra(Config.FROM, NORMAL)));
        });
    }

    private void SubFImginitAdapterForGreeting(List<FearuredImageData> responseData, RecyclerView recyclerView) {
        MyMasterRecyclerAdapter SubHomerecyclerAdapter = new MyMasterRecyclerAdapter(responseData, R.layout.item_sub_dimg_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(SubHomerecyclerAdapter);

        SubHomerecyclerAdapter.setOnBind((viewDataBinding, position) -> {
            ItemSubDimgListBinding binding = (ItemSubDimgListBinding) viewDataBinding;
            FearuredImageData model = responseData.get(position);
            binding.setModel(model);
            if (model.getProduct_type().equals("FREE") || new SharedPreferencesHelper(context).getBoolean(Config.Buy_Plan)) {
                binding.rlPremium.setVisibility(View.GONE);
            } else {
                binding.rlPremium.setVisibility(View.VISIBLE);
            }
            Glide.with(context).load(Config.IMG_PATH + model.getProduct_image()).into(binding.imageView);
            binding.getRoot().setOnClickListener(v -> {
                if (model.getProduct_type().equals("FREE") || new SharedPreferencesHelper(context).getBoolean(Config.Buy_Plan)) {
                    startActivity(new Intent(context, GreetingBannerEditingActivity.class)
                            .putExtra(Config.IMAGE_URL, Config.IMG_PATH + model.getProduct_image())
                            .putExtra(Config.PRODUCT_ID, model.getProduct_id())
                            .putExtra(Config.SUB_CAT_ID_1, model.getSub_category_id())
                            .putExtra(Config.FORGROUND_IMAGE, Config.IMG_PATH + model.getForeground_image()));
                } else {
                    startActivity(new Intent(context, PackageActivity.class));
                }
            });

            // startActivity(new Intent(context, SubCategoryActivity.class).putExtra(SUB_CAT_ID, model.getSubCategoryId()).putExtra(SUB_CAT_NAME, model.getSubCategoryName()).putExtra(Config.FROM, NORMAL)));
        });
    }

    /*-----------*/
    private void setGreetingRecyclerAdapter() {
        rvGreeting.setLayoutManager(new GridLayoutManager(this, 1));
        rvGreeting.setAdapter(GreetingrecyclerAdapter);
    }

    private void GreetingUpdateProductlist(List<GreetingReponse> list) {
        Log.d("main", "greeting-=-===>>>" + list.size());
        GreetingarrayList = list;
        if (GreetingrecyclerAdapter == null) {
            GreetinginitAdapter();
        } else {
            GreetingrecyclerAdapter.updateNewList(list);
        }
        rvGreeting.setAdapter(GreetingrecyclerAdapter);
    }

    private void GreetinginitAdapter() {
        GreetingrecyclerAdapter = new MyMasterRecyclerAdapter(GreetingarrayList, R.layout.item_greeting);
        rvGreeting.setAdapter(GreetingrecyclerAdapter);
        GreetingrecyclerAdapter.setOnBind((viewDataBinding, position) -> {
            ItemGreetingBinding binding = (ItemGreetingBinding) viewDataBinding;
            GreetingReponse model = GreetingarrayList.get(position);
            binding.setModel(model);
            SubGreetinginitAdapter(model.getGreetingListResponses(), binding.rvSubGreetingList);
        });
    }

    /*-----------*/
    private void SubGreetinginitAdapter(List<GreetingListResponse> greetingListResponses, RecyclerView recyclerView) {
        MyMasterRecyclerAdapter SubGreetingrecyclerAdapter = new MyMasterRecyclerAdapter(greetingListResponses, R.layout.item_sub_greeting);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(SubGreetingrecyclerAdapter);
        SubGreetingrecyclerAdapter.setOnBind((viewDataBinding, position) -> {
            ItemSubGreetingBinding binding = (ItemSubGreetingBinding) viewDataBinding;
            GreetingListResponse model = greetingListResponses.get(position);
            binding.setModel(model);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, SubCategoryActivity.class).
                            putExtra(SUB_CAT_ID, model.getSubCategoryId()).
                            putExtra(SUB_CAT_NAME, model.getSubCategoryName()).putExtra(Config.FROM, GREETING));
                }
            });
            Glide.with(context).load(Config.IMG_PATH + model.getSubCategoryImage()).into(binding.imageView);

        });
    }

    /*-----------*/

    private void setBusinessRecyclerAdapter() {
        rvBuinnessList.setLayoutManager(new GridLayoutManager(this, 1));
        rvBuinnessList.setAdapter(BuisnessListAdapter);
    }

    private void BusinessUpdateProductlist(List<BusinessResponseData> list) {
        businessarrayList = list;
        if (BuisnessListAdapter == null) {
            BusinessinitAdapter();
        } else {
            BuisnessListAdapter.updateNewList(list);
        }
        rvBuinnessList.setAdapter(BuisnessListAdapter);
        if (businessarrayList.size() == 0) {
            Log.e("Count", "RemoveBusinessDatatoStorage");
            SharedPreferencesHelper.saveObjectToSharedPreference(context, STORAGE_BUSINESS_ACCOUNT, new BusinessResponseData());
            binding.txtAccName.setText(getResources().getString(R.string.Business_Name));
            Glide.with(context).load(R.drawable.ic_adduser).into(binding.profileImage);
        }
    }

    private void BusinessinitAdapter() {
        BuisnessListAdapter = new MyMasterRecyclerAdapter(businessarrayList, R.layout.item_buisness_list);
        rvBuinnessList.setAdapter(BuisnessListAdapter);
        BuisnessListAdapter.setOnBind((viewDataBinding, position) -> {
            ItemBuisnessListBinding binding = (ItemBuisnessListBinding) viewDataBinding;
            BusinessResponseData model = businessarrayList.get(position);
            binding.setModel(model);
            Glide.with(context).load(Config.IMG_PATH + model.getBusinessLogo()).into(binding.imageView);
            binding.ivEdit.setOnClickListener(v -> {
                Gson gson = new Gson();
                String Buisnessobj = gson.toJson(model);
                Intent intent = new Intent(context, BusinessProfileActivity.class);
                intent.putExtra(Config.FROM, Config.EDIT_BUSINESS);
                intent.putExtra(Config.DATA, Buisnessobj);
                startActivity(intent);
            });


            binding.imbDelete.setOnClickListener(v -> {
                ShowAlert(model, position);
            });
        });
    }

    private void BottomClick() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
                AllViewVisblityGone();
                Headerview(true);
                switch (item.getItemId()) {
                    case R.id.navigation_Home:
                        binding.llHome.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.navigation_gretting:
                        binding.llGreeting.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.navigation_account:
                        Headerview(false);
                        binding.llProfile.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.navigation_business:
                        Headerview(false);
                        binding.llBusinessList.setVisibility(View.VISIBLE);
                        binding.fbAddBusniess.setVisibility(View.VISIBLE);
                        return true;

                }
                return false;
            };

    private void AllViewVisblityGone() {
//        binding.nestedScrollView.fullScroll(View.FOCUS_UP);
        binding.nestedScrollView.scrollTo(0, 0);
        binding.llHome.setVisibility(View.GONE);
        binding.llGreeting.setVisibility(View.GONE);
        binding.llProfile.setVisibility(View.GONE);
        binding.llBusinessList.setVisibility(View.GONE);
        binding.fbAddBusniess.setVisibility(View.GONE);
    }

    private void Headerview(boolean isview) {
        if (isview) {
            binding.rltvacc.setVisibility(View.VISIBLE);
        } else {
            binding.rltvacc.setVisibility(View.GONE);
        }
    }

    private void GetHomeData() {
        Helper.Show_loader(context, false, false);
        String user_id = new SharedPreferencesHelper(context).getString(Config.USER_ID);
        Log.d("sdsdddddddd", "sfsjf" + FCM_TOKEN);
        Call<ResponseBody> call = apiInterface.get_home_screen_data(user_id, FCM_TOKEN);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        HandleHomeData(jsonObject);
//                        GetGreetingData();
                        GetGreetingTopProductData();
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
                GetGreetingTopProductData();
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });

    }

    private void GetGreetingTopProductData() {
        Helper.Show_loader(context, false, false);
        Call<ResponseBody> call = apiInterface.get_greeting_products();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
//                        HandleHomeData(jsonObject);
                        try {
                            GetGreetingTopProductSection(jsonObject.getJSONObject("data"));
                        } catch (Exception e) {
                            rvGreetingTopProduct.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                        GetGreetingData();
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
                GetGreetingData();
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });

    }

    private void GetGreetingData() {
        Helper.Show_loader(context, false, false);
        Call<ResponseBody> call = apiInterface.get_greetings_data();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        HandleGreeting(jsonObject.getJSONObject("data").getJSONObject("greeting_section"));
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

    private void HandleGreeting(JSONObject jsonObject) {
        try {
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                GreetingReponse greetingReponse = new GreetingReponse();
                greetingReponse.setGreeting_section(key);
                /*-------*/
                Gson gson = new Gson();
                Type type = new TypeToken<List<GreetingListResponse>>() {
                }.getType();
                List<GreetingListResponse> dataList = gson.fromJson(jsonObject.getJSONArray(key).toString(), type);
                greetingReponse.setGreetingListResponses(dataList);
                GreetingarrayList.add(greetingReponse);
            }
            GreetingUpdateProductlist(GreetingarrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void HandleHomeData(JSONObject jsonObject) {
        try {
            JSONArray Banner_Array = jsonObject.getJSONObject("data").getJSONArray("banner_section");

            Log.d("sdsdj", "sdfshd" + Banner_Array.toString());
            BannerSetup(Banner_Array);
            HomeRvSection(jsonObject.getJSONObject("data").getJSONObject("category_section"));
            if (jsonObject.getJSONObject("data").getJSONObject("home_products") != null) {
                HomeFISection(jsonObject.getJSONObject("data").getJSONObject("home_products"));

            } else {
                binding.llFImage.setVisibility(View.GONE);
                //  binding.txtTitle.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void HomeRvSection(JSONObject jsonObject) {
        try {

            Iterator<String> keys = jsonObject.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                HomeResponse homeResponse = new HomeResponse();
                homeResponse.setCategory_section(key);
                /*-------*/
                Gson gson = new Gson();
                Type type = new TypeToken<List<HomeResponseData>>() {
                }.getType();
                List<HomeResponseData> dataList = gson.fromJson(jsonObject.getJSONArray(key).toString(), type);
                homeResponse.setDataList(dataList);
                homeResponseList.add(homeResponse);
            }
            updateHomelist(homeResponseList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setRIData() {
        FeaturedImageAdepter featuredImageAdepter = new FeaturedImageAdepter(context, R.layout.item_sub_home_list, fearuredImageList);
        binding.rvFI.setAdapter(featuredImageAdepter);
    }

    private void HomeFISection(JSONObject jsonObject) {
        try {
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                FearuredImage fearuredImage = new FearuredImage();
                fearuredImage.setCategory_section(key);
                /*-------*/
                Gson gson = new Gson();
                Type type = new TypeToken<List<FearuredImageData>>() {
                }.getType();
                ArrayList<FearuredImageData> dataList = gson.fromJson(jsonObject.getJSONArray(key).toString(), type);
                Log.d("jfkdfjkdjfk", "jdkfjdjkfjdjfk" + dataList.get(0).getProduct_id());
                // Toast.makeText(context,"jksjdksjdsj"+dataList.get(0).getProduct_id(),Toast.LENGTH_LONG).show();
                fearuredImage.setDataList(dataList);
                fearuredImageData.add(fearuredImage);
            }
            Log.d("sdskdksdkl", "hdjhsjhdjshj" + fearuredImageData.get(0).getDataList().size());

            // fearuredImageList.addAll(fearuredImageData.get(0).getDataList());
            // String abc=fearuredImageList.get(0).getProduct_id().toString();
            //Log.d("jdskfjkdsfjkd","fdkfjdfjdkj"+abc);

            setRIData();
            updateFImglist(fearuredImageData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetGreetingTopProductSection(JSONObject jsonObject) {
        try {
            Iterator<String> keys = jsonObject.keys();
//            GreetingTopProductList.clear();
            while (keys.hasNext()) {
                String key = keys.next();
                FearuredImage fearuredImage = new FearuredImage();
                fearuredImage.setCategory_section(key);
                /*-------*/
                Gson gson = new Gson();
                Type type = new TypeToken<List<FearuredImageData>>() {
                }.getType();
                ArrayList<FearuredImageData> dataList = gson.fromJson(jsonObject.getJSONArray(key).toString(), type);
                Log.d("jfkdfjkdjfk", "jdkfjdjkfjdjfk" + dataList.get(0).getProduct_id());
                // Toast.makeText(context,"jksjdksjdsj"+dataList.get(0).getProduct_id(),Toast.LENGTH_LONG).show();
                fearuredImage.setDataList(dataList);
                GreetingTopProductList.add(fearuredImage);
            }
            Log.d("sdskdksdkl", "hdjhsjhdjshj" + GreetingTopProductList.get(0).getDataList().size());

            // fearuredImageList.addAll(GreetingTopProductList.get(0).getDataList());
            // String abc=fearuredImageList.get(0).getProduct_id().toString();
            //Log.d("jdskfjkdsfjkd","fdkfjdfjdkj"+abc);

            setRIData();
            updateGreetingTopProductlist(GreetingTopProductList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void BannerSetup(JSONArray banner_array) {

        Gson gson = new Gson();
        Type type = new TypeToken<List<BannerSection>>() {
        }.getType();
        banner_list = gson.fromJson(banner_array.toString(), type);
        ViewPagerSetup();
    }

    private void GetBuinsessList() {
        RequestBody user_id = RequestBody.create(new SharedPreferencesHelper(context).getString(Config.USER_ID), MediaType.parse("text/plain"));
        businessarrayList.clear();
        Call<BusinessResponse> call = apiInterface.get_user_business_list(user_id);
        call.enqueue(new Callback<BusinessResponse>() {
            @Override
            public void onResponse(Call<BusinessResponse> call, Response<BusinessResponse> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        BusinessUpdateProductlist(response.body().getData());
                        businessExtraData = response.body().getExtraData();
                        if (preferencesHelper.getBoolean(SELECT_ACCOUNT)) {
                            SelectionLoop(response.body().getData());
                        } else {
                            binding.txtAccName.setText(response.body().getData().get(0).getBusinessName());
                            Glide.with(context).load(Config.IMG_PATH + response.body().getData().get(0).getBusinessLogo()).into(binding.profileImage);
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
            public void onFailure(Call<BusinessResponse> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });

    }

    private void ShowAlert(BusinessResponseData model, int position) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.Are_you_sure))
                .setContentText(getString(R.string.Are_you_sure_delete))
                .setConfirmText("Yes")
                .setConfirmClickListener(sDialog -> {
                    // reuse previous dialog instance
                    sDialog.dismiss();
                    if (Helper.checkInternet(context)) {
                        DeleteBusiness(model, position);
                    }
                })
                .setCancelText("No")
                .setCancelClickListener(Dialog::dismiss)
                .show();
    }

    private void DeleteBusiness(BusinessResponseData model, int position) {
        Helper.Show_loader(context, false, false);
        RequestBody business_id = RequestBody.create(model.getBusinessId(), MediaType.parse("text/plain"));
        Call<ResponseBody> call = apiInterface.delete_business(business_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        if (response.isSuccessful()) {
                            String responseString = response.body().string();
                            JSONObject object = new JSONObject(responseString);
                            if (object.getBoolean("status")) {
                                if (model.getBusinessId().equals(new SharedPreferencesHelper(context).getString(SELECT_ACCOUNT_ID))) {
                                    /*int count = businessarrayList.size() - 1;
                                    Log.e("Count", String.valueOf(count));
                                    if (count != 0) {
                                        new SharedPreferencesHelper(context).setString(SELECT_ACCOUNT_ID, businessarrayList.get(0).getBusinessId());
                                        SharedPreferencesHelper.saveObjectToSharedPreference(context, STORAGE_BUSINESS_ACCOUNT, businessarrayList.get(0));
                                    } else {
                                        Log.e("Count", "RemoveBusinessDatatoStorage");
                                        SharedPreferencesHelper.saveObjectToSharedPreference(context, STORAGE_BUSINESS_ACCOUNT, new BusinessResponseData());
                                    }*/
                                }
                                if (Helper.checkInternet(context)) {
                                    GetBuinsessList();
                                }
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

    @Override
    protected void onResume() {
        super.onResume();
        initAcc();
        BottomClick();
        ClickEvents();

        if (Helper.checkInternet(context)) {
            GetBuinsessList();
        }
    }

    private void SelectionLoop(List<BusinessResponseData> arrayList) {
        Log.e("SELECT_ACCOUNT_ID", preferencesHelper.getString(SELECT_ACCOUNT_ID));
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getBusinessId().equals(preferencesHelper.getString(SELECT_ACCOUNT_ID))) {
                Log.e("getBusinessId", "SAMEe");
                binding.txtAccName.setText(arrayList.get(i).getBusinessName());
                Glide.with(context).load(Config.IMG_PATH + arrayList.get(i).getBusinessLogo()).into(binding.profileImage);
                arrayList.get(i).setSetselectedAcc(true);
                break;
            }
        }

    }
}