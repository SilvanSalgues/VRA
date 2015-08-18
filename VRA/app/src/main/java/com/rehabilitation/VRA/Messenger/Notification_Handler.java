// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.rehabilitation.VRA.Messenger;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.darren.VRA.R;
import com.microsoft.windowsazure.notifications.NotificationsHandler;

public class Notification_Handler extends NotificationsHandler {

    public static final int NOTIFICATION_ID = 1;
    NotificationManager mNotificationManager;
    Context ctx;

    @com.google.gson.annotations.SerializedName("handle")
    private static String mHandle;


    public static String getHandle() {
        return mHandle;
    }

    public static void setHandle(String handle) {
        mHandle = handle;
    }

    @Override
    public void onRegistered(Context context,  final String gcmRegistrationId) {
        super.onRegistered(context, gcmRegistrationId);

        setHandle(gcmRegistrationId);
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... params) {
                try {
                    Azure.AzureClient.getPush().register(gcmRegistrationId, null);
                    return null;
                }
                catch(Exception e) {
                    // handle error
                }
                return null;
            }
        }.execute();
    }

    @Override
    public void onReceive(Context context, Bundle bundle) {
        ctx = context;
        String nhMessage = bundle.getString("message");

        sendNotification(nhMessage);
    }

    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                new Intent(ctx, Fragment_messenger.class), 0);

        Log.d("Notification", "" + msg);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.drawable.icon_notification)
                        .setContentTitle("New Board Post")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}

