package com.axpresslogistics.it2.axpresslogisticapp.MyFirebaseMessagingService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.axpresslogistics.it2.axpresslogisticapp.R;

import com.axpresslogistics.it2.axpresslogisticapp.activities.activities_module.NewToDo;
import com.axpresslogistics.it2.axpresslogisticapp.activities.activities_module.ToDoList;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "TAG";
    Intent intent;
    NewToDo scheduleAlarmFunction = new NewToDo();


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.e("RemoteData ", "Message data payload" + remoteMessage.getData());
        }
        if (!remoteMessage.getNotification().equals("") || remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            String method = remoteMessage.getNotification().getTag();

//            method = "new_reminder";

            Log.e(TAG, "Title :" + title);
            Log.e(TAG, "Body :" + body);
            Log.e(TAG, "Method :" + method);
            showNotification(title, body, method);
        }
    }

    @Override
    public void onDeletedMessages() {
    }

    public void showNotification(String title, String body, String method) {
        if (method.equals("update")) {
            intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.axpresslogistics.it2.axpresslogisticapp"));
        }
        else if (method.equals("reminder")) {
            AsyncTaskRunner runner = new AsyncTaskRunner(this);
            runner.execute(title,body);
            intent = new Intent(this, ToDoList.class);
            Log.e("BODY",body);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);
        Uri uriSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this);
        notificationCompat.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{100, 200, 300, 400, 500})
                .setSound(uriSound);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationCompat.build());
    }

}
