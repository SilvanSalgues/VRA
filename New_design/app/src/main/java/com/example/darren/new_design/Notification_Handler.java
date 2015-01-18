package com.example.darren.new_design;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class Notification_Handler extends com.microsoft.windowsazure.notifications.NotificationsHandler {
    public static final int NOTIFICATION_ID = 1;
    NotificationManager mNotificationManager;
    private Context ctx;

    @Override
    public void onReceive(Context context, Bundle bundle) {
        ctx = context;
        String nhMessage = bundle.getString("message");

        sendNotification(nhMessage);
    }

    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        Activity_container activity = (Activity_container) ctx;
        FragmentManager fm = activity.getFragmentManager();
        Fragment_messenger fragment = (Fragment_messenger)fm.findFragmentById(R.id.content_layout);
        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                new Intent(ctx, fragment.getClass()), 0);

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
}
