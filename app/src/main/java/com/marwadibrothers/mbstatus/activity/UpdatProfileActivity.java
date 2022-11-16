package com.marwadibrothers.mbstatus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.adapter.MyMasterRecyclerAdapter;
import com.marwadibrothers.mbstatus.callbacks.ProfilebtnClick;
import com.marwadibrothers.mbstatus.databinding.ActivityUpdatProfileBinding;
import com.marwadibrothers.mbstatus.databinding.ItemBuisnessListBinding;
import com.marwadibrothers.mbstatus.models.buiness.BusinessResponse;
import com.marwadibrothers.mbstatus.models.buiness.BusinessResponseData;
import com.marwadibrothers.mbstatus.models.profille.MyProfileResponse;
import com.marwadibrothers.mbstatus.models.profille.MyProfileResponseData;
import com.marwadibrothers.mbstatus.sevices.ApiInterface;
import com.marwadibrothers.mbstatus.sevices.RetrofitClient;
import com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper;
import com.marwadibrothers.mbstatus.support.ProfileSupport;
import com.marwadibrothers.mbstatus.utils.Config;
import com.marwadibrothers.mbstatus.utils.Helper;
import com.marwadibrothers.mbstatus.weidget.SweetAlert.SweetAlertDialog;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.marwadibrothers.mbstatus.activity.SplashScreenActivity.FCM_TOKEN;
import static com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper.STORAGE_BUSINESS_ACCOUNT;
import static com.marwadibrothers.mbstatus.utils.Config.SELECT_ACCOUNT;
import static com.marwadibrothers.mbstatus.utils.Config.SELECT_ACCOUNT_ID;
import static com.marwadibrothers.mbstatus.utils.Helper.HideActionBar;

public class UpdatProfileActivity extends AppCompatActivity implements ProfilebtnClick {
    private ActivityUpdatProfileBinding binding;
    private Context context;
    private File IMAGE_FILE;
    private ApiInterface apiInterface;
    private ProfileSupport profileSupport;
    private MyProfileResponseData responseData;
    int PERMISSION_ALL = 1;
    private RecyclerView rvBuinnessList;
    private MyMasterRecyclerAdapter BuisnessListAdapter;
    private List<BusinessResponseData> BusinessarrayList = new ArrayList<>();
    private SharedPreferencesHelper preferencesHelper;


