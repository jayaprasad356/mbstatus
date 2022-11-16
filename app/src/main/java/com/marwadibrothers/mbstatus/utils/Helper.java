package com.marwadibrothers.mbstatus.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.Ad;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.marwadibrothers.mbstatus.BuildConfig;
import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.activity.MainActivity;
import com.marwadibrothers.mbstatus.activity.OtpActivity;
import com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper;
import com.marwadibrothers.mbstatus.weidget.SweetAlert.SweetAlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLConnection;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import static com.marwadibrothers.mbstatus.utils.Config.IS_LOGIN;

public class Helper {

    public static Bitmap UritoBitmap(Uri paths, Context context) {
        Bitmap bitmap = null;
        try {
            if (paths != null) {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), paths);
            }
        } catch (Exception e) {
            //handle exception
        }

        return bitmap;
    }

    public static void LanguageDialog(Context context, String tag) {
        SharedPreferencesHelper preferencesHelper = new SharedPreferencesHelper(context);
        final boolean[] isSelectTemp = {false};

        final String[] Language = {"ENGLISH", "हिन्दी"};
        int checkedItem = 0;

        Activity activity = (Activity) context;

        if (preferencesHelper.getString(Config.LANGUAGE).equals("")) {
            checkedItem = 0;
        } else if (preferencesHelper.getString(Config.LANGUAGE).equals("ENGLISH")) {
            checkedItem = 0;
        } else {
            checkedItem = 1;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        int finalCheckedItem = checkedItem;
        builder.setTitle("Select a Language...")
                .setSingleChoiceItems(Language, checkedItem, (dialog, which) -> {
                    isSelectTemp[0] = true;
                    //if user select prefered language as English then
                    Log.e("Language", Language[which]);
                    Log.e("checkedItem", String.valueOf(finalCheckedItem));
                    if (Language[which].equals("ENGLISH")) {
                        setApplicationLocale("en", context);
                        preferencesHelper.setString(Config.LANGUAGE, Config.ENGLISH);
                    } else {
                        setApplicationLocale("hi", context);
                        preferencesHelper.setString(Config.LANGUAGE, Config.HINDI);
                    }

                })
                .setCancelable(false)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    dialog.dismiss();

                    if (tag.equals("splash")) {
                        if (new SharedPreferencesHelper(context).getBoolean(IS_LOGIN)) {
                            context.startActivity(new Intent(context, MainActivity.class));
                        } else {
                            context.startActivity(new Intent(context, OtpActivity.class));
                        }
                        activity.finish();
                    } else {
                        if (isSelectTemp[0]) {
                            context.startActivity(new Intent(context, MainActivity.class));
                            activity.finish();
                        }
                    }

                });
        builder.create().show();
    }

    public static void LanguyageSelected(Context context) {
        SharedPreferencesHelper preferencesHelper = new SharedPreferencesHelper(context);
        final String[] Language = {"ENGLISH", "हिन्दी"};
        int checkedItem = 0;
        if (preferencesHelper.getString(Config.LANGUAGE).equals("")) {
            checkedItem = 0;
        } else if (preferencesHelper.getString(Config.LANGUAGE).equals("ENGLISH")) {
            checkedItem = 0;
        } else {
            checkedItem = 1;
        }
        if (Language[checkedItem].equals("ENGLISH")) {
            setApplicationLocale("en", context);

        } else {
            setApplicationLocale("hi", context);

        }
    }

    private static void setApplicationLocale(String locale, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(locale.toLowerCase()));
        } else {
            config.locale = new Locale(locale.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }


    public static void ShowToast(String messgae, Context context) {
        Toast.makeText(context, messgae, Toast.LENGTH_SHORT).show();
    }


    public static boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 9 && phone.length() <= 13;
        }
        return false;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean checkInternet(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null) {
            new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.app_name))
                    .setMessage(context.getString(R.string.please_check_internet_connection))
                    .setPositiveButton("OK", null).show();
            return false;
        }
        return true;
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static String replacePath(String path) {
        path = path.replaceAll(" ", "_").toLowerCase();
        return path;
    }

    public static void HideActionBar(AppCompatActivity context) {
        if (context.getSupportActionBar() != null)
            context.getSupportActionBar().hide();

    }


    public static Dialog dialog;

    public static void Show_loader(Context context, boolean outside_touch, boolean cancleable) {

        if (dialog != null) {
            dialog.cancel();
        }
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_dialog_loading_view);
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.d_round_white_background));
        if (!outside_touch)
            dialog.setCanceledOnTouchOutside(false);

        if (!cancleable)
            dialog.setCancelable(false);

        dialog.show();
    }

    public static void cancel_loader() {
        if (dialog != null) {
            dialog.cancel();
        }
    }


    public static void ShowSucessDownload(Context context) {
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.successfully_downloaded_Image))
                .setConfirmText(context.getString(R.string.ok))
                .setConfirmClickListener(Dialog::dismiss)
                .show();
    }

    public static void SaveFile(View layout, Context context, String product_id, String tag) {

        Bitmap cache = getBitmapFromMV(layout);
//        String root = Environment.getExternalStorageDirectory().toString();
//        File Main_Dir = new File(root + "/" + getString(R.string.app_name) + "/" + getString(R.string.Downloaded));
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File Main_Dir = new File(path, "/" + context.getString(R.string.app_name) + "/" + context.getString(R.string.Downloaded));

        Main_Dir.mkdirs();
        try {
            String imageFileName = "/" + product_id + ".png";
            File imageFile = new File(Main_Dir, imageFileName);

            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            cache.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            galleryAddPic(imageFile.getAbsolutePath(), context, tag);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("dsjdsjdhjs", "hdh  " + e.getMessage());
            // TODO: handle exception
        } finally {
            layout.destroyDrawingCache();
        }
    }

    /*      if (imageFile.exists()) {
                    Toast.makeText(context, context.getString(R.string.Already_downloaded), Toast.LENGTH_SHORT).show();
                    return;
                }*/
    public static Bitmap getBitmapFromMV(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
//        Bitmap bitmapnew = getResizedBitmap(bitmap, 1500, 1500);
//        Bitmap bitmapnew = Bitmap.createScaledBitmap(bitmap, 1500, 1500, false);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

/*
    public static void ShowAlertForAddBusiness(Context context) {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.Business_Content))
                .setConfirmText(Config.OKAY)
                .setConfirmClickListener(sDialog -> {
                    // reuse previous dialog instance
                    sDialog.dismiss();
                    context.startActivity(new Intent(context, MyBusinessCategoryActivity.class).putExtra(FROM, CREATE).putExtra(Config.FROM, Config.REGULER));
                })
//                .setCancelText("No")
//                .setCancelClickListener(Dialog::dismiss)
                .show();
    }*/

    public static void galleryAddPic(String imagePath, Context context, String tag) {
        try {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(imagePath);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);

            if (tag.equals("Share")) {
                ShareFile(f, context);
            } else {
                ShowSucessDownload(context);
            }

        } catch (Exception e) {
            Toast.makeText(context, "Something Wants Wrong Try Again", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        /*notification();*/
    }

    public static void ShareFile(File file, Context context) {
        Log.d("dsjdsjdhjs", "dshdshdh" + file.getPath());
        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        intentShareFile.setType(URLConnection.guessContentTypeFromName(file.getName()));
        intentShareFile.putExtra(Intent.EXTRA_STREAM,
                Uri.parse(file.getPath()));
        context.startActivity(Intent.createChooser(intentShareFile, "Share File"));
        //if you need
        //intentShareFile.putExtra(Intent.EXTRA_SUBJECT,"Sharing File Subject);
        //intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File Description");


    }

    public static String GetUniqueNumber() {
        return UUID.randomUUID().toString();

    }

    public static void ShareApp(Context context) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);

    }

    public static void rateApp(Context context) {
        try {
            Uri uri = Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID);
            Intent intentrate = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intentrate);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
        }

    }

    public static void OpenMailIntentview(Context context, String emailId) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", context.getString(R.string.mail), null));
        if (emailId != null)
            emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailId, null));

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));

    }

    private static InterstitialAd mInterstitialAd;

    public static void adMobInterstitialAds(Activity activity) {
        if (new SharedPreferencesHelper(activity).getBoolean(Config.Buy_Plan)) {
            return;
        }

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(activity, activity.getResources().getString(R.string.interstitial_ads), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "admob-=-=->>>>" + "onAdLoaded");
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(activity);
                            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    // Called when fullscreen content is dismissed.
                                    Log.d("TAG", "admob-=-=->>>>" + "The ad was dismissed.");
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    // Called when fullscreen content failed to show.
                                    Log.d("TAG", "admob-=-=->>>>" + "The ad failed to show.");
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
//                                    Utils.hideProgress();
                                    // Called when fullscreen content is shown.
                                    // Make sure to set your reference to null so you don't
                                    // show it a second time.
                                    mInterstitialAd = null;
                                    Log.d("TAG", "admob-=-=->>>>" + "The ad was shown.");
//                                    imageDownloader(activity, link, false);
                                }
                            });
                        } else {
                            Log.d("TAG", "admob-=-=->>>>" + "The interstitial ad wasn't ready yet.");
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", "admob-=-=->>>>error>" + loadAdError.getMessage());
                        mInterstitialAd = null;
                        fbInterstitialAds(activity);
                    }
                });
    }

    public static void fbInterstitialAds(Activity activity) {
        AudienceNetworkAds.initialize(activity);
        com.facebook.ads.InterstitialAd interstitialAd = new com.facebook.ads.InterstitialAd(activity, activity.getResources().getString(R.string.facebook_interstitial_Ad));

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e("facebook->ad", "fb-=-=->>>>" + "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e("facebook->ad", "fb-=-=->>>>" + "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                // Ad error callback
                Log.e("facebook->ad", "fb-=-=->>>>" + "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d("facebook->ad", "fb-=-=->>>>" + "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d("facebook->ad", "fb-=-=->>>>" + "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d("facebook->ad", "fb-=-=->>>>" + "Interstitial ad impression logged!");
            }
        };

        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }
}
