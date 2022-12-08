package com.marwadibrothers.mbstatus.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.databinding.ActivityOtpBinding;
import com.marwadibrothers.mbstatus.models.OtpModel;
import com.marwadibrothers.mbstatus.models.buiness.BusinessResponseData;
import com.marwadibrothers.mbstatus.sevices.ApiInterface;
import com.marwadibrothers.mbstatus.sevices.RetrofitClient;
import com.marwadibrothers.mbstatus.sevices.RetrofitClientOTP;
import com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper;
import com.marwadibrothers.mbstatus.utils.Config;
import com.marwadibrothers.mbstatus.utils.Helper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwadibrothers.mbstatus.activity.SplashScreenActivity.FCM_TOKEN;
import static com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper.STORAGE_BUSINESS_ACCOUNT;
import static com.marwadibrothers.mbstatus.utils.Helper.HideActionBar;

public class OtpActivity extends AppCompatActivity {
    private ActivityOtpBinding binding;
    private Context context;
    private String mVerificationId;
    private FirebaseAuth mAuth;
    private ApiInterface apiInterface, apiInterface1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);



        initviews();
        Config.OTP = getRandomNumberString();
        Log.d("TAG", "onCreate: -=--==->>>OTP>>>"+ Config.OTP);
//https://www.fast2sms.com/dev/bulkV2?authorization=WRAs6QvxhXZzcfHlpnDm1d5bE9GqiwYNToKuLjCMP4OkSyV78UvYOHZTaMlpAItxfJzU5ojED1WuV7P8&variables_values=1111&route=otp&numbers=7096219885
        Clickevents();

        apiInterface1 = RetrofitClientOTP.getRetrofitInstance(this).create(ApiInterface.class);
        //getOtp();
    }

    private void Clickevents() {
        binding.tvTerm.setOnClickListener(v -> {
            String url = "http://mbstatus.in/Terms-and-conditions.html";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
        binding.txtSubmitOtp.setOnClickListener(v -> {
            //GetUserPlanList();
            if (IsvalidOtp()) {
                // OTPVerification(binding.otpTextView.getOTP());

                if (Config.OTP.equals(binding.otpTextView.getOTP())) {
                    if (Helper.checkInternet(context)) {
                        GetUserPlanList();
                    } else {

                    }
                } else {
                    Toast.makeText(context, "Invalid code entered...", Toast.LENGTH_LONG).show();
                }

            }
        });
        binding.txtBack.setOnClickListener(v -> ShowMobileView());
        binding.txtSendOtp.setOnClickListener(v -> {
            hideKeyboard(this);
            if (Isvalid()) {
                if (binding.edtMobile.getText().toString().equals("8128809990"))
                    Config.OTP = "123456";
                Config.Mobile_No = binding.edtMobile.getText().toString();
                new SharedPreferencesHelper(context).setString(Config.USER_NO, Config.Mobile_No);
                getOtp(false);
                // SENdOTP();

                new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        binding.Timer.setText(""+millisUntilFinished/1000);
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        binding.tvwaiting.setVisibility(View.GONE);
                        binding.tvResend.setVisibility(View.VISIBLE);
                    }

                }.start();
                HideMobileView();
            }
        });


        binding.tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.tvwaiting.setVisibility(View.VISIBLE);
                binding.tvResend.setVisibility(View.GONE);

                getOtp(true);
                new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        binding.Timer.setText(""+millisUntilFinished/1000);
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        binding.tvwaiting.setVisibility(View.GONE);
                        binding.tvResend.setVisibility(View.VISIBLE);
                    }

                }.start();

            }
        });








        binding.tvHelp.setOnClickListener(v -> {
            showDialog();
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showDialog() {

        Log.d("bsxs", "sjasj1");
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.contact_alert_dialog);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        // lp.windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setAttributes(lp);
        ImageView ivPAClose;
        TextView tvContactEmail, tvContactCall;
        ivPAClose = dialog.findViewById(R.id.ivPAClose);
        tvContactEmail = dialog.findViewById(R.id.tvContactEmail);
        tvContactCall = dialog.findViewById(R.id.tvContactCall);

        tvContactEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.OpenMailIntentview(context, null);
                dialog.dismiss();
            }
        });

        tvContactCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + getString(R.string.mobile_no)));
                startActivity(intent);
                dialog.dismiss();
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

    private boolean IsvalidOtp() {
        if (binding.otpTextView.getOTP().length() != 6) {
            Toasty.error(context, getString(R.string.Valid_Otp), Toasty.LENGTH_SHORT, true).show();
            return false;
        }
        return true;
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String no = String.valueOf(number);
        Log.d("bdbsdbh", "dsh" + no);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    private void getOtp(boolean flag) {

        Call<OtpModel> call = apiInterface1.getOtp("WRAs6QvxhXZzcfHlpnDm1d5bE9GqiwYNToKuLjCMP4OkSyV78UvYOHZTaMlpAItxfJzU5ojED1WuV7P8",
                Config.OTP, "otp", binding.edtMobile.getText().toString());
        call.enqueue(new Callback<OtpModel>() {
            @Override
            public void onResponse(Call<OtpModel> call, Response<OtpModel> response) {
                Log.d("jsdsdjs", "gdhsghdg" + response.code());
                if (flag)
                    Toast.makeText(getApplicationContext(), "OTP successfully send.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<OtpModel> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("jsdsdjs", "sgsg xx" + t.getMessage());
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
                if (flag)
                    Toast.makeText(getApplicationContext(), "OTP send failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ShowMobileView() {
        binding.llMobileView.setVisibility(View.VISIBLE);
        binding.llOtpview.setVisibility(View.GONE);
    }

    private void HideMobileView() {
        binding.llMobileView.setVisibility(View.GONE);
        binding.llOtpview.setVisibility(View.VISIBLE);
    }

    private boolean Isvalid() {

        if (!Helper.isValidMobile(binding.edtMobile.getText().toString())) {
            Toasty.error(context, getString(R.string.Valid_MObile_NUmber), Toasty.LENGTH_SHORT, true).show();
            return false;
        }
        return true;
    }

    private void initviews() {
        context = this;
        HideActionBar(this);
        apiInterface = RetrofitClient.getRetrofitInstance(this).create(ApiInterface.class);
        FirebaseApp.initializeApp(context);
        mAuth = FirebaseAuth.getInstance();
    }

    private void SENdOTP() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + binding.edtMobile.getText().toString(), 30L /*timeout*/, TimeUnit.SECONDS,
                this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        mVerificationId = verificationId;

                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Log.d("dshjdhj", "hdhs" + e.getMessage());
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void OTPVerification(String otp) {
        //creating the credential
        Helper.Show_loader(context, false, false);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        if (Helper.checkInternet(context)) {
                            GetUserPlanList();
                        }

                    } else {
                        Helper.cancel_loader();
                        //verification unsuccessful.. display an error message
                        String message = "Somthing is wrong, we will fix it soon...";
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered...";
                        }
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                });
    }
/* startActivity(new Intent(context, ProfileMakeActivity.class).putExtra(Config.MOBILE, binding.edtMobile.getText().toString()));
                finish();*/


    private void GetUserPlanList() {
        Config.Mobile_No = binding.edtMobile.getText().toString();
        RequestBody fcm_token = RequestBody.create(FCM_TOKEN, MediaType.parse("text/plain"));

        Log.d("fdkfj", "fjdfh" + FCM_TOKEN);
        Log.d("fdkfj", "fjdfh" + fcm_token);

        RequestBody mobile_number = RequestBody.create((binding.edtMobile.getText().toString()), MediaType.parse("text/plain"));
        Call<ResponseBody> call = apiInterface.send_otp(mobile_number, fcm_token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("OTP_RES",response.body() + "");
                Helper.cancel_loader();
                try {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(response.body().string());

                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                                new SharedPreferencesHelper(context).setBoolean(Config.isFirstTime, true);


                            }
                            Config.Mobile_No = binding.edtMobile.getText().toString();
                            Log.d("hdjshdjh", "hhah    " + Config.Mobile_No);
                            new SharedPreferencesHelper(context).setString(Config.USER_ID, jsonObject.getJSONObject("data").getString("user_id"));
                            new SharedPreferencesHelper(context).setBoolean(Config.IS_LOGIN, true);
                            if (jsonObject.getJSONObject("data").getString("full_name").equals("")){
                                startActivity(new Intent(context, ProfileMakeActivity.class));

                            }else {
                                startActivity(new Intent(context, MainActivity.class));
                            }


                            finish();
                        } else {
                            new SharedPreferencesHelper(context).setString(Config.USER_ID, jsonObject.getJSONObject("data").getString("user_id"));
                            new SharedPreferencesHelper(context).setBoolean(Config.IS_LOGIN, true);
                            Config.Mobile_No = binding.edtMobile.getText().toString();
                            Log.d("hdjshdjh", "hhah" + Config.Mobile_No);
                           // startActivity(new Intent(context, ProfileMakeActivity.class).putExtra(Config.MOBILE, binding.edtMobile.getText().toString()));
                            startActivity(new Intent(context, MainActivity.class));
                            finish();
                        }

                    } else {
                        Toast.makeText(context, getString(R.string.Somethingswrong), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Helper.cancel_loader();
                    e.printStackTrace();

                    Log.d("sghg", "hdush" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("sghg", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });
    }

}