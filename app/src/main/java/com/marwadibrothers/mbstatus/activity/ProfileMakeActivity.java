package com.marwadibrothers.mbstatus.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.callbacks.ProfilebtnClick;
import com.marwadibrothers.mbstatus.databinding.ActivityProfileMakeBinding;
import com.marwadibrothers.mbstatus.sevices.ApiInterface;
import com.marwadibrothers.mbstatus.sevices.RetrofitClient;
import com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper;
import com.marwadibrothers.mbstatus.support.ProfileSupport;
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

import static com.marwadibrothers.mbstatus.activity.SplashScreenActivity.FCM_TOKEN;
import static com.marwadibrothers.mbstatus.utils.Helper.HideActionBar;


public class ProfileMakeActivity extends AppCompatActivity implements ProfilebtnClick {
    private ActivityProfileMakeBinding binding;
    private File IMAGE_FILE;
    private int COUNT = 4;
    private boolean CheckFacebook = true;
    private boolean CheckTwitter = true;
    private boolean CheckInstagram = true;
    private boolean CheckLinkdedIn = true;
    private boolean CheckWhtsapp = false;
    private boolean CheckTelegram = false;
    private boolean CheckYoutube = false;
    private Context context;
    protected static final int REQUEST_CODE_GALLERY = 102;

    private ApiInterface apiInterface;
    private SharedPreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_make);
        initviews();
        ClickEvents();
//        HandleTags();
    }

