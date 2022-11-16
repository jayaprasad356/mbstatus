package com.marwadibrothers.mbstatus.sevices;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientOTP {

    public static final String BASE_URL = "https://www.fast2sms.com/dev/";
    private static Retrofit retrofit;
    private static Retrofit QbRetrofit;

    public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
