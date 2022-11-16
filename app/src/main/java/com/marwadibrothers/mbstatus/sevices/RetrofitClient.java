package com.marwadibrothers.mbstatus.sevices;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String BASE_URL = "http://mbstatus.in/admin/api/";
    //public static final String BASE_URL = "http://mbstatus.royalample.in/admin/api/";
    private static Retrofit retrofit;
    private static Retrofit QbRetrofit;

    public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(15, TimeUnit.MINUTES)
                    .connectTimeout(15, TimeUnit.MINUTES)
                    .addNetworkInterceptor(new AddHeaderInterceptor())
                    .addInterceptor(interceptor).build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)

                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }


}
