package com.marwadibrothers.mbstatus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.gson.Gson;
import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.databinding.ActivityBusinessProfileBinding;
import com.marwadibrothers.mbstatus.models.buiness.BusinessResponseData;
import com.marwadibrothers.mbstatus.sevices.ApiInterface;
import com.marwadibrothers.mbstatus.sevices.RetrofitClient;
import com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper;
import com.marwadibrothers.mbstatus.utils.Config;
import com.marwadibrothers.mbstatus.utils.Helper;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper.STORAGE_BUSINESS_ACCOUNT;
import static com.marwadibrothers.mbstatus.utils.Config.IS_LOGIN;
import static com.marwadibrothers.mbstatus.utils.Config.SELECT_ACCOUNT_ID;
import static com.marwadibrothers.mbstatus.utils.Helper.HideActionBar;
import static com.marwadibrothers.mbstatus.utils.Helper.ShowToast;

public class BusinessProfileActivity extends AppCompatActivity {
    private ActivityBusinessProfileBinding binding;
    private File IMAGE_FILE;
    private Context context;
    private ApiInterface apiInterface;
    private BusinessResponseData model;
    private SharedPreferencesHelper preferencesHelper;
    protected static final int REQUEST_CODE_GALLERY = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_business_profile);
        Initview();
        ClickEvents();
        DataPopulate();
        loadAd();
    }

    private void DataPopulate() {
        if (getIntent().getStringExtra(Config.FROM).equals(Config.EDIT_BUSINESS)) {
            Gson gson = new Gson();
            model = gson.fromJson(getIntent().getStringExtra(Config.DATA), BusinessResponseData.class);
            Glide.with(context).load(Config.IMG_PATH + model.getBusinessLogo()).into(binding.profileImage);
            binding.setModel(model);
        }
    }

    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder()

                .build();

        binding.adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                // Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        binding.adView.loadAd(adRequest);
    }

    private void ClickEvents() {
        binding.profileImage.setOnClickListener(v -> OpenGellery());
        binding.txtSubmit.setOnClickListener(v -> {
            if (Isvaid()) {
                if (Helper.checkInternet(context)) {
                    if (getIntent().getStringExtra(Config.FROM).equals(Config.EDIT_BUSINESS)) {
                        EditBuainess();
                    } else {
                        AddBuainess();
                    }

                }
            }
        });
//        startActivity(new Intent(context, MainActivity.class))
    }

    private boolean Isvaid() {

        if (isEmptyText(binding.edtCompanyOrgnization)) {
            ShowToast(context.getString(R.string.Enter_Copmany), context);
            return false;
        }

        if (!Helper.isValidEmail(binding.edtMail.getText().toString())) {
            ShowToast(context.getString(R.string.Enter_Mail), context);
            return false;
        }
        if (isEmptyText(binding.edtAddress)) {
            ShowToast(context.getString(R.string.Enter_Address), context);
            return false;
        }

        if (!Helper.isValidMobile(binding.edtContactNo.getText().toString())) {
            ShowToast(context.getString(R.string.Enter_Contact), context);
            return false;
        }

        if (!getIntent().getStringExtra(Config.FROM).equals(Config.EDIT_BUSINESS)) {
            if (IMAGE_FILE == null) {
                Helper.ShowToast(getString(R.string.Select_image), context);
                return false;
            }
        }

        return true;
    }

    private boolean isEmptyText(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            return true;
        }
        return false;
    }

    private void Initview() {
        context = this;
        HideActionBar(this);
        preferencesHelper = new SharedPreferencesHelper(context);
        apiInterface = RetrofitClient.getRetrofitInstance(this).create(ApiInterface.class);
    }

    private void OpenGellery() {
      /*  CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                .start(this);*/

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                IMAGE_FILE = new File(resultUri.getPath());
                binding.profileImage.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        if (requestCode == 102) {

            if (resultCode == RESULT_OK) {
                Uri resultUri = data.getData();

                Log.d("sdfs", "sdfjfk" + resultUri.getPath());

                File file = new File( Config.convertMediaUriToPath(BusinessProfileActivity.this, resultUri));
                File compressedImageFile = null;
//                    imgpath1 = realPath;

                try {
                    compressedImageFile = new Compressor(context).compressToFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // IMAGE_FILE = new File( Config.convertMediaUriToPath(UpdatProfileActivity.this, resultUri));
                IMAGE_FILE = compressedImageFile;                binding.profileImage.setImageURI(resultUri);
            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void AddBuainess() {
        Helper.Show_loader(context, false, false);
        MultipartBody.Part body;
       // RequestBody requestFile = RequestBody.create(IMAGE_FILE, MediaType.parse(Helper.getMimeType(Helper.replacePath(IMAGE_FILE.getPath()))));
        RequestBody requestFile = RequestBody.create(IMAGE_FILE, MediaType.parse("image/*"));
        body = MultipartBody.Part.createFormData("business_logo", IMAGE_FILE.getPath(), requestFile);
        RequestBody user_id = RequestBody.create(new SharedPreferencesHelper(context).getString(Config.USER_ID), MediaType.parse("text/plain"));
        RequestBody business_name = RequestBody.create(binding.edtCompanyOrgnization.getText().toString(), MediaType.parse("text/plain"));
        RequestBody business_website = RequestBody.create(binding.edtWebsite.getText().toString(), MediaType.parse("text/plain"));
        RequestBody business_address = RequestBody.create(binding.edtAddress.getText().toString(), MediaType.parse("text/plain"));
        RequestBody business_email = RequestBody.create(binding.edtMail.getText().toString(), MediaType.parse("text/plain"));
        RequestBody business_phone_no = RequestBody.create(binding.edtContactNo.getText().toString(), MediaType.parse("text/plain"));

        Call<ResponseBody> call = apiInterface.add_business(user_id, business_name,business_name, business_website, business_address, business_email, business_phone_no, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        String responseString = response.body().string();
                        Config.isBusinessDone = true;
                        //  Toast.makeText(context, new JSONObject(responseString).getString("message"), Toast.LENGTH_SHORT).show();
                        if (new JSONObject(responseString).getBoolean("status")) {
                            if (getIntent().getStringExtra(Config.FROM).equals(Config.ADD_BUSINESS)) {
                                model = new BusinessResponseData();
                                model.setSetselectedAcc(true);
                                JSONObject jsonObject = new JSONObject(responseString).getJSONObject("data");
                                model.setBusinessAddress(jsonObject.getString("business_address"));
                                model.setBusinessEmail(jsonObject.getString("business_email"));
                                model.setBusinessId(jsonObject.getString("business_id"));
                                if (jsonObject.has("business_logo"))
                                    model.setBusinessLogo(jsonObject.getString("business_logo"));
                                model.setBusinessName(jsonObject.getString("business_name"));
                                model.setBusinessPhoneNo(jsonObject.getString("business_phone_no"));
                                model.setBusinessWebsite(jsonObject.getString("business_website"));
                                SharedPreferencesHelper.saveObjectToSharedPreference(context, STORAGE_BUSINESS_ACCOUNT, model);

                                new SharedPreferencesHelper(context).setBoolean(IS_LOGIN, true);
                                // startActivity(new Intent(context, MainActivity.class));
                            }
                            finish();

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

    private void EditBuainess() {
        Helper.Show_loader(context, false, false);
        MultipartBody.Part body = null;
        if (IMAGE_FILE != null) {
            // RequestBody requestFile = RequestBody.create(IMAGE_FILE, MediaType.parse(Helper.getMimeType(Helper.replacePath(IMAGE_FILE.getPath()))));

            RequestBody requestFile = RequestBody.create(IMAGE_FILE, MediaType.parse("image/*"));
            body = MultipartBody.Part.createFormData("business_logo", IMAGE_FILE.getPath(), requestFile);
        }
        RequestBody user_id = RequestBody.create(new SharedPreferencesHelper(context).getString(Config.USER_ID), MediaType.parse("text/plain"));
        RequestBody business_name = RequestBody.create(binding.edtCompanyOrgnization.getText().toString(), MediaType.parse("text/plain"));
        RequestBody business_website = RequestBody.create(binding.edtWebsite.getText().toString(), MediaType.parse("text/plain"));
        RequestBody business_address = RequestBody.create(binding.edtAddress.getText().toString(), MediaType.parse("text/plain"));
        RequestBody business_email = RequestBody.create(binding.edtMail.getText().toString(), MediaType.parse("text/plain"));
        RequestBody business_phone_no = RequestBody.create(binding.edtContactNo.getText().toString(), MediaType.parse("text/plain"));
        RequestBody business_id = RequestBody.create(model.getBusinessId(), MediaType.parse("text/plain"));

        Call<ResponseBody> call = apiInterface.edit_business(user_id, business_name, business_website, business_address, business_email, business_id, business_phone_no, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        String responseString = response.body().string();
                        Toast.makeText(context, new JSONObject(responseString).getString("message"), Toast.LENGTH_SHORT).show();
                        if (new JSONObject(responseString).getBoolean("status")) {
                            if (new JSONObject(responseString).getJSONObject("data").getString("business_id").equals(preferencesHelper.getString(SELECT_ACCOUNT_ID))) {
                                Log.e("getBusinessId", "SAMEe");
                                JSONObject jsonObject = new JSONObject(responseString).getJSONObject("data");
                                model = SharedPreferencesHelper.getSavedObjectFromPreference(context, STORAGE_BUSINESS_ACCOUNT, BusinessResponseData.class);
                                model.setSetselectedAcc(true);
                                model.setBusinessAddress(jsonObject.getString("business_address"));
                                model.setBusinessEmail(jsonObject.getString("business_email"));
                                model.setBusinessId(jsonObject.getString("business_id"));
                                if (jsonObject.has("business_logo"))
                                    model.setBusinessLogo(jsonObject.getString("business_logo"));
                                model.setBusinessName(jsonObject.getString("business_name"));
                                model.setBusinessPhoneNo(jsonObject.getString("business_phone_no"));
                                model.setBusinessWebsite(jsonObject.getString("business_website"));
                                SharedPreferencesHelper.saveObjectToSharedPreference(context, STORAGE_BUSINESS_ACCOUNT, model);
                            }
                            finish();
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
}
