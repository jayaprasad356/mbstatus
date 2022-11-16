package com.marwadibrothers.mbstatus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.adapter.MyMasterRecyclerAdapter;
import com.marwadibrothers.mbstatus.databinding.ActivityNotificationBinding;

import com.marwadibrothers.mbstatus.databinding.ItemNotyBinding;
import com.marwadibrothers.mbstatus.models.notification.NotificationResponse;
import com.marwadibrothers.mbstatus.models.notification.NotificationResponseData;
import com.marwadibrothers.mbstatus.models.profille.MyProfileResponse;
import com.marwadibrothers.mbstatus.sevices.ApiInterface;
import com.marwadibrothers.mbstatus.sevices.RetrofitClient;
import com.marwadibrothers.mbstatus.utils.Helper;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwadibrothers.mbstatus.utils.Helper.HideActionBar;

public class NotificationActivity extends AppCompatActivity {
    private ActivityNotificationBinding binding;
    private MyMasterRecyclerAdapter recyclerAdapter;
    private RecyclerView rvNotification;
    private Context context;
    private List<NotificationResponseData> arrayList = new ArrayList<>();
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        initviews();
        ClickEvents();
    }

    private void ClickEvents() {
        binding.toolbarSupport.ivBack.setOnClickListener(v -> finish());

    }

    private void initviews() {
        context = this;
        HideActionBar(this);
        rvNotification = binding.rvNotification;
        binding.toolbarSupport.txttitle.setText(R.string.Notification);
        apiInterface = RetrofitClient.getRetrofitInstance(this).create(ApiInterface.class);
        setRecyclerAdapter();
        initAdapter();
        if (Helper.checkInternet(context)) {
            MyNotification();
        }

    }


    private void setRecyclerAdapter() {
        rvNotification.setAdapter(recyclerAdapter);
    }

    private void updateNotificationlist(List<NotificationResponseData> list) {
        arrayList = list;
        if (recyclerAdapter == null) {
            initAdapter();
        } else {
            recyclerAdapter.updateNewList(list);
        }
        rvNotification.setAdapter(recyclerAdapter);
    }

    private void initAdapter() {
        recyclerAdapter = new MyMasterRecyclerAdapter(arrayList, R.layout.item_noty);
        rvNotification.setLayoutManager(new GridLayoutManager(context, 1));
        rvNotification.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnBind((viewDataBinding, position) -> {
            ItemNotyBinding binding = (ItemNotyBinding) viewDataBinding;
            NotificationResponseData model = arrayList.get(position);
            binding.setModel(model);
        });
    }

    private void MyNotification() {
        arrayList.clear();
        Helper.Show_loader(context, false, false);
        Call<NotificationResponse> call = apiInterface.get_notification_list();
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        updateNotificationlist(response.body().getData());
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
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });
    }


}