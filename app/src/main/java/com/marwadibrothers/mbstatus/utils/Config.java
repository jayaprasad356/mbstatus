package com.marwadibrothers.mbstatus.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

public class Config {
    public  static String IMG_PATH = "http://mbstatus.in/admin/";
    public  static String LANGUAGE = "language";
    public  static String ENGLISH = "ENGLISH";
    public  static String HINDI = "HINDI";
    public  static String ISFIRSTTIME = "ISFIRSTTIME";
    public  static String SELECTED_LANGUAGE = "SELECTED_LANGUAGE";
    public  static String SIGNUP = "SIGNUP";
    public  static String MOBILE = "MOBILE";
    public  static String UPDATE_PROFILE = "UPDATE_PROFILE";
    public  static String USER_ID = "USER_ID";
    public  static String isFirstTime = "isFirstTime";
    public  static String USER_NO = "USER_NO";
    public  static String FROM = "FROM";
    public  static String DATA = "DATA";
    public  static String GREETING = "GREETING";
    public  static String NORMAL = "NORMAL";
    public  static String ADD_BUSINESS = "ADD_BUSINESS";
    public  static String ADD_BUSINESS_REGULER = "ADD_BUSINESS_REGULER";
    public  static String EDIT_BUSINESS = "EDIT_BUSINESS";
    public  static String IS_LOGIN = "IS_LOGIN";
    public  static String Buy_Plan = "Buy_Plan";
    public  static String SUB_CAT_ID = "SUB_CAT_ID";
    public  static String SUB_CAT_NAME = "SUB_CAT_NAME";
    public  static String IMAGE_URL = "IMAGE_URL";
    public  static String PRODUCT_ID = "PRODUCT_ID";
    public  static String SUB_CAT_ID_1 = "SUB_CAT_ID_1";
    public  static String isPopUpOpen = "isPopUpOpen";

    public  static String Mobile_No = "";
    public  static String FORGROUND_IMAGE = "FORGROUND_IMAGE";
    public static String SELECT_ACCOUNT = "SELECT_ACCOUNT";
    public static String SELECT_ACCOUNT_ID = "SELECT_ACCOUNT_ID";
    public static String OTP = "";
    public static Boolean isProfileDone = false;
    public static Boolean isBusinessDone = false;
    public static Boolean isFb = false;
    public static Boolean isTwitter = false;
    public static Boolean isInsta = false;
    public static Boolean isLinkedIn = false;
    public static Boolean isWhatsapp = false;
    public static Boolean isTelegram = false;
    public static Boolean isYoutube = false;
    public static Boolean ifFromPopup = false;
    public static String isFbs = "isFbs";
    public static String isTwitters = "isTwitters";
    public static String isInstas = "isInstas";
    public static String isLinkedIns = "isLinkedIns";
    public static String isWhatsapps = "isWhatsapps";
    public static String isTelegrams = "isTelegrams";
    public static String isYoutubes = "isYoutubes";
    public static String lastDate = "lastDate";



    public static String getPath(Context context, Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null)
            return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public static String convertMediaUriToPath(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            String string = getPath(context,uri);
            return string;
        }
        else
            return getFilePathForN(uri, context);
    }

    private static String getFilePathForN(Uri uri, Context context) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(context.getFilesDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }
}
