package com.marwadibrothers.mbstatus.activity.editing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.facebook.ads.Ad;
//import com.facebook.ads.AdSettings;
//import com.facebook.ads.AudienceNetworkAds;
//import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.marwadibrothers.mbstatus.BuildConfig;
import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.activity.BannerEditingActivity;
import com.marwadibrothers.mbstatus.activity.BusinessProfileActivity;
import com.marwadibrothers.mbstatus.activity.MainActivity;
import com.marwadibrothers.mbstatus.activity.PackageActivity;
import com.marwadibrothers.mbstatus.activity.ProfileMakeActivity;
import com.marwadibrothers.mbstatus.activity.SubCategoryActivity;
import com.marwadibrothers.mbstatus.adapter.FontsAdapter;
import com.marwadibrothers.mbstatus.adapter.FramePersonalSliderAdepter;
import com.marwadibrothers.mbstatus.adapter.FrameSliderAdepter;
import com.marwadibrothers.mbstatus.adapter.MyMasterRecyclerAdapter;
import com.marwadibrothers.mbstatus.databinding.ActivityImageEditingBinding;
import com.marwadibrothers.mbstatus.databinding.ItemFooterStyleBinding;

import com.marwadibrothers.mbstatus.models.UpcomingModel;
import com.marwadibrothers.mbstatus.models.WaterMark;
import com.marwadibrothers.mbstatus.models.buiness.BusinessResponseData;
import com.marwadibrothers.mbstatus.models.home.UpcomingEventsSection;
import com.marwadibrothers.mbstatus.models.profille.MyProfileResponse;
import com.marwadibrothers.mbstatus.models.profille.MyProfileResponseData;
import com.marwadibrothers.mbstatus.sevices.ApiInterface;
import com.marwadibrothers.mbstatus.sevices.RetrofitClient;
import com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper;
import com.marwadibrothers.mbstatus.utils.Config;
import com.marwadibrothers.mbstatus.utils.Conts;
import com.marwadibrothers.mbstatus.utils.Helper;
import com.marwadibrothers.mbstatus.weidget.SweetAlert.SweetAlertDialog;
import com.marwadibrothers.mbstatus.weidget.entity.ImageEntity;
import com.marwadibrothers.mbstatus.weidget.entity.MotionEntity;
import com.marwadibrothers.mbstatus.weidget.entity.MotionView;
import com.marwadibrothers.mbstatus.weidget.entity.TextEntity;
import com.marwadibrothers.mbstatus.weidget.utils.FontProvider;
import com.marwadibrothers.mbstatus.weidget.utils.TextEditorDialogFragment;
import com.marwadibrothers.mbstatus.weidget.viewmodel.Font;
import com.marwadibrothers.mbstatus.weidget.viewmodel.Layer;
import com.marwadibrothers.mbstatus.weidget.viewmodel.TextLayer;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwadibrothers.mbstatus.activity.SplashScreenActivity.FCM_TOKEN;
import static com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper.STORAGE_BUSINESS_ACCOUNT;
import static com.marwadibrothers.mbstatus.utils.Helper.HideActionBar;

public class ImageEditingActivity extends AppCompatActivity implements TextEditorDialogFragment.OnTextLayerCallback {
    private ActivityImageEditingBinding binding;
    private RecyclerView rvFrame;
    private MyMasterRecyclerAdapter recyclerAdapter;

    private Context context;
    protected MotionView motionView;
    private FontProvider fontProvider;
    private ApiInterface apiInterface, apiInterface1;
    private boolean IsImage = false;
    private boolean IsFooterBackground = false;
    private String ColorStatus = "normal";
    private TextEntity CompanyTitletextEntity;
    private BusinessResponseData model;
    private List<Integer> imgarrayList = new ArrayList<>();
    Dialog dialog, downloadDialog, profileDialog;
    public InterstitialAd mInterstitialAd;
    String comeFrom = "watermark", selectProfile = "personal";
    public RewardedAd mRewardedAd;
    protected static final int REQUEST_CODE_GALLERY = 102;
    public static ImageEditingActivity instance = null;
    int padding;
    boolean profileEmpty, businessEmpty;

    public ImageEditingActivity() {
        instance = ImageEditingActivity.this;
    }

    public static synchronized ImageEditingActivity getInstance() {
        if (instance == null) {
            instance = new ImageEditingActivity();
        }
        return instance;
    }

//    public com.facebook.ads.InterstitialAd interstitialFacbookAd;
//    public InterstitialAdListener interstitialAdListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        Config.Mobile_No = new SharedPreferencesHelper(context).getString(Config.USER_NO);
        setContentView(R.layout.activity_image_editing);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_editing);
        dialog = new Dialog(context);
        downloadDialog = new Dialog(context);
        getWaterMark();
        binding.tvSwipe.setText("<< Swipe to change Frame >>");

//        initAD();
        ViewTreeObserver viewTreeObserver = binding.rlMain.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    binding.rlMain.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int viewHeight = binding.rlMain.getHeight();
                    int viewWeight = binding.rlMain.getWidth();
                    binding.rlMain.setLayoutParams(new LinearLayout.LayoutParams(viewWeight, viewWeight));

                    Log.d("cxdjhjshj", "djsjdsjd" + viewHeight);
                    Log.d("cxdjhjshj", "djsjdsjd" + viewWeight);
                    Log.d("cxdjhjshj", "djsjdsjd" + binding.rlMain.getHeight());
                    Log.d("cxdjhjshj", "djsjdsjd" + binding.rlMain.getWidth());
                }
            });
        }

        binding.toolbarSupport.llShare.setVisibility(View.VISIBLE);
        binding.toolbarSupport.llShowNextView.setVisibility(View.VISIBLE);
        binding.toolbarSupport.ivprofileselection.setVisibility(View.GONE);
        binding.toolbarSupport.txttitle.setVisibility(View.GONE);
        binding.toolbarSupport.ivDelete.setVisibility(View.VISIBLE);
        binding.toolbarSupport.ivDownload.setVisibility(View.GONE);
        binding.toolbarSupport.ivShare.setVisibility(View.GONE);

        binding.vpPersonalFrame.setVisibility(View.GONE);
        binding.vpFrame.setVisibility(View.GONE);
        binding.txtPersonal.setVisibility(View.GONE);
        binding.txtBusiness.setVisibility(View.GONE);
        binding.txtPersonal.setBackgroundResource(R.drawable.btn_bg);
        binding.txtPersonal.setTextColor(getResources().getColor(R.color.white));
        binding.txtBusiness.setBackgroundResource(R.drawable.daily_img_bg);
        binding.txtBusiness.setTextColor(getResources().getColor(R.color.main_color));
        binding.toolbarSupport.txttitle.setText(R.string.Update_Profile);
        binding.ivfooterLogo.setVisibility(View.GONE);
        binding.txtPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectProfile = "personal";
                GetUserPlanList();
                binding.vpPersonalFrame.setVisibility(View.VISIBLE);
                binding.vpFrame.setVisibility(View.GONE);
                binding.txtPersonal.setBackgroundResource(R.drawable.btn_bg);
                binding.txtPersonal.setTextColor(getResources().getColor(R.color.white));
                binding.txtBusiness.setBackgroundResource(R.drawable.daily_img_bg);
                binding.txtBusiness.setTextColor(getResources().getColor(R.color.main_color));
                binding.ivfooterLogo.setVisibility(View.GONE);
            }
        });
        binding.txtBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectProfile = "business";
                GetUserPlanList();
                binding.vpPersonalFrame.setVisibility(View.GONE);
                binding.vpFrame.setVisibility(View.VISIBLE);
                binding.txtPersonal.setBackgroundResource(R.drawable.daily_img_bg);
                binding.txtBusiness.setBackgroundResource(R.drawable.btn_bg);
                binding.txtPersonal.setTextColor(getResources().getColor(R.color.main_color));
                binding.txtBusiness.setTextColor(getResources().getColor(R.color.white));
                binding.ivfooterLogo.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Config.Mobile_No = new SharedPreferencesHelper(context).getString(Config.USER_NO);

        if (Config.isProfileDone && Config.isBusinessDone) {
            dialog.dismiss();
        }
        GetUserPlanList();
        Config.isFb = new SharedPreferencesHelper(context).getBoolean(Config.isFbs);
        Config.isInsta = new SharedPreferencesHelper(context).getBoolean(Config.isInstas);
        Config.isYoutube = new SharedPreferencesHelper(context).getBoolean(Config.isYoutubes);
        Config.isWhatsapp = new SharedPreferencesHelper(context).getBoolean(Config.isWhatsapps);


    }

    public void setLogoVisibility(boolean isVisible) {
        if (isVisible) {
            binding.ivfooterLogo.setVisibility(View.VISIBLE);

        } else {
            binding.ivfooterLogo.setVisibility(View.GONE);

        }
    }


    public void showDialog() {

        Log.d("bsxs", "sjasj1");
        dialog.setContentView(R.layout.profile_alert_dialog);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        // lp.windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setAttributes(lp);
        ImageView ivPAClose;
        TextView tvPAProfile, tvPABusiness, tvText;
        ivPAClose = dialog.findViewById(R.id.ivPAClose);
        tvPAProfile = dialog.findViewById(R.id.tvPAProfile);
        tvPABusiness = dialog.findViewById(R.id.tvPABusiness);
        tvText = dialog.findViewById(R.id.tvText);
        ivPAClose.setVisibility(View.VISIBLE);


        if (selectProfile.equals("personal") && profileEmpty) {
            tvText.setText(getString(R.string.profile_alert_string_1));
            tvPABusiness.setVisibility(View.GONE);
        } else if (selectProfile.equals("business") && businessEmpty) {
            tvText.setText(getString(R.string.profile_alert_string_2));
            tvPAProfile.setVisibility(View.GONE);
        } else {
            dialog.dismiss();
        }
        tvPABusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.ADD_BUSINESS = "ADD_BUSINESS";
                startActivity(new Intent(context, BusinessProfileActivity.class).putExtra(Config.FROM, Config.ADD_BUSINESS));

            }
        });

        tvPAProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ProfileMakeActivity.class).putExtra(Config.MOBILE, Config.Mobile_No));

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


    public void DownloadDialog() {

        Log.d("bsxs", "sjasj1");
        //downloadDialog=new Dialog(context);
        downloadDialog.setContentView(R.layout.download_alert_dialog);
        downloadDialog.getWindow().setGravity(Gravity.CENTER);
        downloadDialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(downloadDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        // lp.windowAnimations = R.style.DialogAnimation;
        downloadDialog.getWindow().setAttributes(lp);
        ImageView ivDPAClose;
        TextView tvShowAd, tvBuyPlan;
        ivDPAClose = downloadDialog.findViewById(R.id.ivDPAClose);
        tvShowAd = downloadDialog.findViewById(R.id.tvShowAd);
        tvBuyPlan = downloadDialog.findViewById(R.id.tvBuyPlan);

        tvBuyPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, PackageActivity.class));
            }
        });

        ivDPAClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadDialog.dismiss();

            }
        });

        tvShowAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadDialog.dismiss();

                MainActivity.instance.stopTask();
