package com.marwadibrothers.mbstatus.apicallback;

import android.app.Activity;
import android.util.Log;

import com.marwadibrothers.mbstatus.base.App;
import com.marwadibrothers.mbstatus.models.customFrame.CustomFrame;
import com.marwadibrothers.mbstatus.sevices.ApiInterface;
import com.marwadibrothers.mbstatus.sevices.RetrofitClient;
import com.marwadibrothers.mbstatus.utils.Config;
import com.marwadibrothers.mbstatus.utils.Helper;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAppSettingApi {

    GetAppSetting getAppSetting;
    Activity context;
    private ApiInterface apiInterface;

    public GetAppSettingApi(Activity context, GetAppSetting getAppSetting) {
        this.context = context;
        this.getAppSetting = getAppSetting;
        apiInterface = RetrofitClient.getRetrofitInstance(context).create(ApiInterface.class);
        callGetAppSettingApi();
    }

    private void callGetAppSettingApi() {
//        RequestBody user_id = RequestBody.create(App.sharedPreferences.getString(Config.USER_ID), MediaType.parse("text/plain"));
        Call<ResponseBody> call = apiInterface.get_application_settings();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
//                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject jsonObject = new JSONObject(response.body().string()).getJSONObject("data");
                        getAppSetting.onSuccessResponse(jsonObject.getString("app_name"),
                                jsonObject.getString("app_email"),
                                jsonObject.getString("app_version"),
                                jsonObject.getString("whatsapp_no"));
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
}