/*    private void HandleTags() {
        binding.txtTagFacebook.setOnClickListener(v -> {
            if (!CheckFacebook) {
                if (COUNT != 4) {
                    CheckFacebook = true;
                    FillTagbg(binding.txtTagFacebook, binding.llFacebook);
                    IncreaseCount();
                }

            } else {
                CheckFacebook = false;
                DecreaseCount();
                UnFillTagbg(binding.txtTagFacebook, binding.llFacebook);
            }

        });

        binding.txtTagTwitter.setOnClickListener(v -> {
            if (!CheckTwitter) {
                if (COUNT != 4) {
                    CheckTwitter = true;
                    FillTagbg(binding.txtTagTwitter, binding.llTwitter);
                    IncreaseCount();
                }

            } else {
                CheckTwitter = false;
                DecreaseCount();
                UnFillTagbg(binding.txtTagTwitter, binding.llTwitter);
            }

        });

        binding.txtTagInstagram.setOnClickListener(v -> {
            if (!CheckInstagram) {
                if (COUNT != 4) {
                    CheckInstagram = true;
                    FillTagbg(binding.txtTagInstagram, binding.llnstagram);
                    IncreaseCount();
                }

            } else {
                CheckInstagram = false;
                DecreaseCount();
                UnFillTagbg(binding.txtTagInstagram, binding.llnstagram);
            }

        });

        binding.txtTagLinkedin.setOnClickListener(v -> {
            if (!CheckLinkdedIn) {
                if (COUNT != 4) {
                    CheckLinkdedIn = true;
                    FillTagbg(binding.txtTagLinkedin, binding.llLinkedIn);
                    IncreaseCount();
                }
            } else {
                CheckLinkdedIn = false;
                DecreaseCount();
                UnFillTagbg(binding.txtTagLinkedin, binding.llLinkedIn);
            }

        });

        binding.txtTagWhatsapp.setOnClickListener(v -> {
            if (!CheckWhtsapp) {
                if (COUNT != 4) {
                    CheckWhtsapp = true;
                    FillTagbg(binding.txtTagWhatsapp, binding.llWhtsapp);
                    IncreaseCount();
                }
            } else {
                CheckWhtsapp = false;
                DecreaseCount();
                UnFillTagbg(binding.txtTagWhatsapp, binding.llWhtsapp);
            }

        });

        binding.txtTagTelegram.setOnClickListener(v -> {
            if (!CheckTelegram) {
                if (COUNT != 4) {
                    CheckTelegram = true;
                    FillTagbg(binding.txtTagTelegram, binding.llTelegram);
                    IncreaseCount();
                }
            } else {
                CheckTelegram = false;
                DecreaseCount();
                UnFillTagbg(binding.txtTagTelegram, binding.llTelegram);
            }

        });


        binding.txtTagYoutube.setOnClickListener(v -> {
            if (!CheckYoutube) {
                if (COUNT != 4) {
                    CheckYoutube = true;
                    FillTagbg(binding.txtTagYoutube, binding.llYoutube);
                    IncreaseCount();
                }
            } else {
                CheckYoutube = false;
                DecreaseCount();
                UnFillTagbg(binding.txtTagYoutube, binding.llYoutube);
            }

        });
    }*/

    private void IncreaseCount() {
        COUNT = COUNT + 1;
    }

    private void DecreaseCount() {
        COUNT = COUNT - 1;
    }

    private void UnFillTagbg(TextView textView, LinearLayout linearLayout) {
        textView.setBackgroundResource(R.drawable.tag_border_bg);
        textView.setTextColor(Color.BLACK);
        linearLayout.setVisibility(View.GONE);
    }

    private void FillTagbg(TextView textView, LinearLayout linearLayout) {
        textView.setBackgroundResource(R.drawable.tag_bg);
        textView.setTextColor(Color.WHITE);
        linearLayout.setVisibility(View.VISIBLE);
    }

    private void ClickEvents() {
//        binding.profileImage.setOnClickListener(v -> OpenGellery());
//        binding.txtOtpSubmit.setOnClickListener(v -> startActivity(new Intent(context, BusinessProfileActivity.class)));
    }

    private void initviews() {
        context = this;
        HideActionBar(this);
        preferencesHelper = new SharedPreferencesHelper(context);
        apiInterface = RetrofitClient.getRetrofitInstance(this).create(ApiInterface.class);
        new ProfileSupport(binding.ProfilSupport, false, Config.SIGNUP, context, this);
        binding.ProfilSupport.toolbarSupport.ivBack.setVisibility(View.GONE);
        binding.ProfilSupport.toolbarSupport.txttitle.setText(R.string.Profile_make);
    }


    private void OpenGellery() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
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

                File file = new File( Config.convertMediaUriToPath(ProfileMakeActivity.this, resultUri));
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
      /*  if (requestCode == REQUEST_CODE_GALLERY) {

            if (resultCode == RESULT_OK) {
                Uri resultUri =  data.getData();
                IMAGE_FILE = new File(resultUri.getPath());
                binding.ProfilSupport.profileImage.setImageURI(resultUri);
            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }

        }*/

    }

    @Override
    public void ProfilebtnClick() {
        if (IMAGE_FILE != null) {
            if (Helper.checkInternet(context)) {
                Singup();
            }
        } else {
            Helper.ShowToast(getString(R.string.Select_image), context);
        }
    }

    private void Singup() {
        Helper.Show_loader(context, false, false);
        MultipartBody.Part body = null;
        Log.e("newToken1", FCM_TOKEN);

        // Log.d("dfjdfj","dfhs"+IMAGE_FILE.getPath());
        // Log.d("dfjdfj","dfhs"+MediaType.parse(Helper.getMimeType(Helper.replacePath(IMAGE_FILE.getPath()))));
        if (IMAGE_FILE != null) {
            RequestBody requestFile = RequestBody.create(IMAGE_FILE,MediaType.parse("image/*"));
            body = MultipartBody.Part.createFormData("profile_pic", IMAGE_FILE.getPath(), requestFile);

            Log.d("fjdsfsf","jfkdsfj"+body);
        }
        //MultipartBody.Part      body;
        //RequestBody requestFile = RequestBody.create(IMAGE_FILE, MediaType.parse(Helper.getMimeType("image/*")));
        /*RequestBody requestFile = RequestBody.create(IMAGE_FILE, MediaType.parse(Helper.getMimeType(Helper.replacePath(IMAGE_FILE.getPath()))));
        MultipartBody.Part  body = MultipartBody.Part.createFormData("profile_pic", IMAGE_FILE.getPath(), requestFile);
        */
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
        RequestBody mobile_number = RequestBody.create(Config.Mobile_No, MediaType.parse("text/plain"));
        RequestBody fcm_token = RequestBody.create(FCM_TOKEN, MediaType.parse("text/plain"));
        RequestBody user_id = RequestBody.create(new SharedPreferencesHelper(context).getString(Config.USER_ID), MediaType.parse("text/plain"));
        Call<ResponseBody> call = apiInterface.edit_profile(user_id,fcm_token, designation, full_name, email, address, company_org_name, facebook_username,
                twitter_username, instagram_username, linkedin_username, whatsapp_username, telegram_username
                , youtube_username, mobile_number, body);
      /*  Call<ResponseBody> call = apiInterface.signup(full_name, fcm_token, designation, email, address, company_org_name, facebook_username,
                twitter_username, instagram_username, linkedin_username, whatsapp_username, telegram_username
                , youtube_username, mobile_number, body);*/
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        String responseString = response.body().string();
                        Config.isProfileDone=true;
                        //Toast.makeText(context, new JSONObject(responseString).getString("message"), Toast.LENGTH_SHORT).show();
                        if (new JSONObject(responseString).getBoolean("status")) {
                            preferencesHelper.setString(Config.USER_ID, new JSONObject(responseString).getJSONObject("data").getString("user_id"));
                            //startActivity(new Intent(context, BusinessProfileActivity.class).putExtra(Config.FROM, Config.ADD_BUSINESS));
                            finish();
                        }
                    } catch (Exception e) {
                        Helper.cancel_loader();
                        e.printStackTrace();
                        Log.d("jsjsd","shh"+e.getMessage());
                    }

                } else {
                    Helper.cancel_loader();
                    Log.d("jsjsd","sucess not");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("jsjsd","shh"+t.getMessage());
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });

    }
}