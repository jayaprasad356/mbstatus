package com.marwadibrothers.mbstatus.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.adapter.CustomFrameDemoAdapter;
import com.marwadibrothers.mbstatus.apicallback.GetAppSetting;
import com.marwadibrothers.mbstatus.apicallback.GetAppSettingApi;
import com.marwadibrothers.mbstatus.apicallback.GetUserCustomFramesApi;
import com.marwadibrothers.mbstatus.databinding.ActivityCustomFrameDemoBinding;
import com.marwadibrothers.mbstatus.models.customFrameDemo.CustomFrameDemo;
import com.marwadibrothers.mbstatus.models.customFrameDemo.CustomFrameDemoItem;
import com.marwadibrothers.mbstatus.models.notification.NotificationResponse;
import com.marwadibrothers.mbstatus.models.notification.NotificationResponseData;
import com.marwadibrothers.mbstatus.sevices.ApiInterface;
import com.marwadibrothers.mbstatus.sevices.RetrofitClient;
import com.marwadibrothers.mbstatus.utils.Helper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwadibrothers.mbstatus.utils.Helper.HideActionBar;


public class CustomFrameDemoActivity extends AppCompatActivity {

    private ActivityCustomFrameDemoBinding binding;
    private Context context;

    private RecyclerView rvCustomFrameDemo;
    private CustomFrameDemoAdapter customFrameDemoAdapter;
    private List<CustomFrameDemoItem> arrayList = new ArrayList<>();

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_frame_demo);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_custom_frame_demo);

        initViews();
        ClickEvents();
    }

    private void initViews() {
        context = this;
        HideActionBar(this);
        rvCustomFrameDemo = binding.rvCustomFrameDemo;
        binding.toolbarSupport.txttitle.setText(R.string.custom_frame_demo_title);
        apiInterface = RetrofitClient.getRetrofitInstance(this).create(ApiInterface.class);
        initAdapter();
        if (Helper.checkInternet(context)) {
            customFrameDemo();
        }
    }

    String whatsAppNo = "";

    private void ClickEvents() {
        binding.toolbarSupport.ivBack.setOnClickListener(v -> finish());
        new GetAppSettingApi(CustomFrameDemoActivity.this, new GetAppSetting() {
            @Override
            public void onSuccessResponse(String appNm, String appEmail, String appVersion, String whatsAppNumber) {
                whatsAppNo = whatsAppNumber;
            }
        });

        binding.txtCustomFrameDemo.setOnClickListener(v -> openWhatsApp());
        binding.ivCustomFrameDemo.setOnClickListener(v -> openWhatsApp());
    }

    private void openWhatsApp() {
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/919521735997"));
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/" + whatsAppNo));
        if (whatsAppNo.length() == 10)
            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/91" + whatsAppNo));
        context.startActivity(browserIntent);

    }

    private void initAdapter() {
        customFrameDemoAdapter = new CustomFrameDemoAdapter(context, arrayList);
        rvCustomFrameDemo.setLayoutManager(new GridLayoutManager(context, 1));
        rvCustomFrameDemo.setAdapter(customFrameDemoAdapter);
    }

    private void customFrameDemo() {
        arrayList.clear();
        Helper.Show_loader(context, false, false);
        Call<CustomFrameDemo> call = apiInterface.get_custom_frames_demo();
        call.enqueue(new Callback<CustomFrameDemo>() {
            @Override
            public void onResponse(Call<CustomFrameDemo> call, Response<CustomFrameDemo> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        arrayList.clear();
                        arrayList.addAll(response.body().getData());
                        customFrameDemoAdapter.notifyDataSetChanged();
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
            public void onFailure(Call<CustomFrameDemo> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });
    }
}