package com.marwadibrothers.mbstatus.firebase;

import static com.marwadibrothers.mbstatus.utils.Config.NORMAL;
import static com.marwadibrothers.mbstatus.utils.Config.SUB_CAT_ID;
import static com.marwadibrothers.mbstatus.utils.Config.SUB_CAT_NAME;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.activity.MainActivity;
import com.marwadibrothers.mbstatus.activity.SubCategoryActivity;
import com.marwadibrothers.mbstatus.activity.editing.PlanPopupActivity;
import com.marwadibrothers.mbstatus.utils.Config;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String debugTag = "MyFirebaseApp";
    private int notificationID = 100;
    private int numMessages = 0;
    String title, message, image;

    private NotificationManager mNotificationManager;

    String subCateID,subCateName,type;
    Intent resultIntent;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(debugTag, "Notification Received!");

        if (remoteMessage.getData().size() > 0) {
            title = remoteMessage.getData().get("title");
            message = remoteMessage.getData().get("body");
            image = remoteMessage.getData().get("icon");
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            message = remoteMessage.getNotification().getBody();
            image = remoteMessage.getData().get("image");
        }

        Log.d("jjkjk","hjhjhjhj"+remoteMessage.getData().get("sub_category_id"));
        subCateID=remoteMessage.getData().get("sub_category_id");
        subCateName=remoteMessage.getData().get("sub_category_name");
        type=remoteMessage.getData().get("type");
        displayNotification(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), image);

        //Calling method to generate notification
        //sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

    }

    //This method is only generating push notification
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"channel01")
                .setSmallIcon(R.drawable.mb_logo)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setColor(getColor(R.color.notification))
                .setColorized(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)   // heads-up
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void displayNotification(Context context, String PUSH_TITLE, String PUSH_MSG, String PUSH_IMAGE) {
        Log.i("Start", "notification");

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        /* Invoking the default notification service */
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,getString(R.string.app_name));
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setContentTitle(PUSH_TITLE);
        mBuilder.setContentText(PUSH_MSG);
        mBuilder.setTicker("Message from " + getResources().getString(R.string.app_name));
        mBuilder.setSmallIcon(R.drawable.mb_logo);
        mBuilder.setLargeIcon(icon);
        mBuilder.setSound(defaultSoundUri);
        mBuilder.setColor(getColor(R.color.notification));
        mBuilder.setColorized(true);
        mBuilder.setStyle(new androidx.media.app.NotificationCompat.DecoratedMediaCustomViewStyle());
        mBuilder.setChannelId(getResources().getString(R.string.default_notification_channel_id));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        //mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);   // heads-up

        /* Increase notification number every time a new notification arrives */
        mBuilder.setNumber(++numMessages);
        System.out.println("Push image:" + PUSH_IMAGE);
        NotificationCompat.BigTextStyle notiStyle = new
                NotificationCompat.BigTextStyle();
        notiStyle.setBigContentTitle(PUSH_TITLE);
        notiStyle.bigText(PUSH_MSG);
        mBuilder.setStyle(notiStyle);
        /* Creates an explicit intent for an Activity in your app */
        if(Integer.parseInt(subCateID)>0){

            resultIntent = new Intent(context, SubCategoryActivity.class);
            resultIntent.putExtra(SUB_CAT_ID,subCateID);
            resultIntent.putExtra(SUB_CAT_NAME,subCateName);
            resultIntent.putExtra(Config.FROM, NORMAL);


        }/*else if(type.equals("subscription")){
            resultIntent = new Intent(context, PlanPopupActivity.class);
        }*/
        else {
             resultIntent = new Intent(context, MainActivity.class);
        }
        //resultIntent = new Intent(context, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);

        /* Adds the Intent that starts the Activity to the top of the stack */
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Sets an ID for the notification, so it can be updated.
            String CHANNEL_ID = getResources().getString(R.string.default_notification_channel_id);// The id of the channel.
            CharSequence name = getString(R.string.app_name);// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setSound(null, null);
            mChannel.setLightColor(R.color.notification);
            mChannel.setShowBadge(true);
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        /* notificationID allows you to update the notification later on. */
        mNotificationManager.notify(notificationID, mBuilder.build());
    }

}
