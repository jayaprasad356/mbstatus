package com.marwadibrothers.mbstatus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.adapter.MyMasterRecyclerAdapter;
import com.marwadibrothers.mbstatus.databinding.ActivityPackageBinding;
import com.marwadibrothers.mbstatus.databinding.ItemPackageSliderListBinding;
import com.marwadibrothers.mbstatus.models.payment.PaymentResponse;
import com.marwadibrothers.mbstatus.models.payment.PaymentResponseData;
import com.marwadibrothers.mbstatus.models.subscriptionplan.PlanResponse;
import com.marwadibrothers.mbstatus.models.subscriptionplan.PlanResponseData;
import com.marwadibrothers.mbstatus.sevices.ApiInterface;
import com.marwadibrothers.mbstatus.sevices.RetrofitClient;
import com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper;
import com.marwadibrothers.mbstatus.utils.Config;
import com.marwadibrothers.mbstatus.utils.Helper;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwadibrothers.mbstatus.utils.Helper.HideActionBar;

public class PackageActivity extends AppCompatActivity implements PaymentResultListener {
    private ActivityPackageBinding binding;
    private MyMasterRecyclerAdapter recyclerAdapter;
    private RecyclerView rvPackage;
    private Context context;
    private List<PlanResponseData> arrayList = new ArrayList<>();
    private ApiInterface apiInterface;
    private PlanResponseData SellectefPlanResponseData;
    private PaymentResponseData paymentResponseData;
    private ArrayList<String> Plan_id_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_package);
        initviews();
        Clickevents();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(context, MainActivity.class);
        startActivity(i);
        finishAffinity();
    }

    private void Clickevents() {
        binding.toolbarSupport.ivBack.setOnClickListener(v -> finish());
    }

    private void initviews() {
        context = this;
        HideActionBar(this);
        apiInterface = RetrofitClient.getRetrofitInstance(this).create(ApiInterface.class);
        rvPackage = binding.rvPackage;
        binding.toolbarSupport.txttitle.setTextColor(getResources().getColor(R.color.white));
        binding.toolbarSupport.txttitle.setText(R.string.Subscription_Plan);
        binding.toolbarSupport.ivBack.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
        setRecyclerAdapter();
        initAdapter();

        if (Helper.checkInternet(context)) {
            GetPlanList();
        }
    }

    private void setRecyclerAdapter() {
        rvPackage.setAdapter(recyclerAdapter);
    }

    private void updateProductlist(List<PlanResponseData> list) {
        arrayList = list;
//        arrayList.clear();
//        arrayList.addAll(list);
        if (recyclerAdapter == null) {
            initAdapter();
        } else {
            recyclerAdapter.updateNewList(list);
        }
        rvPackage.setAdapter(recyclerAdapter);
        GetUserPlanList();
    }

    PlanResponseData model;

    private void initAdapter() {
        recyclerAdapter = new MyMasterRecyclerAdapter(arrayList, R.layout.item_package_slider_list);
        rvPackage.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnBind((viewDataBinding, position) -> {
            ItemPackageSliderListBinding binding = (ItemPackageSliderListBinding) viewDataBinding;
//            PlanResponseData model = arrayList.get(position);
            model = arrayList.get(position);
            PlanResponseData model1 = arrayList.get(position);
            binding.setModel(model1);
            Glide.with(context).load(Config.IMG_PATH + model1.getPlanImage()).placeholder(R.drawable.placeholderlogo).error(R.drawable.placeholderlogo).into(binding.ivPackageImg);
            binding.txtPrice.setText(model1.getPlanAmount());
            binding.tvExpiryDate.setText("Your Plan Expiry Date" + model1.getPlanDuration());
            binding.txtDuration.setText("Duration " + model1.getPlanDuration() + " " + model1.getPlanDurationType());

            if (Integer.parseInt(model1.getPlanDuration()) <= 6) {
                binding.txtMonth.setText(R.string.Month);
//                binding.txtPriceDuration.setText(model.getPlanAmount() + "/- " + model.getPlanDuration() + " " + getString(R.string.Month));
            } else {
                binding.txtMonth.setText(R.string.Year);
//                binding.txtPriceDuration.setText(model.getPlanAmount() + "/- " + model.getPlanDuration() + " " + getString(R.string.Year));
            }

            binding.txtPriceDuration.setText(model1.getPlanAmount() + "/- " + model1.getPlanDuration() + " " + model1.getPlanDurationType());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tctDescription.setText(Html.fromHtml(model1.getPlanDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                binding.tctDescription.setText(Html.fromHtml(model1.getPlanDescription()));
            }

            if (Plan_id_list.contains(model1.getBusinessPlanId())) {
                Log.e("Contain", "Contain");
                binding.txtByNow.setText(R.string.Plan_Selected);
                binding.cardBuNow.setOnClickListener(null);
            } else {
                Log.e("Not Contain", "Not Contain");
                binding.txtByNow.setText(R.string.Buy_Now);
                binding.cardBuNow.setOnClickListener(v -> {
                    model = arrayList.get(position);
                    SellectefPlanResponseData = model;
                    startPayment(model.getPlanAmount());
                });
            }
        });
    }

    private void GetPlanList() {
//        arrayList.clear();
        Helper.Show_loader(context, false, false);
        Call<PlanResponse> call = apiInterface.get_plan_list();
        call.enqueue(new Callback<PlanResponse>() {
            @Override
            public void onResponse(Call<PlanResponse> call, Response<PlanResponse> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
//                        arrayList = response.body().getData();
                        arrayList.clear();
                        arrayList.addAll(response.body().getData());
                        GetUserPlanList1();
                        Log.d("fdfjfj", "jdfdfdj" + arrayList.get(0).getPlanDurationType());
                        Log.d("fdfjfj", "jdfdfdj" + arrayList.get(0).getPlanDuration());
                        //GetPaymentList();

                        if (Config.ifFromPopup) {
//                            updateProductlist(arrayList);
                            Log.d("djsdjsjd", "a");
                            Config.ifFromPopup = false;
                        } else if (!new SharedPreferencesHelper(context).getBoolean(Config.Buy_Plan)) {
//                            updateProductlist(arrayList);
                            Log.d("djsdjsjd", "b");
                            GetPaymentList();

                        } else {
                            Log.d("djsdjsjd", "c");
                            GetPaymentList();
//                            updateProductlist(arrayList);
                        }
                        updateProductlist(arrayList);
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
            public void onFailure(Call<PlanResponse> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });
    }

    private void GetUserPlanList1() {
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
                            JSONObject jsonObjects = jsonObject.getJSONArray("data").getJSONObject(0);
                            Log.d("jfrdjfdhyn", "fjsjfksk" + jsonObjects.getString("plan_expiry_date"));

                            if (jsonObject.getBoolean("status")) {
                                new SharedPreferencesHelper(context).setBoolean(Config.Buy_Plan, true);

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
                Log.d("dsndhhsjhdj", "jhsjdjs" + t.getMessage());
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.e("onPaymentSuccess", s);
        if (Helper.checkInternet(context)) {
            AddPlan(s);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e("onPaymentError", s);
        Log.e("onPaymentError", String.valueOf(i));
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();

    }

    public void startPayment(String amount) {
        final Activity activity = this;
        final Checkout co = new Checkout();
        co.setKeyID(paymentResponseData.getCredValue());
        try {
            JSONObject options = new JSONObject();
            options.put("name", getResources().getString(R.string.app_name));
            options.put("currency", "INR");
            double total = Double.parseDouble(amount);
            total = total * 100;
            options.put("amount", total);
            JSONObject preFill = new JSONObject();
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void AddPlan(String paymentSuccessfullId) {
        RequestBody plan_amount = RequestBody.create(SellectefPlanResponseData.getPlanAmount(), MediaType.parse("text/plain"));
        String planDuration = SellectefPlanResponseData.getPlanDuration() + " " + SellectefPlanResponseData.getPlanDurationType();
        RequestBody plan_duration = RequestBody.create(planDuration, MediaType.parse("text/plain"));
        RequestBody plan_id = RequestBody.create(SellectefPlanResponseData.getBusinessPlanId(), MediaType.parse("text/plain"));
        RequestBody user_id = RequestBody.create(new SharedPreferencesHelper(context).getString(Config.USER_ID), MediaType.parse("text/plain"));
        RequestBody payment_id = RequestBody.create(paymentSuccessfullId, MediaType.parse("text/plain"));
        Call<ResponseBody> call = apiInterface.add_plan(user_id, plan_id, plan_duration, plan_amount, payment_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        if (response.isSuccessful()) {
//                            RazorpayClient razorpay = new RazorpayClient("[YOUR_KEY_ID]", "[YOUR_KEY_SECRET]");
                            RazorpayClient razorpay = new RazorpayClient(paymentResponseData.getCredValue(), "i2Oih7BKKljgyKBz4Qro3a3z");
                            try {
                                JSONObject captureRequest = new JSONObject();
                                captureRequest.put("amount", model.getPlanAmount());
                                captureRequest.put("currency", "INR");

                                Payment payment = razorpay.payments.capture(paymentSuccessfullId, captureRequest);
                            } catch (RazorpayException e) {
                                // Handle Exception
                                System.out.println(e.getMessage());
                            }

                            String responseString = response.body().string();
                            JSONObject object = new JSONObject(responseString);
                            Toast.makeText(context, object.getString("message"), Toast.LENGTH_SHORT).show();
                            new SharedPreferencesHelper(context).setBoolean(Config.Buy_Plan, true);
                            Log.d("fsjfjsdfjdsffsd", "jj");
                            if (object.getBoolean("status")) {
                                startActivity(new Intent(context, MainActivity.class));
                                finish();
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

    private void GetPaymentList() { // get Razorpay key
        Call<PaymentResponse> call = apiInterface.get_payment_list();
        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                if (response.isSuccessful()) {

                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus()) {
                                paymentResponseData = response.body().getData().get(0);
//                                GetUserPlanList();
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
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });
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
                            JSONObject jsonObjects = jsonObject.getJSONArray("data").getJSONObject(0);
                            Log.d("jfrdjfdhyn", "fjsjfksk" + jsonObjects.getString("plan_expiry_date"));

                            // Log.d("fjksjfdsxxxfskfl","fjsjfksk"+jsonObject.getString("plan_expiry_date"));
                            binding.tvExpiryDate.setVisibility(View.VISIBLE);
                            binding.tvExpiryDate.setText("Expiry date " + jsonObjects.getString("plan_expiry_date"));
                            // Log.d("jdsjdssd","jsdjskdj"+jsonObjects.getBoolean("status"));

                            if (jsonObject.getBoolean("status")) {
                                //  Log.d("fjksjfdsxxxfskfl","fjsjfksk"+jsonObject.getString("plan_expiry_date"));
                                HandleUserPlanList(jsonObject.getJSONArray("data"));
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
                Log.d("dsndhhsjhdj", "jhsjdjs" + t.getMessage());
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });
    }

    private void HandleUserPlanList(JSONArray data) {

        Log.e("datadata", String.valueOf(data));
        Plan_id_list = new ArrayList<>();
        try {
            for (int i = 0; i < data.length(); i++) {
                Plan_id_list.add(data.getJSONObject(i).getString("plan_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<PlanResponseData> list = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
                /*if (!arrayList.get(i).getBusinessPlanId().equals(Plan_id_list.get(0))) {
                    arrayList.remove(i);
                }*/
            if (arrayList.get(i).getBusinessPlanId().equals(Plan_id_list.get(0)))
                list.add(arrayList.get(i));
        }
        Log.d("jdsdsdjsj", "sdjksjdksj-=-=-=-=>>>" + arrayList.size());
        Log.d("jdsdsdjsj", "list-=-=-=-=>>>" + list.size());
//            updateProductlist(arrayList);

        arrayList = list;
//        arrayList.clear();
//        arrayList.addAll(list);
        if (recyclerAdapter == null) {
            initAdapter();
        } else {
            recyclerAdapter.updateNewList(list);
        }
        rvPackage.setAdapter(recyclerAdapter);
    }
}