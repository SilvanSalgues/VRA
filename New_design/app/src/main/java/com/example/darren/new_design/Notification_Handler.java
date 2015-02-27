package com.example.darren.new_design;

import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class Notification_Handler extends com.microsoft.windowsazure.notifications.NotificationsHandler {
        public static final int NOTIFICATION_ID = 1;
        private NotificationManager mNotificationManager;
        NotificationCompat.Builder builder;
        private Context ctx;

        @com.google.gson.annotations.SerializedName("handle")
        private static String mHandle;


    public static String getHandle() {
        return mHandle;
    }

    public static final void setHandle(String handle) {
        mHandle = handle;
    }

    @Override
    public void onReceive(Context context, Bundle bundle) {
        ctx = context;
        String Message = bundle.getString("message");

        Log.d("onReceive",Message);
        sendNotification(Message);
    }

    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

       // Activity_container activity = (Activity_container) ctx;
       // FragmentManager fm = activity.getFragmentManager();
       // Fragment_messenger fragment = (Fragment_messenger)fm.findFragmentById(R.id.content_layout);


        Activity_container activity = (Activity_container) ctx;

        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                //new Intent(ctx, fragment.getClass()), 0);
                new Intent(ctx, activity.getClass()), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Notification Hub Demo")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }


@Override
    public void onRegistered(Context context,  final String gcmRegistrationId) {
        super.onRegistered(context, gcmRegistrationId);
        setHandle(gcmRegistrationId);
        new AsyncTask<Void, Void, Void>() {

            protected Void doInBackground(Void... params) {
                try {
                    Activity_container activity = (Activity_container) ctx;
                    FragmentManager fm = activity.getFragmentManager();
                    Fragment_messenger fragment = (Fragment_messenger)fm.findFragmentById(R.id.content_layout);
                    fragment.AzureClient.getPush().register(gcmRegistrationId, null);
                    return null;
                }
                catch(Exception e) {
                    // handle error
                }
                return null;
            }
        }.execute();
    }
}
