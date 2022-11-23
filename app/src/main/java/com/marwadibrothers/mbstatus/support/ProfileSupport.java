package com.marwadibrothers.mbstatus.support;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.callbacks.ProfilebtnClick;
import com.marwadibrothers.mbstatus.databinding.ProfileIncludedBinding;
import com.marwadibrothers.mbstatus.databinding.ProfileViewLayoutBinding;
import com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper;
import com.marwadibrothers.mbstatus.utils.Config;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import static com.marwadibrothers.mbstatus.utils.Helper.ShowToast;

public class ProfileSupport {
    private ProfileIncludedBinding binding;
    private boolean IsfromEdit = false;
    private File IMAGE_FILE;
    public int COUNT = 0;
    private boolean CheckFacebook = false;
    private boolean CheckTwitter = false;
    private boolean CheckInstagram = false;
    private boolean CheckLinkdedIn = false;
    private boolean CheckWhtsapp = false;
    private boolean CheckTelegram = false;
    private boolean CheckYoutube = false;
    private Context context;
    private Activity activity;
    private String IsComeFrom;
    private ProfilebtnClick profilebtnClick;
    protected static final int REQUEST_CODE_GALLERY = 102;
    protected static final int  REQUEST_CODE_CAMERA = 101;

    public ProfileSupport(ProfileIncludedBinding binding, boolean IsfromEdit, String IsComeFrom, Context context, ProfilebtnClick profilebtnClick) {
        this.binding = binding;
        this.IsfromEdit = IsfromEdit;
        this.context = context;
        this.IsComeFrom = IsComeFrom;
        this.profilebtnClick = profilebtnClick;
        this.activity = (Activity) context;
        initviews();
        ClickEvents();
        HandleTags();
    }


    private void HandleTags() {
        binding.txtTagFacebook.setOnClickListener(v -> {
            if (!CheckFacebook) {
                if (COUNT != 4) {
                    CheckFacebook = true;
                    FillTagbg(binding.txtTagFacebook, binding.llFacebook);
                    IncreaseCount();
                }
                else {
                    CheckFacebook = false;
                    DecreaseCount();
                    Log.d("dgsgdhsgdhsh","hdhsdhsh");
                    UnFillTagbg(binding.txtTagFacebook, binding.llFacebook, binding.edtFacebook);

                }

            } else {
                CheckFacebook = false;
                DecreaseCount();
                Log.d("dgsgdhsgdhsh","hdhsdhsh");
                UnFillTagbg(binding.txtTagFacebook, binding.llFacebook, binding.edtFacebook);
            }

        });

        binding.txtTagTwitter.setOnClickListener(v -> {
            if (!CheckTwitter) {
                if (COUNT != 4) {
                    CheckTwitter = true;
                    FillTagbg(binding.txtTagTwitter, binding.llTwitter);
                    IncreaseCount();
                }
                else {
                    CheckTwitter = false;
                    DecreaseCount();
                    UnFillTagbg(binding.txtTagTwitter, binding.llTwitter, binding.edtTwitter);

                }

            } else {
                CheckTwitter = false;
                DecreaseCount();
                UnFillTagbg(binding.txtTagTwitter, binding.llTwitter, binding.edtTwitter);
            }

        });

        binding.txtTagInstagram.setOnClickListener(v -> {
            if (!CheckInstagram) {
                if (COUNT != 4) {
                    CheckInstagram = true;
                    FillTagbg(binding.txtTagInstagram, binding.llnstagram);
                    IncreaseCount();
                }
                else {
                    CheckInstagram = false;
                    DecreaseCount();
                    UnFillTagbg(binding.txtTagInstagram, binding.llnstagram, binding.edtInstagram);

                }

            } else {
                CheckInstagram = false;
                DecreaseCount();
                UnFillTagbg(binding.txtTagInstagram, binding.llnstagram, binding.edtInstagram);
            }

        });

        binding.txtTagLinkedin.setOnClickListener(v -> {
            if (!CheckLinkdedIn) {
                if (COUNT != 4) {
                    CheckLinkdedIn = true;
                    FillTagbg(binding.txtTagLinkedin, binding.llLinkedIn);
                    IncreaseCount();
                }else {
                    CheckLinkdedIn = false;
                    DecreaseCount();
                    UnFillTagbg(binding.txtTagLinkedin, binding.llLinkedIn, binding.edtLinkdedIn);

                }
            } else {
                CheckLinkdedIn = false;
                DecreaseCount();
                UnFillTagbg(binding.txtTagLinkedin, binding.llLinkedIn, binding.edtLinkdedIn);
            }

        });

        binding.txtTagWhatsapp.setOnClickListener(v -> {
            if (!CheckWhtsapp) {
                if (COUNT != 4) {
                    CheckWhtsapp = true;
                    FillTagbg(binding.txtTagWhatsapp, binding.llWhtsapp);
                    IncreaseCount();
                }
                else {
                    CheckWhtsapp = false;
                    DecreaseCount();
                    UnFillTagbg(binding.txtTagWhatsapp, binding.llWhtsapp, binding.edtWhatsapp);
                }
            } else {
                CheckWhtsapp = false;
                DecreaseCount();
                UnFillTagbg(binding.txtTagWhatsapp, binding.llWhtsapp, binding.edtWhatsapp);
            }

        });

        binding.txtTagTelegram.setOnClickListener(v -> {
            if (!CheckTelegram) {
                if (COUNT != 4) {
                    CheckTelegram = true;
                    FillTagbg(binding.txtTagTelegram, binding.llTelegram);
                    IncreaseCount();
                }
                else {
                    CheckTelegram = false;
                    DecreaseCount();
                    UnFillTagbg(binding.txtTagTelegram, binding.llTelegram, binding.edtTelegram);

                }
            } else {
                CheckTelegram = false;
                DecreaseCount();
                UnFillTagbg(binding.txtTagTelegram, binding.llTelegram, binding.edtTelegram);
            }

        });


        binding.txtTagYoutube.setOnClickListener(v -> {
            if (!CheckYoutube) {
                if (COUNT != 4) {
                    CheckYoutube = true;
                    FillTagbg(binding.txtTagYoutube, binding.llYoutube);
                    IncreaseCount();
                }
                else {
                    CheckYoutube = false;
                    DecreaseCount();
                    UnFillTagbg(binding.txtTagYoutube, binding.llYoutube, binding.edtYoutube);

                }
            } else {
                CheckYoutube = false;
                DecreaseCount();
                UnFillTagbg(binding.txtTagYoutube, binding.llYoutube, binding.edtYoutube);
            }

        });
    }

