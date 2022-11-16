package com.marwadibrothers.mbstatus.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.iid.FirebaseInstanceId;
import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.databinding.ActivitySplashScreenBinding;
import com.marwadibrothers.mbstatus.models.AppOpen;
import com.marwadibrothers.mbstatus.sevices.ApiInterface;
import com.marwadibrothers.mbstatus.sevices.RetrofitClient;
import com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper;
import com.marwadibrothers.mbstatus.utils.Config;
import com.marwadibrothers.mbstatus.utils.Helper;

import org.json.JSONArray;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwadibrothers.mbstatus.utils.Config.IS_LOGIN;
import static com.marwadibrothers.mbstatus.utils.Helper.HideActionBar;

public class SplashScreenActivity extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;
    private Context context;
    private final int SPLASH_TIME = 3000;
    private SharedPreferencesHelper preferencesHelper;
    public static String FCM_TOKEN = "";
    private ApiInterface apiInterface;
    Date last_date, current_date;
    AlertDialog alertDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);
        //GetFcmToken();
        RunApp();
        appOpen();
//        Log.d("jdjjdfj","djsjds"+preferencesHelper.getBoolean(Config.ISFIRSTTIME));

        binding.ivlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));

            }
        });


        //PopOpen();
        //UpdateApp();

        // Testing();
    }

    /*public void UpdateApp(){
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(result -> {

            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                Log.d("sdsjdhjshdsj","dfjhsdjf    dd");
//                requestUpdate(result);

            } else {
                Log.d("sdsjdhjshdsj","dfjhsdjf    dssd");

            }
        });
    }*/
    private void Testing() {
        JSONArray j1 = null;
        for (int i = 0; i < j1.length(); i++) {
        }
    }

    private void RunApp() {
        context = this;
        HideActionBar(this);
        preferencesHelper = new SharedPreferencesHelper(context);
        new Handler().postDelayed(
                () -> {

                    if (preferencesHelper.getBoolean(Config.SELECTED_LANGUAGE)) {
                        Helper.LanguyageSelected(context);
                        if (new SharedPreferencesHelper(context).getBoolean(IS_LOGIN)) {
                            startActivity(new Intent(context, MainActivity.class));
                        } else {
                            startActivity(new Intent(context, OtpActivity.class));
                        }
                        finish();
                    } else {
                        preferencesHelper.setBoolean(Config.SELECTED_LANGUAGE, true);
                        Helper.LanguageDialog(context, "splash");

                    }
                }, SPLASH_TIME);
    }

//    private void GetFcmToken() {
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
//            String newToken = instanceIdResult.getToken();
//            Log.e("newToken", newToken);
//            FCM_TOKEN = newToken;
//        });
//
//    }

    private void appOpen() {
        apiInterface = RetrofitClient.getRetrofitInstance(this).create(ApiInterface.class);

        Call<AppOpen> call = apiInterface.appOpen();
        call.enqueue(new Callback<AppOpen>() {
            @Override
            public void onResponse(Call<AppOpen> call, Response<AppOpen> response) {

                if (response.isSuccessful()) {
                    Log.d("cjdfjdjf", "fdjjdjjf" + response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<AppOpen> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("jsdsdjs", "sgsg xx" + t.getMessage());
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });

    }

    /*private void PopOpen() {
        preferencesHelper.setBoolean(Config.isPopUpOpen,true);
        apiInterface = RetrofitClient.getRetrofitInstance(this).create(ApiInterface.class);

        Call<PopUpModel> call = apiInterface.popUPOpen();
        call.enqueue(new Callback<PopUpModel>() {
            @Override
            public void onResponse(Call<PopUpModel> call, Response<PopUpModel> response) {

                if (response.isSuccessful()) {
                    Log.d("cjdfjdjf","ddddd"+response.body().getMessage());

                    View view = View.inflate(SplashScreenActivity.this, R.layout.start_alert_raw, null);
                    //showHomeScreenFacebookAds(view);
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(SplashScreenActivity.this);
                    alertDialogBuilder.setView(view);

                    ImageView ivImage=view.findViewById(R.id.ivImage);
                    TextView tvCancle=view.findViewById(R.id.tvCancle);

                    if(response.body().getData().getImage()!=null){
                        Glide.with(context).load(Config.IMG_PATH+response.body().getData().getImage()).into(ivImage);
                    }

                    tvCancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(response.body().getData().getLink_type().equals("1")){
                                startActivity(new Intent(context, SubCategoryActivity.class)
                                        .putExtra(SUB_CAT_ID, response.body().getData().getSub_category_id())
                                        .putExtra(SUB_CAT_NAME,response.body().getData().getSub_category_name())
                                        .putExtra(Config.FROM, NORMAL));
                                alertDialog.dismiss();
                                finish();
                            }

                            if(response.body().getData().getLink_type().equals("2")){
                                startActivity(new Intent(context, PackageActivity.class));
                                alertDialog.dismiss();
                                finish();
                            }
                            if(response.body().getData().getLink_type().equals("3")){
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.body().getData().getLink()));
                                startActivity(browserIntent);
                                alertDialog.dismiss();
                                finish();
                            }
                        }
                    });

                    alertDialog = alertDialogBuilder.create();
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<PopUpModel> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("mvkfdsfkldf", "sgsg xx" + t.getMessage());
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });

    }*/

}