//                diplayFacebookad();
               /* if (comeFrom.equals("save") || comeFrom.equals("Share")) {
                    download();
                } else {
                    Log.d("dhsdh", "dsjdj");
                    binding.llWaterMark.setVisibility(View.GONE);
                }*/
                fullADInit(ImageEditingActivity.this);
            }
        });
        downloadDialog.show();

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
                MainActivity.instance.StartTimer();
                if (comeFrom.equals("save") || comeFrom.equals("Share")) {
                    download();
                } else {
                    Log.d("dhsdh", "dsjdj");
                    binding.llWaterMark.setVisibility(View.GONE);
                }

            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                // Ad error callback
                // Interstitial dismissed callback
                MainActivity.instance.StartTimer();
                if (comeFrom.equals("save") || comeFrom.equals("Share")) {
                    download();
                } else {
                    Log.d("dhsdh", "dsjdj");
                    binding.llWaterMark.setVisibility(View.GONE);
                }

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

                //AdSettings.setDebugBuild(true);
                AdSettings.clearTestDevices();
                interstitialFacbookAd.loadAd();
            } else {

            }
        } else {

        }*/
    }

    public void fullADInit(Activity activity) {


        AdRequest adRequest = new AdRequest.Builder().build();
        Log.d("ujuuj", "initialize");

        RewardedAd.load(context, getString(R.string.rewarded_ads),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d("klcxck", loadAdError.getMessage());
                        mRewardedAd = null;
                        /*if (comeFrom.equals("save") || comeFrom.equals("Share")) {
                            download();
                        } else {
                            Log.d("dhsdh", "dsjdj");
                            binding.llWaterMark.setVisibility(View.GONE);
                        }*/
                        showFbRewardedAd();
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        adMobInitialListioner();
                        Log.d("klcxck", "Ad was loaded.");
                    }


                });
    }

    public void adMobInitialListioner() {
        if (mRewardedAd != null) {

            mRewardedAd.show((Activity) context, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d("klcxck", "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                    if (comeFrom.equals("save") || comeFrom.equals("Share")) {
                        download();
                    } else {
                        Log.d("dhsdh", "dsjdj");
                        binding.llWaterMark.setVisibility(View.GONE);
                    }
                }
            });

            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Log.d("klcxck", "Ad was shown.");
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // Called when ad fails to show.
                    Log.d("klcxck", "Ad failed to show.");
                    /*if (comeFrom.equals("save") || comeFrom.equals("Share")) {
                        download();
                    } else {
                        Log.d("dhsdh", "dsjdj");
                        binding.llWaterMark.setVisibility(View.GONE);
                    }*/
                    showFbRewardedAd();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    // Set the ad reference to null so you don't show the ad a second time.
                    Log.d("klcxck", "Ad was dismissed.");
                    /*downloadDialog.dismiss();
                    if (comeFrom.equals("save") || comeFrom.equals("Share")) {
                        download();
                    } else {
                        Log.d("dhsdh", "dsjdj");
                        binding.llWaterMark.setVisibility(View.GONE);
                    }*/
                    // DownloadStickerFragment.getInstance().adInitialize();
                }


            });
        } else {
            Log.d("klcxck", "The rewarded ad wasn't ready yet.");
        }

    }

    private final String TAG = BannerEditingActivity.class.getSimpleName();
    private RewardedVideoAd rewardedVideoAd;
    private void showFbRewardedAd(){
        AudienceNetworkAds.initialize(this);

        rewardedVideoAd = new RewardedVideoAd(this, getResources().getString(R.string.facebook_rewarded_Ad));
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, com.facebook.ads.AdError error) {
                // Rewarded video ad failed to load
                Log.e(TAG, "Rewarded video ad failed to load: " + error.getErrorMessage());
                Helper.cancel_loader();

                MainActivity.instance.StartTimer();
                if (comeFrom.equals("save") || comeFrom.equals("Share")) {
                    download();
                } else {
                    Log.d("dhsdh", "failed WaterM-=-=-=->>>>>" +"dsjdj");
                    binding.llWaterMark.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Rewarded video ad is loaded and ready to be displayed
                Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");
                Helper.cancel_loader();
                rewardedVideoAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Rewarded video ad clicked
                Log.d(TAG, "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Rewarded Video ad impression - the event will fire when the
                // video starts playing
                Log.d(TAG, "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {
                // Rewarded Video View Complete - the video has been played to the end.
                // You can use this event to initialize your reward
                Log.d(TAG, "Rewarded video completed!");

                // Call method to give reward
                // giveReward();

                MainActivity.instance.StartTimer();
                if (comeFrom.equals("save") || comeFrom.equals("Share")) {
                    download();
                } else {
                    Log.d("dhsdh", "onAdDismissedFullScreenContent-=-=-=->>>>>" +"dsjdj");
                    binding.llWaterMark.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRewardedVideoClosed() {
                // The Rewarded Video ad was closed - this can occur during the video
                // by closing the app, or closing the end card.
                Log.d(TAG, "Rewarded video ad closed!");
            }
        };
        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
    }

    @Override
    protected void onDestroy() {
        if (rewardedVideoAd != null) {
            rewardedVideoAd.destroy();
            rewardedVideoAd = null;
        }
        super.onDestroy();
    }

    public void download() {

        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.
                HideBoarder();
                Helper.SaveFile(binding.rltvDownLoadView, context, Helper.GetUniqueNumber(), comeFrom);

            }
        });
    }


    public void profileSelectionDialog() {

        Log.d("bsxs", "sjasj1");
        profileDialog = new Dialog(context);
        profileDialog.setContentView(R.layout.profileselect_alert_dialog);
        profileDialog.getWindow().setGravity(Gravity.CENTER);
        profileDialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(profileDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        // lp.windowAnimations = R.style.DialogAnimation;
        profileDialog.getWindow().setAttributes(lp);
        ImageView iv_close;
        RadioButton rb_pp, rb_bus;
        rb_pp = profileDialog.findViewById(R.id.rb_pp);
        rb_bus = profileDialog.findViewById(R.id.rb_bus);
        iv_close = profileDialog.findViewById(R.id.iv_close);

        if (selectProfile.equals("personal")) {
            rb_pp.setChecked(true);

        }
        if (selectProfile.equals("business")) {
            rb_bus.setChecked(true);

        }

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileDialog.dismiss();

            }
        });
        rb_pp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectProfile = "personal";
                    FooterPersionalDataLooping();
                    initPersonalAdapter();
                    binding.vpPersonalFrame.setVisibility(View.VISIBLE);
                    binding.vpFrame.setVisibility(View.GONE);
                    binding.txtPersonal.setBackgroundResource(R.drawable.btn_bg);
                    binding.txtPersonal.setTextColor(getResources().getColor(R.color.white));
                    binding.txtBusiness.setBackgroundResource(R.drawable.daily_img_bg);
                    binding.txtBusiness.setTextColor(getResources().getColor(R.color.main_color));

                    profileDialog.dismiss();
                }
            }
        });
        rb_bus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectProfile = "business";
                    FooterBusinessDataLooping();
                    initBusinessAdapter();
                    binding.vpPersonalFrame.setVisibility(View.GONE);
                    binding.vpFrame.setVisibility(View.VISIBLE);
                    binding.txtPersonal.setBackgroundResource(R.drawable.daily_img_bg);
                    binding.txtBusiness.setBackgroundResource(R.drawable.btn_bg);
                    binding.txtPersonal.setTextColor(getResources().getColor(R.color.main_color));
                    binding.txtBusiness.setTextColor(getResources().getColor(R.color.white));

                    profileDialog.dismiss();
                }
            }
        });

        profileDialog.show();

    }

    private void FooterBusinessDataLooping() {
        imgarrayList.clear();
        imgarrayList.add(null);
        imgarrayList.add(R.drawable.design1);
        imgarrayList.add(R.drawable.design2);
        imgarrayList.add(R.drawable.design3);
        imgarrayList.add(R.drawable.design4);
        imgarrayList.add(R.drawable.design5);
        imgarrayList.add(R.drawable.design6);
        imgarrayList.add(R.drawable.frame_14);
        imgarrayList.add(R.drawable.frame_20);
        updateFooterlist(imgarrayList);
        Log.d("dfjdf", "jfdjfk" + imgarrayList.size());

      /*  FrameSliderAdepter frameSliderAdepter=new FrameSliderAdepter(context,imgarrayList);
        binding.vpFrame.setAdapter(frameSliderAdepter);*/
    }

    private void FooterPersionalDataLooping() {
        imgarrayList.clear();

        imgarrayList.add(null);
        imgarrayList.add(R.drawable.design7);
        imgarrayList.add(R.drawable.design8);
        imgarrayList.add(R.drawable.design9);
        imgarrayList.add(R.drawable.design10);
        imgarrayList.add(R.drawable.design11);
        imgarrayList.add(R.drawable.frame_12);
        imgarrayList.add(R.drawable.frame_13);
        imgarrayList.add(R.drawable.frame_15);
        imgarrayList.add(R.drawable.frame_16);
        imgarrayList.add(R.drawable.frame_17);
        imgarrayList.add(R.drawable.frame_18);
        imgarrayList.add(R.drawable.frame_19);

        updateFooterlist(imgarrayList);
     /*   FrameSliderAdepter frameSliderAdepter=new FrameSliderAdepter(context,imgarrayList);
        binding.vpFrame.setAdapter(frameSliderAdepter);*/
        Log.d("dfjdf", "jfdjfk" + imgarrayList.size());

    }


    private void initBusinessAdapter() {
        recyclerAdapter = new MyMasterRecyclerAdapter(imgarrayList, R.layout.item_footer_style);
        rvFrame.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnBind((viewDataBinding, position) -> {
            ItemFooterStyleBinding bindings = (ItemFooterStyleBinding) viewDataBinding;
//            bindings.txtFooter.setText("Footer " + arrayList.get(position));
            Glide.with(context).load(imgarrayList.get(position)).into(bindings.ivFooterStyle);

            bindings.getRoot().setOnClickListener(v -> {

                if (position == 0) {
                    AllFillTagRemove();
                    binding.ivfooterLogo.setVisibility(View.VISIBLE);
                    binding.txtFooterStyle3.setBackgroundResource(R.drawable.tag_bg);
                    binding.txtFooterStyle3.setTextColor(Color.WHITE);
                    RemoveAllFooter();

                    binding.rltvFooter3.setVisibility(View.VISIBLE);


                } else if (position == 1) {
                    AllFillTagRemove();
                    if (CompanyTitletextEntity == null) {

                    }
                    binding.ivfooterLogo.setVisibility(View.VISIBLE);
                    binding.txtFooterStyle1.setBackgroundResource(R.drawable.tag_bg);
                    binding.txtFooterStyle1.setTextColor(Color.WHITE);
                    RemoveAllFooter();
                    binding.rltvFooter1.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    AllFillTagRemove();

                    if (CompanyTitletextEntity == null) {

                    }

                    binding.ivfooterLogo.setVisibility(View.VISIBLE);
                    binding.txtFooterStyle2.setBackgroundResource(R.drawable.tag_bg);
                    binding.txtFooterStyle2.setTextColor(Color.WHITE);
                    RemoveAllFooter();
                    if (Config.isYoutube || Config.isFb || Config.isInsta || Config.isWhatsapp) {
                        binding.Footer2.llSocial.setVisibility(View.VISIBLE);
                    } else {
                        binding.Footer2.llSocial.setVisibility(View.GONE);
                    }
                    if (Config.isYoutube) {
                        binding.Footer2.ivF2Yt.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        binding.Footer2.ivF2Fb.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        binding.Footer2.ivF2Inst.setVisibility(View.VISIBLE);
                    }
                    if (Config.isWhatsapp) {
                        binding.Footer2.ivF2Wa.setVisibility(View.VISIBLE);
                    }
                    binding.rltvFooter2.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    AllFillTagRemove();

                } else if (position == 3) {
                    AllFillTagRemove();

                    binding.txtFooterStyle4.setBackgroundResource(R.drawable.tag_bg);
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    binding.txtFooterStyle4.setTextColor(Color.WHITE);
                    RemoveAllFooter();
                    if (Config.isYoutube || Config.isFb || Config.isInsta || Config.isWhatsapp) {
                        binding.Footer4.llSocial.setVisibility(View.VISIBLE);
                    } else {
                        binding.Footer4.llSocial.setVisibility(View.GONE);
                    }
                    if (Config.isYoutube) {
                        binding.Footer4.ivF2Yt.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        binding.Footer4.ivF2Fb.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        binding.Footer4.ivF2Inst.setVisibility(View.VISIBLE);
                    }
                    if (Config.isWhatsapp) {
                        binding.Footer4.ivF2Wt.setVisibility(View.VISIBLE);
                    }
                    binding.rltvFooter4.setVisibility(View.VISIBLE);
                } else if (position == 4) {
                    AllFillTagRemove();
                    if (CompanyTitletextEntity == null) {

                    }
                    binding.ivfooterLogo.setVisibility(View.VISIBLE);
                    binding.txtFooterStyle5.setBackgroundResource(R.drawable.tag_bg);
                    binding.txtFooterStyle5.setTextColor(Color.WHITE);
                    RemoveAllFooter();
                    binding.rltvFooter5.setVisibility(View.VISIBLE);
                } else if (position == 5) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    binding.rltvFooter6.setVisibility(View.VISIBLE);
                } else if (position == 6) {

                    Log.d("sddfhas", "sdfauff");
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    binding.rltvFooter14.setVisibility(View.VISIBLE);
                }
            });


        });

    }

    private void initPersonalAdapter() {
        recyclerAdapter = new MyMasterRecyclerAdapter(imgarrayList, R.layout.item_footer_style);
        rvFrame.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnBind((viewDataBinding, position) -> {
            ItemFooterStyleBinding bindings = (ItemFooterStyleBinding) viewDataBinding;
//            bindings.txtFooter.setText("Footer " + arrayList.get(position));
            Glide.with(context).load(imgarrayList.get(position)).into(bindings.ivFooterStyle);

            bindings.getRoot().setOnClickListener(v -> {
                if (position == 0) {
                    AllFillTagRemove();

                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                } else if (position == 1) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    binding.rltvFooter7.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    binding.rltvFooter8.setVisibility(View.VISIBLE);
                } else if (position == 3) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    binding.rltvFooter9.setVisibility(View.VISIBLE);
                } else if (position == 4) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    binding.rltvFooter10.setVisibility(View.VISIBLE);
                } else if (position == 5) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    binding.rltvFooter11.setVisibility(View.VISIBLE);
                } else if (position == 6) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    if (Config.isYoutube || Config.isFb || Config.isInsta || Config.isWhatsapp) {
                        binding.Footer12.llSocial.setVisibility(View.VISIBLE);
                    } else {
                        binding.Footer12.llSocial.setVisibility(View.GONE);
                    }
                    if (Config.isYoutube) {
                        binding.Footer12.llYt.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        binding.Footer12.llFb.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        binding.Footer12.llInsta.setVisibility(View.VISIBLE);
                    }
                    if (Config.isWhatsapp) {
                        binding.Footer12.llWa.setVisibility(View.VISIBLE);
                    }
                    binding.rltvFooter12.setVisibility(View.VISIBLE);
                } else if (position == 7) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    if (Config.isYoutube || Config.isFb || Config.isInsta || Config.isWhatsapp) {
                        binding.Footer13.llSocial.setVisibility(View.VISIBLE);
                    } else {
                        binding.Footer13.llSocial.setVisibility(View.GONE);
                    }
                    if (Config.isYoutube) {
                        binding.Footer13.llYt.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        binding.Footer13.llFb.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        binding.Footer13.llInsta.setVisibility(View.VISIBLE);
                    }
                    if (Config.isWhatsapp) {
                        binding.Footer13.llWa.setVisibility(View.VISIBLE);
                    }
                    binding.rltvFooter13.setVisibility(View.VISIBLE);
                } else if (position == 8) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    if (Config.isYoutube || Config.isFb || Config.isInsta || Config.isWhatsapp) {
                        binding.Footer15.llSocial.setVisibility(View.VISIBLE);
                    } else {
                        binding.Footer15.llSocial.setVisibility(View.GONE);
                    }
                    if (Config.isYoutube) {
                        binding.Footer15.llYt.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        binding.Footer15.llFb.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        binding.Footer15.llInsta.setVisibility(View.VISIBLE);
                    }
                    if (Config.isWhatsapp) {
                        binding.Footer15.llWa.setVisibility(View.VISIBLE);
                    }
                    binding.rltvFooter15.setVisibility(View.VISIBLE);
                } else if (position == 9) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    binding.rltvFooter16.setVisibility(View.VISIBLE);
                }
            });


        });

    }

    private void getWaterMark() {
        apiInterface1 = RetrofitClient.getRetrofitInstance(this).create(ApiInterface.class);

        Call<WaterMark> call = apiInterface1.getWatermark();
        call.enqueue(new Callback<WaterMark>() {
            @Override
            public void onResponse(Call<WaterMark> call, Response<WaterMark> response) {

                if (response.isSuccessful()) {

                    if (response.body().getData() != null) {

                        if (new SharedPreferencesHelper(context).getBoolean(Config.Buy_Plan)) {
                            binding.llWaterMark.setVisibility(View.GONE);
                        } else {
                            binding.tvWText.setText(response.body().getData().getWatermark_text());
                            if (!response.body().getData().getMargin().matches("") || response.body().getData().getMargin() != null) {
                                padding = Integer.parseInt(response.body().getData().getMargin());
                            } else {
                                padding = 0;

                            }

                            if (response.body().getData().getFont_family().equals("Aaargh.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Aaargh.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Anton-Regular.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Anton-Regular.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Arya-Regular.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Arya-Regular.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Asar-Regular.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Asar-Regular.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Baloo-Regular.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Baloo-Regular.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Gotu-Regular.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Gato-Regular.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Halant-Bold.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Halant-Bold.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Hey-August.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Hey-August.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("IndieFlower-Regular.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "IndieFlower-Regular.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Kalam-Bold.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Kalam-Bold.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Kalam-Regular.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Kalam-Regular.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Kokila.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Kokila.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Laila-Bold.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Laila-Bold.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Laila-Medium.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Laila-Medium.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Laila-Regular.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Laila-Regular.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Lobster-Regular.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Lobster-Regular.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("NotoSerifDevanagari-Bold.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "NotoSerifDevanagari-Bold.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("NotoSerifDevanagari-Medium.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "NotoSerifDevanagari-Medium.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("NotoSerifDevanagari-SemiBold.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "NotoSerifDevanagari-SemiBold.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Poppins-SemiBold.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Poppins-SemiBold.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Poppins-Thin.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Poppins-Thin.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Pushster-Regular.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Pushster-Regular.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Roboto-Bold.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Bold.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Roboto-Medium.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Medium.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("sahadeva.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "sahadeva.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Samyak-Devanagari.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Samyak-Devanagari.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Sanskrit2003.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Sanskrit2003.ttf"));
                            }

                            if (response.body().getData().getFont_family().equals("Sarai_07.ttf")) {
                                binding.tvWText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Sarai_07.ttf"));
                            }


                            if (response.body().getData().getFont_style().equals("NORMAL")) {
                                binding.tvWText.setTypeface(binding.tvWText.getTypeface(), Typeface.NORMAL);

                            }
                            if (response.body().getData().getFont_style().equals("BOLD")) {
                                binding.tvWText.setTypeface(binding.tvWText.getTypeface(), Typeface.BOLD);

                            }
                            if (response.body().getData().getFont_style().equals("ITALIC")) {
                                binding.tvWText.setTypeface(binding.tvWText.getTypeface(), Typeface.ITALIC);

                            }
                            if (response.body().getData().getFont_style().equals("BOLD_ITALIC")) {
                                binding.tvWText.setTypeface(binding.tvWText.getTypeface(), Typeface.BOLD_ITALIC);

                            }
                            binding.tvWText.setTextColor(Color.parseColor(response.body().getData().getFont_color()));
                            binding.tvWText.setTextSize(Float.parseFloat(response.body().getData().getFont_size()));

                            if (response.body().getData().getWatermark_text().matches("") || response.body().getData().getWatermark_text() == null) {
                                Glide.with(context).load(Config.IMG_PATH + response.body().getData().getWatermark_image()).into(binding.ivWLogo);
                            } else {
                                binding.ivWLogo.setVisibility(View.GONE);
                            }
                            String s = response.body().getData().getWatermark_position();
                            if (s.equals("center")) {
                                Log.d("shjdhsjdjas", "5");
                                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                                binding.llWaterMark.setLayoutParams(params);
                                binding.llWaterMark.setPadding(padding, padding, padding, padding);
                                binding.llWaterMark.setAlpha(Float.parseFloat(response.body().getData().getWatermark_opacity()));
                            } else {
                                Log.d("shjdhsjdjas", "6");
                                String[] parts = s.split("\\_"); // escape .
                                String part1 = parts[0];
                                String part2 = parts[1];
                                Log.d("dsjfksdkfj", "" + part1);
                                Log.d("dsjfksdkfj", "" + part2);
                                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                if (part1.equals("top")) {
                                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                                }
                                if (part1.equals("bottom")) {
                                    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                                }
                                if (part2.equals("right")) {
                                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                                }
                                if (part2.equals("left")) {
                                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                                }
                                binding.llWaterMark.setLayoutParams(params);
                                binding.llWaterMark.setPadding(padding, padding, padding, padding);
                                binding.llWaterMark.setAlpha(Float.parseFloat(response.body().getData().getWatermark_opacity()));
                            }
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<WaterMark> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("jsdsdjs", "sgsg xx" + t.getMessage());
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });

    }

    private void GetUserPlanList() {
        Log.d("hdjshdjh", "hhah" + Config.Mobile_No);
        RequestBody fcm_token = RequestBody.create(FCM_TOKEN, MediaType.parse("text/plain"));

        apiInterface = RetrofitClient.getRetrofitInstance(this).create(ApiInterface.class);

        RequestBody mobile_number = RequestBody.create(Config.Mobile_No, MediaType.parse("text/plain"));
        RequestBody user_id = RequestBody.create(new SharedPreferencesHelper(context).getString(Config.USER_ID), MediaType.parse("text/plain"));
        Log.d("hdjshdjh", "hhah" + mobile_number);
        Log.d("hdjshdjh", "hhah" + user_id);
//        Call<ResponseBody> call = apiInterface.send_otp(mobile_number, fcm_token);
        Call<ResponseBody> call = apiInterface.my_profile_send_otp(user_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Helper.cancel_loader();
                if (response.isSuccessful()) {

                    try {
                        if (response.isSuccessful()) {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            // Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            if (jsonObject.getBoolean("status")) {
                                if ((jsonObject.getJSONObject("data").getJSONArray("business_list").length() != 0)) {
                                    BusinessResponseData model = new BusinessResponseData();
                                    model.setSetselectedAcc(true);
                                    JSONObject jsonObjects = jsonObject.getJSONObject("data").getJSONArray("business_list").getJSONObject(0);
                                    model.setBusinessAddress(jsonObjects.getString("business_address"));
                                    model.setBusinessEmail(jsonObjects.getString("business_email"));
                                    model.setBusinessId(jsonObjects.getString("business_id"));
                                    if (jsonObjects.has("business_logo"))
                                        model.setBusinessLogo(jsonObjects.getString("business_logo"));
                                    model.setBusinessName(jsonObjects.getString("business_name"));
                                    model.setBusinessPhoneNo(jsonObjects.getString("business_phone_no"));
                                    model.setBusinessWebsite(jsonObjects.getString("business_website"));
                                    SharedPreferencesHelper.saveObjectToSharedPreference(context, STORAGE_BUSINESS_ACCOUNT, model);

                                } else {
                                    Log.d("hdjshdjh", "else:    ");
                                    //showDialog();
                                }

                                if (selectProfile.equals("personal")) {
                                    if (jsonObject.getJSONObject("data").getString("full_name").equals("") ||
                                            jsonObject.getJSONObject("data").getString("address").equals("") ||
                                            jsonObject.getJSONObject("data").getString("designation").equals("")) {
                                        profileEmpty = true;
                                        showDialog();
                                    } else {
                                        profileEmpty = false;

                                    }
                                }
                                Log.d("fjjfjsjdfjsdk", "fjdjfksjdk" + selectProfile);

                                if (selectProfile.equals("business")) {
                                    if ((jsonObject.getJSONObject("data").getJSONArray("business_list").length() == 0)) {
                                        businessEmpty = true;
                                        showDialog();

                                    } else {
                                        businessEmpty = false;

                                    }
                                }
                                new SharedPreferencesHelper(context).setString(Config.USER_ID, jsonObject.getJSONObject("data").getString("user_id"));
                                new SharedPreferencesHelper(context).setBoolean(Config.IS_LOGIN, true);
                                initviews();
                                ClickEvent();
                                Log.d("bsxs", "sjasj3");


                            } else {
                                Log.d("bsxs", "sjasj2");
                                //startActivity(new Intent(context, ProfileMakeActivity.class).putExtra(Config.MOBILE, binding.edtMobile.getText().toString()));
                                showDialog();
                            }
                        } else {
                            Toast.makeText(context, getString(R.string.Somethingswrong), Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        Helper.cancel_loader();
                        e.printStackTrace();

                        Log.d("sghg", "hdush" + e.getMessage());
                    }

                } else {
                    Helper.cancel_loader();
                    Log.d("sghg", "not Succ");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("sghg", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });
    }


    private void ClickEvent() {

        binding.toolbarSupport.ivprofileselection.setOnClickListener(v -> {

            profileSelectionDialog();
        });
        binding.ivFooterColorChange.setOnClickListener(v -> {
            ShowImageALertFooter(context);
        });
        binding.toolbarSupport.ivBack.setOnClickListener(v -> finish());
        binding.ivTextAdd.setOnClickListener(v -> addTextSticker());
        binding.ivTextStyle.setOnClickListener(v -> ChangeTextEntityFont());
        binding.ivTextColor.setOnClickListener(v -> changeTextEntityColor());
        binding.toolbarSupport.ivDelete.setOnClickListener(v -> motionView.deletedSelectedEntity());
        binding.ivAddLogo.setOnClickListener(v -> {
            ShowImageALert(context);
        });
        binding.toolbarSupport.ivDownload.setOnClickListener(v -> {

            comeFrom = "save";
            if (new SharedPreferencesHelper(context).getBoolean(Config.Buy_Plan)) {
                download();
            } else {
                DownloadDialog();
            }
           /* String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            Permissions.check(this*//*context*//*, permissions, null*//*rationale*//*, null*//*options*//*, new PermissionHandler() {
                @Override
                public void onGranted() {
                    // do your task.
                    HideBoarder();
                    Helper.SaveFile(binding.rltvDownLoadView, context, Helper.GetUniqueNumber(), "save");

                }
            });*/
        });
        binding.toolbarSupport.ivShare.setOnClickListener(v -> {

            comeFrom = "Share";
            if (new SharedPreferencesHelper(context).getBoolean(Config.Buy_Plan)) {
                download();
            } else {
                DownloadDialog();
            }
           /* String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            Permissions.check(this*//*context*//*, permissions, null*//*rationale*//*, null*//*options*//*, new PermissionHandler() {
                @Override
                public void onGranted() {
                    // do your task.
                    HideBoarder();
                    Helper.SaveFile(binding.rltvDownLoadView, context, Helper.GetUniqueNumber(), "Share");
                }
            });
*/
        });

        binding.llWaterMark.setOnClickListener(v -> {
            comeFrom = "watemark";
            if (new SharedPreferencesHelper(context).getBoolean(Config.Buy_Plan)) {
                binding.llWaterMark.setVisibility(View.GONE);
            } else {
                DownloadDialog();
            }
        });


        binding.txtFooterStyle1.setOnClickListener(v -> {
            AllFillTagRemove();
            binding.ivfooterLogo.setVisibility(View.GONE);

            binding.txtFooterStyle1.setBackgroundResource(R.drawable.tag_bg);
            binding.txtFooterStyle1.setTextColor(Color.WHITE);
            RemoveAllFooter();
            binding.rltvFooter1.setVisibility(View.VISIBLE);
        });


        binding.txtFooterStyle2.setOnClickListener(v -> {
            AllFillTagRemove();

            binding.ivfooterLogo.setVisibility(View.GONE);
            binding.txtFooterStyle2.setBackgroundResource(R.drawable.tag_bg);
            binding.txtFooterStyle2.setTextColor(Color.WHITE);
            RemoveAllFooter();
            binding.rltvFooter2.setVisibility(View.VISIBLE);
        });

        binding.txtFooterStyle3.setOnClickListener(v -> {
            AllFillTagRemove();

            binding.ivfooterLogo.setVisibility(View.VISIBLE);
            binding.txtFooterStyle3.setBackgroundResource(R.drawable.tag_bg);
            binding.txtFooterStyle3.setTextColor(Color.WHITE);
            RemoveAllFooter();
            binding.rltvFooter3.setVisibility(View.VISIBLE);
        });

        binding.txtFooterStyle4.setOnClickListener(v -> {
            AllFillTagRemove();

            binding.txtFooterStyle4.setBackgroundResource(R.drawable.tag_bg);
            binding.ivfooterLogo.setVisibility(View.VISIBLE);
            binding.txtFooterStyle4.setTextColor(Color.WHITE);
            RemoveAllFooter();
            binding.rltvFooter4.setVisibility(View.VISIBLE);
        });

        binding.txtFooterStyle5.setOnClickListener(v -> {
            AllFillTagRemove();

            binding.ivfooterLogo.setVisibility(View.VISIBLE);
            binding.txtFooterStyle5.setBackgroundResource(R.drawable.tag_bg);
            binding.txtFooterStyle5.setTextColor(Color.WHITE);
            RemoveAllFooter();
            binding.rltvFooter5.setVisibility(View.VISIBLE);
        });
    }

    private void RemoveAllFooter() {
        binding.rltvFooter1.setVisibility(View.GONE);
        binding.rltvFooter2.setVisibility(View.GONE);
        binding.rltvFooter3.setVisibility(View.GONE);
        binding.rltvFooter4.setVisibility(View.GONE);
        binding.rltvFooter5.setVisibility(View.GONE);
        binding.rltvFooter6.setVisibility(View.GONE);
        binding.rltvFooter7.setVisibility(View.GONE);
        binding.rltvFooter8.setVisibility(View.GONE);
        binding.rltvFooter9.setVisibility(View.GONE);
        binding.rltvFooter10.setVisibility(View.GONE);
        binding.rltvFooter11.setVisibility(View.GONE);
        binding.rltvFooter12.setVisibility(View.GONE);
        binding.rltvFooter13.setVisibility(View.GONE);
        binding.rltvFooter14.setVisibility(View.GONE);
        binding.rltvFooter15.setVisibility(View.GONE);
        binding.rltvFooter16.setVisibility(View.GONE);
    }

    private void AllFillTagRemove() {
        binding.txtFooterStyle1.setBackgroundResource(R.drawable.tag_border_bg);
        binding.txtFooterStyle1.setTextColor(Color.BLACK);
        binding.txtFooterStyle2.setBackgroundResource(R.drawable.tag_border_bg);
        binding.txtFooterStyle2.setTextColor(Color.BLACK);
        binding.txtFooterStyle3.setBackgroundResource(R.drawable.tag_border_bg);
        binding.txtFooterStyle3.setTextColor(Color.BLACK);
        binding.txtFooterStyle4.setBackgroundResource(R.drawable.tag_border_bg);
        binding.txtFooterStyle4.setTextColor(Color.BLACK);
        binding.txtFooterStyle5.setBackgroundResource(R.drawable.tag_border_bg);
        binding.txtFooterStyle5.setTextColor(Color.BLACK);
    }


    private void ShowImageALertFooter(Context context) {

        new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.what_footer))
                .setConfirmText(context.getString(R.string.Background_Color))
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    changeTextEntityColor();
                    IsFooterBackground = false;
                    ColorStatus = "footerback";

                })
                .setCancelText(context.getString(R.string.Text_Color))
                .setCancelClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    changeTextEntityColor();
                    IsFooterBackground = true;
                    ColorStatus = "textcolor";


                })
                .show();

    }

    public void ShowImageALert(Context context) {
        new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.what_do_you))
                .setConfirmText(context.getString(R.string.Add_Logo))
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    IsImage = false;
                 /*   CropImage.activity()
                            .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(this);*/


                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, REQUEST_CODE_GALLERY);
                })
                .setCancelText(context.getString(R.string.Add_Image))
                .setCancelClickListener(sweetAlertDialog -> {
                    IsImage = true;
                    sweetAlertDialog.dismiss();
                    /*CropImage.activity()
                            .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(this);*/


                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, REQUEST_CODE_GALLERY);
                })
                .show();
    }

    private void HideBoarder() {
        if (motionView.getSelectedEntity() != null)
            motionView.getSelectedEntity().setIsSelected(false);

    }

    private void initviews() {
        context = this;
        HideActionBar(this);
        apiInterface = RetrofitClient.getRetrofitInstance(this).create(ApiInterface.class);
        rvFrame = binding.rvFrame;
        motionView = (MotionView) findViewById(R.id.main_motion_view);
        motionView.setMotionViewCallback(motionViewCallback);

        binding.toolbarSupport.txttitle.setText(R.string.Edit_Frame);
        this.fontProvider = new FontProvider(getResources());
        setRecyclerAdapter();
        if (selectProfile.equals("personal")) {
            FooterPersionalDataLooping();
            initPersonalAdapter();

        } else if (selectProfile.equals("business")) {
            FooterBusinessDataLooping();
            initBusinessAdapter();

        } else {
            initAdapter();
            FooterDataLooping();

        }

        DataLooping();


        if (Helper.checkInternet(context)) {
            MyProfile();
        }

        binding.toolbarSupport.llShowNextView.setOnClickListener(v -> {
            binding.toolbarSupport.llShowNextView.setVisibility(View.GONE);
            binding.llbottomControl.setVisibility(View.GONE);

//            binding.toolbarSupport.llShare.setVisibility(View.VISIBLE);
            binding.toolbarSupport.ivDelete.setVisibility(View.GONE);
            binding.toolbarSupport.ivDownload.setVisibility(View.VISIBLE);
            binding.toolbarSupport.ivShare.setVisibility(View.VISIBLE);

            binding.txtPersonal.setVisibility(View.VISIBLE);
            binding.txtBusiness.setVisibility(View.VISIBLE);
            if (selectProfile.equals("personal"))
                binding.vpPersonalFrame.setVisibility(View.VISIBLE);
            if (selectProfile.equals("business"))
                binding.vpFrame.setVisibility(View.VISIBLE);
            binding.tvSwipe.setVisibility(View.VISIBLE);
            binding.fbShowEditView.setVisibility(View.VISIBLE);
        });

        binding.fbShowEditView.setOnClickListener(v -> {
            binding.toolbarSupport.llShowNextView.setVisibility(View.VISIBLE);
            binding.llbottomControl.setVisibility(View.VISIBLE);

//            binding.toolbarSupport.llShare.setVisibility(View.GONE);
            binding.toolbarSupport.ivDelete.setVisibility(View.VISIBLE);
            binding.toolbarSupport.ivDownload.setVisibility(View.GONE);
            binding.toolbarSupport.ivShare.setVisibility(View.GONE);

            binding.txtPersonal.setVisibility(View.GONE);
            binding.txtBusiness.setVisibility(View.GONE);
            binding.vpPersonalFrame.setVisibility(View.GONE);
            binding.vpFrame.setVisibility(View.GONE);
            binding.tvSwipe.setVisibility(View.GONE);
            binding.fbShowEditView.setVisibility(View.GONE);
        });
    }


    private void setRecyclerAdapter() {
        rvFrame.setAdapter(recyclerAdapter);
    }


    private void updateFooterlist(List<Integer> list) {

        if (recyclerAdapter == null) {
            initAdapter();
        } else {
            recyclerAdapter.updateNewList(list);
        }
        rvFrame.setAdapter(recyclerAdapter);
    }

    private void initAdapter() {
        recyclerAdapter = new MyMasterRecyclerAdapter(imgarrayList, R.layout.item_footer_style);
        rvFrame.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnBind((viewDataBinding, position) -> {
            ItemFooterStyleBinding bindings = (ItemFooterStyleBinding) viewDataBinding;
//            bindings.txtFooter.setText("Footer " + arrayList.get(position));
            Glide.with(context).load(imgarrayList.get(position)).into(bindings.ivFooterStyle);
            bindings.getRoot().setOnClickListener(v -> {
                DeleteCopmanyText(CompanyTitletextEntity);
                if (position == 0) {
                    AllFillTagRemove();
                    if (CompanyTitletextEntity == null) {
                        addTextCompanySticker();
                        AditCompanyText(model.getBusinessName(), CompanyTitletextEntity);
                    }
                    binding.ivfooterLogo.setVisibility(View.VISIBLE);
                    binding.txtFooterStyle1.setBackgroundResource(R.drawable.tag_bg);
                    binding.txtFooterStyle1.setTextColor(Color.WHITE);
                    RemoveAllFooter();
                    binding.rltvFooter1.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    AllFillTagRemove();
                    if (CompanyTitletextEntity == null) {
                        addTextCompanySticker();
                        AditCompanyText(model.getBusinessName(), CompanyTitletextEntity);
                    }
                    binding.ivfooterLogo.setVisibility(View.VISIBLE);
                    binding.txtFooterStyle2.setBackgroundResource(R.drawable.tag_bg);
                    binding.txtFooterStyle2.setTextColor(Color.WHITE);
                    RemoveAllFooter();
                    binding.rltvFooter2.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    AllFillTagRemove();
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    if (Config.isYoutube || Config.isFb || Config.isInsta || Config.isWhatsapp) {
                        binding.Footer2.llSocial.setVisibility(View.VISIBLE);
                    } else {
                        binding.Footer2.llSocial.setVisibility(View.GONE);
                    }
                    if (Config.isYoutube) {
                        binding.Footer2.ivF2Yt.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        binding.Footer2.ivF2Fb.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        binding.Footer2.ivF2Inst.setVisibility(View.VISIBLE);
                    }
                    if (Config.isWhatsapp) {
                        binding.Footer2.ivF2Wa.setVisibility(View.VISIBLE);
                    }

                } else if (position == 3) {
                    AllFillTagRemove();

                    binding.ivfooterLogo.setVisibility(View.VISIBLE);
                    binding.txtFooterStyle3.setBackgroundResource(R.drawable.tag_bg);
                    binding.txtFooterStyle3.setTextColor(Color.WHITE);
                    RemoveAllFooter();
                    binding.rltvFooter3.setVisibility(View.VISIBLE);
                } else if (position == 4) {
                    AllFillTagRemove();

                    binding.txtFooterStyle4.setBackgroundResource(R.drawable.tag_bg);
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    binding.txtFooterStyle4.setTextColor(Color.WHITE);
                    RemoveAllFooter();
                    if (Config.isYoutube || Config.isFb || Config.isInsta || Config.isWhatsapp) {
                        binding.Footer4.llSocial.setVisibility(View.VISIBLE);
                    } else {
                        binding.Footer4.llSocial.setVisibility(View.GONE);
                    }
                    if (Config.isYoutube) {
                        binding.Footer4.ivF2Yt.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        binding.Footer4.ivF2Fb.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        binding.Footer4.ivF2Inst.setVisibility(View.VISIBLE);
                    }
                    if (Config.isWhatsapp) {
                        binding.Footer4.ivF2Wt.setVisibility(View.VISIBLE);
                    }
                    binding.rltvFooter4.setVisibility(View.VISIBLE);
                } else if (position == 5) {
                    AllFillTagRemove();
                    if (CompanyTitletextEntity == null) {
                        addTextCompanySticker();
                        AditCompanyText(model.getBusinessName(), CompanyTitletextEntity);
                    }
                    binding.ivfooterLogo.setVisibility(View.VISIBLE);
                    binding.txtFooterStyle5.setBackgroundResource(R.drawable.tag_bg);
                    binding.txtFooterStyle5.setTextColor(Color.WHITE);
                    RemoveAllFooter();
                    binding.rltvFooter5.setVisibility(View.VISIBLE);
                } else if (position == 6) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    binding.rltvFooter6.setVisibility(View.VISIBLE);
                } else if (position == 7) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    binding.rltvFooter7.setVisibility(View.VISIBLE);
                } else if (position == 8) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    binding.rltvFooter8.setVisibility(View.VISIBLE);
                } else if (position == 9) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    binding.rltvFooter9.setVisibility(View.VISIBLE);
                } else if (position == 10) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    binding.rltvFooter10.setVisibility(View.VISIBLE);
                } else if (position == 11) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    binding.rltvFooter11.setVisibility(View.VISIBLE);
                } else if (position == 12) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    if (Config.isYoutube || Config.isFb || Config.isInsta || Config.isWhatsapp) {
                        binding.Footer12.llSocial.setVisibility(View.VISIBLE);
                    } else {
                        binding.Footer12.llSocial.setVisibility(View.GONE);
                    }
                    if (Config.isYoutube) {
                        binding.Footer12.llYt.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        binding.Footer12.llFb.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        binding.Footer12.llInsta.setVisibility(View.VISIBLE);
                    }
                    if (Config.isWhatsapp) {
                        binding.Footer12.llWa.setVisibility(View.VISIBLE);
                    }
                    binding.rltvFooter12.setVisibility(View.VISIBLE);
                } else if (position == 13) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    if (Config.isYoutube || Config.isFb || Config.isInsta || Config.isWhatsapp) {
                        binding.Footer13.llSocial.setVisibility(View.VISIBLE);
                    } else {
                        binding.Footer13.llSocial.setVisibility(View.GONE);
                    }
                    if (Config.isYoutube) {
                        binding.Footer13.llYt.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        binding.Footer13.llFb.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        binding.Footer13.llInsta.setVisibility(View.VISIBLE);
                    }
                    if (Config.isWhatsapp) {
                        binding.Footer13.llWa.setVisibility(View.VISIBLE);
                    }
                    binding.rltvFooter13.setVisibility(View.VISIBLE);
                } else if (position == 14) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    binding.rltvFooter14.setVisibility(View.VISIBLE);
                } else if (position == 15) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    if (Config.isYoutube || Config.isFb || Config.isInsta || Config.isWhatsapp) {
                        binding.Footer15.llSocial.setVisibility(View.VISIBLE);
                    } else {
                        binding.Footer15.llSocial.setVisibility(View.GONE);
                    }
                    if (Config.isYoutube) {
                        binding.Footer15.llYt.setVisibility(View.VISIBLE);
                    }
                    if (Config.isFb) {
                        binding.Footer15.llFb.setVisibility(View.VISIBLE);
                    }
                    if (Config.isInsta) {
                        binding.Footer15.llInsta.setVisibility(View.VISIBLE);
                    }
                    if (Config.isWhatsapp) {
                        binding.Footer15.llWa.setVisibility(View.VISIBLE);
                    }
                    binding.rltvFooter15.setVisibility(View.VISIBLE);
                } else if (position == 16) {
                    binding.ivfooterLogo.setVisibility(View.GONE);
                    RemoveAllFooter();
                    binding.rltvFooter16.setVisibility(View.VISIBLE);
                }
            });

        });

    }

    private void FooterDataLooping() {

        imgarrayList.add(R.drawable.design1);
        imgarrayList.add(R.drawable.design2);
        imgarrayList.add(null);
        imgarrayList.add(R.drawable.design3);
        imgarrayList.add(R.drawable.design4);
        imgarrayList.add(R.drawable.design5);
        imgarrayList.add(R.drawable.design6);
        imgarrayList.add(R.drawable.design7);
        imgarrayList.add(R.drawable.design8);
        imgarrayList.add(R.drawable.design9);
        imgarrayList.add(R.drawable.design10);
        imgarrayList.add(R.drawable.design11);
        imgarrayList.add(R.drawable.frame_12);
        imgarrayList.add(R.drawable.frame_13);
        imgarrayList.add(R.drawable.frame_14);
        imgarrayList.add(R.drawable.frame_15);
        imgarrayList.add(R.drawable.frame_16);
        updateFooterlist(imgarrayList);
      /*  FrameSliderAdepter frameSliderAdepter=new FrameSliderAdepter(context,imgarrayList);
        binding.vpFrame.setAdapter(frameSliderAdepter);*/
        Log.d("dfjdf", "jfdjfk" + imgarrayList.size());

    }

    private void DataLooping() {


    }

    protected void addTextSticker() {
        TextLayer textLayer = createTextLayer();
        TextEntity TitletextEntity = new TextEntity(textLayer, motionView.getWidth(),
                motionView.getHeight(), fontProvider);
        motionView.addEntityAndPosition(TitletextEntity);
        // move text sticker up so that its not hidden under keyboard
        PointF center = TitletextEntity.absoluteCenter();
        center.y = center.y * 0.5F;
        TitletextEntity.moveCenterTo(center);
        TitletextEntity.getLayer().getFont().setColor(R.color.main_color);
        // redraw
        motionView.invalidate();
    }

    private TextLayer createTextLayer() {
        TextLayer textLayer = new TextLayer();
        Font font = new Font();

        font.setColor(TextLayer.Limits.INITIAL_FONT_COLOR);
        font.setSize(TextLayer.Limits.INITIAL_FONT_SIZE);
        font.setTypeface(fontProvider.getDefaultFontName());

        textLayer.setFont(font);

        if (BuildConfig.DEBUG) {
            textLayer.setText(getString(R.string.double_tap));
        }

        return textLayer;
    }

    private final MotionView.MotionViewCallback motionViewCallback = new MotionView.MotionViewCallback() {

        @Override
        public void onEntitySelected(@Nullable MotionEntity entity) {

        }

        @Override
        public void onEntityDoubleTap(@NonNull MotionEntity entity) {
            startTextEntityEditing();
        }
    };

    private void startTextEntityEditing() {
        TextEntity textEntity = currentTextEntity();
        if (textEntity != null) {
            TextEditorDialogFragment fragment = TextEditorDialogFragment.getInstance(textEntity.getLayer().getText());
            fragment.show(getFragmentManager(), TextEditorDialogFragment.class.getName());
        }
    }

    @Nullable
    private TextEntity currentTextEntity() {
        if (motionView != null && motionView.getSelectedEntity() instanceof TextEntity) {
            return ((TextEntity) motionView.getSelectedEntity());
        } else {
            return null;
        }
    }

    @Override
    public void textChanged(@NonNull String text) {
        AditText(text);
    }

    private void AditText(String text) {
        TextEntity textEntity = currentTextEntity();
        if (textEntity != null) {
            TextLayer textLayer = textEntity.getLayer();
            if (!text.equals(textLayer.getText())) {
                textLayer.setText(text);
                textEntity.updateEntity();
                motionView.invalidate();
            }
        }
    }

    private void ChangeTextEntityFont() {
        final List<String> fonts = fontProvider.getFontNames();
        FontsAdapter fontsAdapter = new FontsAdapter(this, fonts, fontProvider);
        new AlertDialog.Builder(this)
                .setTitle(R.string.select_font)
                .setAdapter(fontsAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        TextEntity textEntity = currentTextEntity();
                        if (textEntity != null) {
                            textEntity.getLayer().getFont().setTypeface(fonts.get(which));
                            textEntity.updateEntity();
                            motionView.invalidate();
                        }
                    }
                })
                .show();
    }

    private void changeTextEntityColor() {
        int initialColor = Color.WHITE;
        TextEntity textEntity = currentTextEntity();
        if (textEntity != null) {
            initialColor = textEntity.getLayer().getFont().getColor();
        }
        ColorPickerDialogBuilder
                .with(context)
                .setTitle(R.string.select_color)
                .initialColor(initialColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12) // magic number
                .setPositiveButton(R.string.ok, (dialog, selectedColor, allColors) -> {
                    if (ColorStatus.equalsIgnoreCase("normal")) {
                        TextEntity textEntity1 = currentTextEntity();
                        if (textEntity1 != null) {
                            textEntity1.getLayer().getFont().setColor(selectedColor);
                            textEntity1.updateEntity();
                            motionView.invalidate();
                        }
                    } else if (ColorStatus.equalsIgnoreCase("footerback")) {
                        ChangeFooterColor(selectedColor);
                    } else {
                        ChangeFooterTextColor(selectedColor);
                    }

                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    private void ChangeFooterTextColor(int selectedColor) {
        binding.Footer1.txtAddres.setTextColor(selectedColor);
        binding.Footer1.txtWebsite.setTextColor(selectedColor);
        binding.Footer1.txtContact.setTextColor(selectedColor);
        binding.Footer1.txtMail.setTextColor(selectedColor);
        /*----------------------*/
        binding.Footer2.txtAddres.setTextColor(selectedColor);
        binding.Footer2.txtMobile.setTextColor(selectedColor);
        binding.Footer2.txtMail.setTextColor(selectedColor);
        binding.Footer2.textWebsite.setTextColor(selectedColor);


        /*----------------------*/
        binding.Footer3.txtCompanyName.setTextColor(selectedColor);
        binding.Footer3.txtMobile.setTextColor(selectedColor);
        binding.Footer3.txtMail.setTextColor(selectedColor);
        binding.Footer3.textWebsite.setTextColor(selectedColor);
        /*----------------------*/
//        binding.Footer4.txtBusinessName.setTextColor(selectedColor);
//        binding.Footer4.txtDesignation.setTextColor(selectedColor);
//        binding.Footer4.txtAddress.setTextColor(selectedColor);
        /*----------------------*/
    /*    binding.Footer5.txtBusinessName.setTextColor(selectedColor);
        binding.Footer5.txtDesignation.setTextColor(selectedColor);
        binding.Footer5.txtAddress.setTextColor(selectedColor);*/
        binding.Footer5.txtMobile.setTextColor(selectedColor);

        binding.Footer12.txtAddres.setTextColor(selectedColor);
        binding.Footer13.txtAddres.setTextColor(selectedColor);
        binding.Footer15.txtAddress.setTextColor(selectedColor);
        binding.Footer16.txtAddress.setTextColor(selectedColor);
    }

    private void ChangeFooterColor(int selectedColor) {
        binding.rltvFooter1.setBackgroundColor(selectedColor);
        binding.rltvFooter2.setBackgroundColor(selectedColor);
        binding.Footer3.llSubthreeFooter.setBackgroundColor(selectedColor);
        binding.Footer2.llSubfourFooter.setBackgroundColor(selectedColor);
        binding.rltvFooter5.setBackgroundColor(selectedColor);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Log.e("Uri Path", result.getUri().getPath());
                Bitmap resultBitmap = Helper.UritoBitmap(result.getUri(), context);
                if (IsImage) {
                    binding.ivBanner.setImageBitmap(resultBitmap);
                    binding.ivBanner.setPadding(0, 0, 0, 0);
                    binding.ivBanner.setAdjustViewBounds(true);
                    binding.ivBanner.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    addSticker(resultBitmap);
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

        if (requestCode == 102) {

            if (resultCode == RESULT_OK) {
                Uri resultUri = data.getData();

                Log.d("sdfs", "sdfjfk" + resultUri.getPath());
                //  File f = new File(Config.convertMediaUriToPath(BannerEditingActivity.this, resultUri));
                Bitmap resultBitmap = Helper.UritoBitmap(resultUri, context);
                if (IsImage) {
                    binding.ivBanner.setImageBitmap(resultBitmap);
                    binding.ivBanner.setPadding(0, 0, 0, 0);
                    binding.ivBanner.setAdjustViewBounds(true);
                    binding.ivBanner.setScaleType(ImageView.ScaleType.FIT_XY);
                } else {
                    addSticker(resultBitmap);
                }

            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private void addSticker(Bitmap pica) {
        motionView.post(new Runnable() {
            @Override
            public void run() {
                Layer layer = new Layer();
                ImageEntity imageEntity = new ImageEntity(layer, pica, motionView.getWidth(), motionView.getHeight());
                motionView.addEntityAndPosition(imageEntity);
            }
        });
    }

    private void MyProfile() {
        Helper.Show_loader(context, false, false);
        RequestBody user_id = RequestBody.create(new SharedPreferencesHelper(context).getString(Config.USER_ID), MediaType.parse("text/plain"));
        Call<MyProfileResponse> call = apiInterface.my_profile(user_id);
        call.enqueue(new Callback<MyProfileResponse>() {
            @Override
            public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        if (!response.body().getData().getFacebookUsername().isEmpty()) {
                            new SharedPreferencesHelper(context).setBoolean(Config.isFbs, true);
                            Config.isFb = new SharedPreferencesHelper(context).getBoolean(Config.isFbs);
                        }
                        if (!response.body().getData().getInstagramUsername().isEmpty()) {
                            new SharedPreferencesHelper(context).setBoolean(Config.isInstas, true);
                            Config.isInsta = new SharedPreferencesHelper(context).getBoolean(Config.isInstas);
                        }
                        if (!response.body().getData().getWhatsappUsername().isEmpty()) {
                            new SharedPreferencesHelper(context).setBoolean(Config.isWhatsapps, true);
                            Config.isWhatsapp = new SharedPreferencesHelper(context).getBoolean(Config.isWhatsapps);
                        }
                        if (!response.body().getData().getYoutubeUsername().isEmpty()) {
                            new SharedPreferencesHelper(context).setBoolean(Config.isYoutubes, true);
                            Config.isYoutube = new SharedPreferencesHelper(context).getBoolean(Config.isYoutubes);
                        }

                        HandlePopulateData(response);
                    } catch (Exception e) {
                        Helper.cancel_loader();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(context, getString(R.string.Somethingswrong), Toast.LENGTH_SHORT).show();
                    Helper.cancel_loader();
                }
            }

            @Override
            public void onFailure(Call<MyProfileResponse> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });
    }

    private void HandlePopulateData(Response<MyProfileResponse> response) {
        MyProfileResponseData responseData = response.body().getData();
        FramePersonalSliderAdepter framePersonalSliderAdepter = new FramePersonalSliderAdepter(context, imgarrayList, responseData, "imageEdit");
        binding.vpPersonalFrame.setAdapter(framePersonalSliderAdepter);

        binding.Footer1.setModels(responseData);
        binding.Footer2.setModels(responseData);
        binding.Footer3.setModels(responseData);
        binding.Footer4.setModels(responseData);
        binding.Footer5.setModels(responseData);
        binding.Footer6.setModels(responseData);
        binding.Footer7.setModels(responseData);
        binding.Footer8.setModels(responseData);
        binding.Footer9.setModels(responseData);
        binding.Footer10.setModels(responseData);
        binding.Footer11.setModels(responseData);
        binding.Footer12.setModels(responseData);
        binding.Footer13.setModels(responseData);
        binding.Footer14.setModels(responseData);
        binding.Footer15.setModels(responseData);
        binding.Footer16.setModels(responseData);
        model = SharedPreferencesHelper.getSavedObjectFromPreference(context, STORAGE_BUSINESS_ACCOUNT, BusinessResponseData.class);
        AllFillTagRemove();
        RemoveAllFooter();

        if (model != null) {
            FrameSliderAdepter frameSliderAdepter = new FrameSliderAdepter(context, imgarrayList, model, "imageEdit");
            binding.vpFrame.setAdapter(frameSliderAdepter);

        }

        binding.Footer1.setModel(model);
        binding.Footer2.setModel(model);
        binding.Footer3.setModel(model);
        binding.Footer4.setModel(model);
        binding.Footer5.setModel(model);
        binding.Footer7.setModel(model);
        binding.Footer8.setModel(model);
        binding.Footer9.setModel(model);
        binding.Footer10.setModel(model);
        binding.Footer11.setModel(model);
        binding.Footer12.setModel(model);
        binding.Footer13.setModel(model);
        binding.Footer14.setModel(model);
        binding.Footer15.setModel(model);
        binding.Footer16.setModel(model);

        new Handler().postDelayed(
                () -> {
                    addTextCompanySticker();
                    if (model != null) {
                        AditCompanyText(model.getBusinessName(), CompanyTitletextEntity);

                    }
                }, 500);
        Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic()).into(binding.Footer6.ivfooterLogo);
        Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic()).into(binding.Footer8.profileImage);
        Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic()).into(binding.Footer9.profileImage);
        Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic()).into(binding.Footer10.profileImage);
        Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic()).into(binding.Footer11.profileImage);
        Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic()).into(binding.Footer12.profileImage);
        Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic()).into(binding.Footer13.profileImage);
        Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic()).into(binding.Footer15.profileImage);
        Glide.with(context).load(Config.IMG_PATH + responseData.getProfilePic()).into(binding.Footer16.profileImage);
        Glide.with(context).load(Config.IMG_PATH + model.getBusinessLogo()).into(binding.ivfooterLogo);
    }

    protected void addTextCompanySticker() {
        TextLayer textLayer = createTextLayer();
        CompanyTitletextEntity = new TextEntity(textLayer, motionView.getWidth(),
                motionView.getHeight(), fontProvider);
        motionView.addEntityAndPosition(CompanyTitletextEntity);
        // move text sticker up so that its not hidden under keyboard
        PointF center = CompanyTitletextEntity.absoluteCenter();
        center.y = center.y * 0.5F;
        CompanyTitletextEntity.moveCenterTo(center);
        CompanyTitletextEntity.getLayer().getFont().setColor(R.color.main_color);
        // redraw
        motionView.invalidate();
    }

    private void AditCompanyText(String text, TextEntity companyTitletextEntity) {
        if (companyTitletextEntity != null) {
            TextLayer textLayer = companyTitletextEntity.getLayer();
            if (text != null && textLayer != null)
                if (!text.equals(textLayer.getText())) {
                    textLayer.setText(text);
                    companyTitletextEntity.updateEntity();
                    motionView.invalidate();
                }
        }
    }


    private void DeleteCopmanyText(TextEntity companyTitletextEntity) {
        if (companyTitletextEntity != null) {
            companyTitletextEntity.setIsSelected(true);
            motionView.deletedSelectedEntity();
            motionView.invalidate();
            CompanyTitletextEntity = null;
        }
    }
}