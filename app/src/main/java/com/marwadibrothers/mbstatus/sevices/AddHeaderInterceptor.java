package com.marwadibrothers.mbstatus.sevices;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AddHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("api_key", "MB_STATUS_API_KEY");
        return chain.proceed(builder.build());
    }
}