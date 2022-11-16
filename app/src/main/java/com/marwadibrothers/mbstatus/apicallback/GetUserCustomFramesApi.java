package com.marwadibrothers.mbstatus.apicallback;

import android.app.Activity;
import android.util.Log;

import com.marwadibrothers.mbstatus.base.App;
import com.marwadibrothers.mbstatus.models.customFrame.CustomFrame;
import com.marwadibrothers.mbstatus.sevices.ApiInterface;
import com.marwadibrothers.mbstatus.sevices.RetrofitClient;
import com.marwadibrothers.mbstatus.utils.Config;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUserCustomFramesApi {

    GetUserCustomFrames getUserCustomFrames;
    Activity context;
    private ApiInterface apiInterface;

    public GetUserCustomFramesApi(Activity context, GetUserCustomFrames getUserCustomFrames) {
        this.context = context;
        this.getUserCustomFrames = getUserCustomFrames;
        apiInterface = RetrofitClient.getRetrofitInstance(context).create(ApiInterface.class);
        callGetUserCustomFrameApi();
    }

    private void callGetUserCustomFrameApi() {
        RequestBody user_id = RequestBody.create(App.sharedPreferences.getString(Config.USER_ID), MediaType.parse("text/plain"));
        App.getUserCustomFrameList.clear();
        Call<CustomFrame> call = apiInterface.GetCustomFrame(user_id);
        call.enqueue(new Callback<CustomFrame>() {
            @Override
            public void onResponse(Call<CustomFrame> call, Response<CustomFrame> response) {
                if (response.isSuccessful()) {
                    try {
                        App.getUserCustomFrameList.addAll(response.body().getData());
                        Log.d("api", "getUserCustomFrameList-=-=--=->>>" + App.getUserCustomFrameList.size());
                        if (getUserCustomFrames != null)
                            getUserCustomFrames.onSuccessResponse();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<CustomFrame> call, Throwable t) {
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });
    }
}