    private void IncreaseCount() {
        COUNT = COUNT + 1;
    }

    private void DecreaseCount() {
        COUNT = COUNT - 1;
    }

    public void UnFillTagbg(TextView textView, LinearLayout linearLayout, EditText editText) {
        textView.setBackgroundResource(R.drawable.tag_border_bg);
        textView.setTextColor(Color.BLACK);
        editText.setText("");
        linearLayout.setVisibility(View.GONE);
    }

    private void FillTagbg(TextView textView, LinearLayout linearLayout) {
        textView.setBackgroundResource(R.drawable.tag_bg);
        textView.setTextColor(Color.WHITE);
        linearLayout.setVisibility(View.VISIBLE);
    }


    private void initviews() {

    }

    private void ClickEvents() {
        binding.profileImage.setOnClickListener(v -> OpenGellery());
        binding.txtSuignSubmit.setOnClickListener(v -> {
            if (IsValid()) {
                profilebtnClick.ProfilebtnClick();
            }
        });
        //context.startActivity(new Intent(activity, BusinessProfileActivity.class))
    }

    private boolean isEmptyText(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean IsValid() {
        if (isEmptyText(binding.edtFullNamne)) {
            ShowToast(context.getString(R.string.Enter_FullName), context);
            return false;
        }
      /*  if (!Helper.isValidEmail(binding.edtMail.getText().toString())) {
            ShowToast(context.getString(R.string.Enter_Mail), context);
            return false;
        }*/
        if (isEmptyText(binding.edtAddress)) {
            ShowToast(context.getString(R.string.Enter_Address), context);
            return false;
        }

      /*  if (isEmptyText(binding.edtCompanyOrgnization)) {
            ShowToast(context.getString(R.string.Enter_Copmany), context);
            return false;
        }*/
       /* if (isEmptyText(binding.edtDesignation)) {
            ShowToast(context.getString(R.string.Enter_Designation), context);
            return false;
        }*/
        Log.e("COUNT", String.valueOf(COUNT));
      /*  if (COUNT < 4) {
            ShowToast(context.getString(R.string.Enter_Socail), context);
            return false;
        }*/

        if (IsVisible(binding.llFacebook)) {
            if (isEmptyText(binding.edtFacebook)) {
                ShowToast(context.getString(R.string.Enter_Socail), context);
                return false;
            }
            else {
                Log.d("dshdjhsdh","hjhshah   1");
                Config.isFb=true;
                new SharedPreferencesHelper(context).setBoolean(Config.isFbs,true);
            }
        }
        else {
            Log.d("dshdjhsdh","hjhshah   2");
            Config.isFb=false;
            new SharedPreferencesHelper(context).setBoolean(Config.isFbs,false);
        }


        if (IsVisible(binding.llTwitter)) {
            if (isEmptyText(binding.edtTwitter)) {
                ShowToast(context.getString(R.string.Enter_Socail), context);
                return false;
            }
            else {
                new SharedPreferencesHelper(context).setBoolean(Config.isTwitters,true);
                Config.isTwitter=true;
            }
        }else {
            new SharedPreferencesHelper(context).setBoolean(Config.isTwitters,false);
            Config.isTwitter=false;
        }

        if (IsVisible(binding.llnstagram)) {
            if (isEmptyText(binding.edtInstagram)) {
                ShowToast(context.getString(R.string.Enter_Socail), context);
                return false;
            }
            else {
                new SharedPreferencesHelper(context).setBoolean(Config.isInstas,true);
                Config.isInsta=true;
            }
        }else {
            new SharedPreferencesHelper(context).setBoolean(Config.isInstas,false);
            Config.isInsta=false;
        }

        if (IsVisible(binding.llLinkedIn)) {
            if (isEmptyText(binding.edtLinkdedIn)) {
                ShowToast(context.getString(R.string.Enter_Socail), context);
                return false;
            }
            else {
                new SharedPreferencesHelper(context).setBoolean(Config.isLinkedIns,true);
                Config.isLinkedIn=true;
            }
        }else {
            new SharedPreferencesHelper(context).setBoolean(Config.isLinkedIns,false);
            Config.isLinkedIn=false;
        }

        if (IsVisible(binding.llWhtsapp)) {
            if (isEmptyText(binding.edtWhatsapp)) {
                ShowToast(context.getString(R.string.Enter_Socail), context);
                return false;
            }
            else {
                new SharedPreferencesHelper(context).setBoolean(Config.isWhatsapps,true);
                Config.isWhatsapp=true;
            }
        }else {
            new SharedPreferencesHelper(context).setBoolean(Config.isWhatsapps,false);
            Config.isWhatsapp=false;
        }
        if (IsVisible(binding.llTelegram)) {
            if (isEmptyText(binding.edtTelegram)) {
                ShowToast(context.getString(R.string.Enter_Socail), context);
                return false;
            }
            else {
                new SharedPreferencesHelper(context).setBoolean(Config.isTelegrams,true);
                Config.isTelegram=true;
            }
        }else {
            new SharedPreferencesHelper(context).setBoolean(Config.isTelegrams,false);
            Config.isTelegram=false;
        }

        if (IsVisible(binding.llYoutube)) {
            if (isEmptyText(binding.edtYoutube)) {
                ShowToast(context.getString(R.string.Enter_Socail), context);
                return false;
            }
            else {
                new SharedPreferencesHelper(context).setBoolean(Config.isYoutubes,true);
                Config.isYoutube=true;
            }
        }
        else {
            new SharedPreferencesHelper(context).setBoolean(Config.isYoutubes,false);
            Config.isYoutube=false;
        }


        return true;
    }

    private boolean IsVisible(LinearLayout linearLayout) {
        if (linearLayout.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
    }


    private void OpenGellery() {

       /* Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
       activity.startActivityForResult(intent,  REQUEST_CODE_GALLERY);*/
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                .setAspectRatio(1,1)
                .start(activity);


    }



}