    String[] PERMISSIONS = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_SMS,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_updat_profile);
        initviews();
        binding.llProfile.setVisibility(View.VISIBLE);
        binding.llBusinessList.setVisibility(View.GONE);

        binding.fbAddBusniess.setOnClickListener(v -> startActivity(new Intent(context, BusinessProfileActivity.class).putExtra(Config.FROM, Config.ADD_BUSINESS_REGULER)));


        binding.txtPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.llProfile.setVisibility(View.VISIBLE);
                binding.llBusinessList.setVisibility(View.GONE);
                binding.txtPersonal.setBackgroundResource(R.drawable.btn_bg);
                binding.txtPersonal.setTextColor(getResources().getColor(R.color.white));
                binding.txtBusiness.setBackgroundResource(R.drawable.daily_img_bg);
                binding.txtBusiness.setTextColor(getResources().getColor(R.color.main_color));
                binding.toolbarSupport.txttitle.setText(R.string.Update_Profile);
                binding.fbAddBusniess.setVisibility(View.GONE);

            }
        });
        binding.txtBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.llProfile.setVisibility(View.GONE);
                binding.llBusinessList.setVisibility(View.VISIBLE);
                binding.txtPersonal.setBackgroundResource(R.drawable.daily_img_bg);
                binding.txtBusiness.setBackgroundResource(R.drawable.btn_bg);
                binding.txtPersonal.setTextColor(getResources().getColor(R.color.main_color));
                binding.txtBusiness.setTextColor(getResources().getColor(R.color.white));
                binding.toolbarSupport.txttitle.setText(R.string.Update_Profile_1);
                binding.fbAddBusniess.setVisibility(View.VISIBLE);


            }
        });

    }

    private void initviews() {
        context = this;
        HideActionBar(this);
        profileSupport = new ProfileSupport(binding.ProfilSupport, false, Config.UPDATE_PROFILE, context, this);
        binding.toolbarSupport.ivBack.setOnClickListener(v -> finish());
        binding.toolbarSupport.txttitle.setText(R.string.Update_Profile);
        binding.ProfilSupport.toolbarSupport.ivBack.setVisibility(View.GONE);
        binding.ProfilSupport.toolbarSupport.txttitle.setVisibility(View.GONE);
        rvBuinnessList = binding.rvBuinnessList;
        preferencesHelper = new SharedPreferencesHelper(context);

        apiInterface = RetrofitClient.getRetrofitInstance(this).create(ApiInterface.class);
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }



        setBusinessRecyclerAdapter();
        BusinessinitAdapter();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Helper.checkInternet(context)) {
            MyProfile();
            GetBuinsessList();

        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                Log.d("sdfs", "sdfjfk" + resultUri.getPath());
                Log.d("sdfs", "sdfjfk" + data.getData());


                IMAGE_FILE = new File(resultUri.getPath());
                binding.ProfilSupport.profileImage.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        if (requestCode == 102) {

            if (resultCode == RESULT_OK) {
                Uri resultUri = data.getData();

                Log.d("sdfs", "sdfjfk" + resultUri.getPath());

                File file = new File(Config.convertMediaUriToPath(UpdatProfileActivity.this, resultUri));
                File compressedImageFile = null;
//                    imgpath1 = realPath;

                try {
                    compressedImageFile = new Compressor(context).compressToFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // IMAGE_FILE = new File( Config.convertMediaUriToPath(UpdatProfileActivity.this, resultUri));
                IMAGE_FILE = compressedImageFile;
                binding.ProfilSupport.profileImage.setImageURI(resultUri);
            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void ProfilebtnClick() {
        if (Helper.checkInternet(context)) {
            EditProfile();
        }
    }

    private void MyProfile() {
        Log.d("hsdsh","jskdjksjd"+new SharedPreferencesHelper(context).getString(Config.USER_ID));
        Helper.Show_loader(context, false, false);
        RequestBody user_id = RequestBody.create(new SharedPreferencesHelper(context).getString(Config.USER_ID), MediaType.parse("text/plain"));
        Call<MyProfileResponse> call = apiInterface.my_profile(user_id);
        call.enqueue(new Callback<MyProfileResponse>() {
            @Override
            public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        binding.ProfilSupport.setModel(response.body().getData());

                        Log.d("sjdhsdhj", "dshhshhdhsj" + Config.IMG_PATH + response.body().getData().getProfilePic());
                        Glide.with(context).load(Config.IMG_PATH + response.body().getData().getProfilePic()).into(binding.ProfilSupport.profileImage);
                        responseData = response.body().getData();
                        CheckSocailHandle(responseData);
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
            public void onFailure(Call<MyProfileResponse> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });
    }

    private void CheckSocailHandle(MyProfileResponseData responseData) {
        VisiBlitiGone();
        if (!responseData.getFacebookUsername().isEmpty()) {
            profileSupport.COUNT = profileSupport.COUNT + 1;
            VisibleView(binding.ProfilSupport.llFacebook, binding.ProfilSupport.txtTagFacebook);
        }
        if (!responseData.getTwitterUsername().isEmpty()) {
            profileSupport.COUNT = profileSupport.COUNT + 1;
            VisibleView(binding.ProfilSupport.llTwitter, binding.ProfilSupport.txtTagTwitter);
        }
        if (!responseData.getInstagramUsername().isEmpty()) {
            profileSupport.COUNT = profileSupport.COUNT + 1;
            VisibleView(binding.ProfilSupport.llnstagram, binding.ProfilSupport.txtTagInstagram);
        }
        if (!responseData.getLinkedinUsername().isEmpty()) {
            profileSupport.COUNT = profileSupport.COUNT + 1;
            VisibleView(binding.ProfilSupport.llLinkedIn, binding.ProfilSupport.txtTagLinkedin);
        }
        if (!responseData.getWhatsappUsername().isEmpty()) {
            profileSupport.COUNT = profileSupport.COUNT + 1;
            VisibleView(binding.ProfilSupport.llWhtsapp, binding.ProfilSupport.txtTagWhatsapp);
        }
        if (!responseData.getTelegramUsername().isEmpty()) {
            profileSupport.COUNT = profileSupport.COUNT + 1;
            VisibleView(binding.ProfilSupport.llTelegram, binding.ProfilSupport.txtTagTelegram);
        }
        if (!responseData.getYoutubeUsername().isEmpty()) {
            profileSupport.COUNT = profileSupport.COUNT + 1;
            VisibleView(binding.ProfilSupport.llYoutube, binding.ProfilSupport.txtTagYoutube);
        }
    }

    private void VisiBlitiGone() {
        profileSupport.COUNT = 0;
        profileSupport.UnFillTagbg(binding.ProfilSupport.txtTagFacebook, binding.ProfilSupport.llFacebook, binding.ProfilSupport.edtFacebook);
        profileSupport.UnFillTagbg(binding.ProfilSupport.txtTagTwitter, binding.ProfilSupport.llTwitter, binding.ProfilSupport.edtTwitter);
        profileSupport.UnFillTagbg(binding.ProfilSupport.txtTagInstagram, binding.ProfilSupport.llnstagram, binding.ProfilSupport.edtInstagram);
        profileSupport.UnFillTagbg(binding.ProfilSupport.txtTagLinkedin, binding.ProfilSupport.llLinkedIn, binding.ProfilSupport.edtLinkdedIn);
        profileSupport.UnFillTagbg(binding.ProfilSupport.txtTagWhatsapp, binding.ProfilSupport.llWhtsapp, binding.ProfilSupport.edtWhatsapp);
        profileSupport.UnFillTagbg(binding.ProfilSupport.txtTagTelegram, binding.ProfilSupport.llTelegram, binding.ProfilSupport.edtTelegram);
        profileSupport.UnFillTagbg(binding.ProfilSupport.txtTagYoutube, binding.ProfilSupport.llYoutube, binding.ProfilSupport.edtYoutube);
    }

    private void VisibleView(LinearLayout linearLayout, TextView textView) {
        linearLayout.setVisibility(View.VISIBLE);
        textView.setBackgroundResource(R.drawable.tag_bg);
        textView.setTextColor(Color.WHITE);
    }

    private void EditProfile() {
        Helper.Show_loader(context, false, false);
        MultipartBody.Part body = null;
        Log.e("newToken1", FCM_TOKEN);

        // Log.d("dfjdfj","dfhs"+IMAGE_FILE.getPath());
        // Log.d("dfjdfj","dfhs"+MediaType.parse(Helper.getMimeType(Helper.replacePath(IMAGE_FILE.getPath()))));
        if (IMAGE_FILE != null) {
            RequestBody requestFile = RequestBody.create(IMAGE_FILE, MediaType.parse("image/*"));
            body = MultipartBody.Part.createFormData("profile_pic", IMAGE_FILE.getPath(), requestFile);

            Log.d("fjdsfsf", "jfkdsfj" + body);
        }
        RequestBody user_id = RequestBody.create(new SharedPreferencesHelper(context).getString(Config.USER_ID), MediaType.parse("text/plain"));
        RequestBody designation = RequestBody.create(binding.ProfilSupport.edtDesignation.getText().toString(), MediaType.parse("text/plain"));
        RequestBody full_name = RequestBody.create(binding.ProfilSupport.edtFullNamne.getText().toString(), MediaType.parse("text/plain"));
        RequestBody email = RequestBody.create(binding.ProfilSupport.edtMail.getText().toString(), MediaType.parse("text/plain"));
        RequestBody address = RequestBody.create(binding.ProfilSupport.edtAddress.getText().toString(), MediaType.parse("text/plain"));
        RequestBody company_org_name = RequestBody.create(binding.ProfilSupport.edtCompanyOrgnization.getText().toString(), MediaType.parse("text/plain"));
        RequestBody facebook_username = RequestBody.create(binding.ProfilSupport.edtFacebook.getText().toString(), MediaType.parse("text/plain"));
        RequestBody twitter_username = RequestBody.create(binding.ProfilSupport.edtTwitter.getText().toString(), MediaType.parse("text/plain"));
        RequestBody instagram_username = RequestBody.create(binding.ProfilSupport.edtInstagram.getText().toString(), MediaType.parse("text/plain"));
        RequestBody linkedin_username = RequestBody.create(binding.ProfilSupport.edtLinkdedIn.getText().toString(), MediaType.parse("text/plain"));
        RequestBody whatsapp_username = RequestBody.create(binding.ProfilSupport.edtWhatsapp.getText().toString(), MediaType.parse("text/plain"));
        RequestBody telegram_username = RequestBody.create(binding.ProfilSupport.edtTelegram.getText().toString(), MediaType.parse("text/plain"));
        RequestBody youtube_username = RequestBody.create(binding.ProfilSupport.edtYoutube.getText().toString(), MediaType.parse("text/plain"));
        RequestBody mobile_number = RequestBody.create(responseData.getMobileNumberwithoutcode(), MediaType.parse("text/plain"));
        RequestBody fcm_token = RequestBody.create(FCM_TOKEN, MediaType.parse("text/plain"));

        Call<ResponseBody> call = apiInterface.edit_profile(user_id, fcm_token, designation, full_name, email, address, company_org_name, facebook_username,
                twitter_username, instagram_username, linkedin_username, whatsapp_username, telegram_username
                , youtube_username, mobile_number, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        String responseString = response.body().string();
                        //Log.d("sjdhsdhj","hh"+ new JSONObject(responseString).getString("message"));
                        Toast.makeText(context, new JSONObject(responseString).getString("message"), Toast.LENGTH_SHORT).show();
                        if (new JSONObject(responseString).getBoolean("status")) {
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


    private void setBusinessRecyclerAdapter() {
        rvBuinnessList.setLayoutManager(new GridLayoutManager(this, 1));
        rvBuinnessList.setAdapter(BuisnessListAdapter);
    }

    private void BusinessUpdateProductlist(List<BusinessResponseData> list) {
        BusinessarrayList = list;
        if (BuisnessListAdapter == null) {
            BusinessinitAdapter();
        } else {
            BuisnessListAdapter.updateNewList(list);
        }
        rvBuinnessList.setAdapter(BuisnessListAdapter);
    }

    private void BusinessinitAdapter() {
        BuisnessListAdapter = new MyMasterRecyclerAdapter(BusinessarrayList, R.layout.item_buisness_list);
        rvBuinnessList.setAdapter(BuisnessListAdapter);
        BuisnessListAdapter.setOnBind((viewDataBinding, position) -> {
            ItemBuisnessListBinding binding = (ItemBuisnessListBinding) viewDataBinding;
            BusinessResponseData model = BusinessarrayList.get(position);
            binding.setModel(model);
            Glide.with(context).load(Config.IMG_PATH + model.getBusinessLogo()).into(binding.imageView);
            binding.ivEdit.setOnClickListener(v -> {
                Gson gson = new Gson();
                String Buisnessobj = gson.toJson(model);
                Intent intent = new Intent(context, BusinessProfileActivity.class);
                intent.putExtra(Config.FROM, Config.EDIT_BUSINESS);
                intent.putExtra(Config.DATA, Buisnessobj);
                startActivity(intent);
            });


            binding.imbDelete.setOnClickListener(v -> {
                ShowAlert(model, position);
            });
        });
    }

    private void ShowAlert(BusinessResponseData model, int position) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.Are_you_sure))
                .setContentText(getString(R.string.Are_you_sure_delete))
                .setConfirmText("Yes")
                .setConfirmClickListener(sDialog -> {
                    // reuse previous dialog instance
                    sDialog.dismiss();
                    if (Helper.checkInternet(context)) {
                        DeleteBusiness(model, position);
                    }
                })
                .setCancelText("No")
                .setCancelClickListener(Dialog::dismiss)
                .show();
    }

    private void DeleteBusiness(BusinessResponseData model, int position) {
        Helper.Show_loader(context, false, false);
        RequestBody business_id = RequestBody.create(model.getBusinessId(), MediaType.parse("text/plain"));
        Call<ResponseBody> call = apiInterface.delete_business(business_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        if (response.isSuccessful()) {
                            String responseString = response.body().string();
                            JSONObject object = new JSONObject(responseString);
                            if (object.getBoolean("status")) {
                                if (model.getBusinessId().equals(new SharedPreferencesHelper(context).getString(SELECT_ACCOUNT_ID))) {
                                    int count = BusinessarrayList.size() - 1;
                                    Log.e("Count", String.valueOf(count));
                                    if (count != 0) {
                                        new SharedPreferencesHelper(context).setString(SELECT_ACCOUNT_ID, BusinessarrayList.get(0).getBusinessId());
                                        SharedPreferencesHelper.saveObjectToSharedPreference(context, STORAGE_BUSINESS_ACCOUNT, BusinessarrayList.get(0));
                                    } else {
                                        Log.e("Count", "RemoveBusinessDatatoStorage");
                                        SharedPreferencesHelper.saveObjectToSharedPreference(context, STORAGE_BUSINESS_ACCOUNT, new BusinessResponseData());
                                    }
                                }
                                if (Helper.checkInternet(context)) {
                                    GetBuinsessList();
                                }
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

    private void GetBuinsessList() {
        RequestBody user_id = RequestBody.create(new SharedPreferencesHelper(context).getString(Config.USER_ID), MediaType.parse("text/plain"));
        BusinessarrayList.clear();
        Call<BusinessResponse> call = apiInterface.get_user_business_list(user_id);
        call.enqueue(new Callback<BusinessResponse>() {
            @Override
            public void onResponse(Call<BusinessResponse> call, Response<BusinessResponse> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        BusinessUpdateProductlist(response.body().getData());

                        if (preferencesHelper.getBoolean(SELECT_ACCOUNT)) {
                            SelectionLoop(response.body().getData());
                        } else {
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
            public void onFailure(Call<BusinessResponse> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });

    }


    private void SelectionLoop(List<BusinessResponseData> arrayList) {
        Log.e("SELECT_ACCOUNT_ID", preferencesHelper.getString(SELECT_ACCOUNT_ID));
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getBusinessId().equals(preferencesHelper.getString(SELECT_ACCOUNT_ID))) {
                Log.e("getBusinessId", "SAMEe");
                arrayList.get(i).setSetselectedAcc(true);
                break;
            }
        }

    }

}