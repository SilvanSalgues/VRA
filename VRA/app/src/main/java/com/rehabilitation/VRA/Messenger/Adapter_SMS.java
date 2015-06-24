// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.rehabilitation.VRA.Messenger;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.darren.VRA.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Adapter to bind a Type_SMS List to a view
 */
public class Adapter_SMS extends ArrayAdapter<Type_SMS> {

    /**
     * Adapter context
     */
    Context mContext;

    /**
     * Adapter View layout
     */
    int mLayoutResourceId;

    public Adapter_SMS(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }

    /**
     * Returns the view for a specific SMS on the list
     */
    @Override
    public View getView(int position, View row, ViewGroup parent)
    {
        final Type_SMS currentItem = getItem(position);

        String user = "" + currentItem.getUser();

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();

            if(user.equals(Notification_Handler.getHandle())){
                row = inflater.inflate(R.layout.row_sms_sent, parent, false);
            }
            else
            {
                row = inflater.inflate(R.layout.row_sms_received, parent, false);
            }
        }
        row.setTag(currentItem);
        TextView smsText = (TextView) row.findViewById(R.id.smsText);
        smsText.setText(currentItem.getText());

        TextView createdAt = (TextView) row.findViewById(R.id.createdAt);


        if(currentItem.getCreatedAt() == null)
        {
            createdAt.setText("Sending..");
        }
        else {

            SimpleDateFormat sm = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.UK);
            Date date = new Date();
            long diff = date.getTime() - currentItem.getCreatedAt().getTime();
            //System.out.println(sm.format(diff));

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000);
            long diffDays = diff / (60 * 60 * 1000 * 24);

            if (diffDays != 0) {
                if (diffDays == 1) {
                    createdAt.setText(diffDays + " Day ago");
                } else
                    createdAt.setText(diffDays + " Days ago");
            } else if (diffHours != 0) {
                if (diffHours == 1) {
                    createdAt.setText(diffHours + " Hour ago");
                } else
                    createdAt.setText(diffHours + " Hours ago");
            } else if (diffMinutes != 0) {
                if (diffMinutes == 1) {
                    createdAt.setText(diffMinutes + " Minute ago");
                } else
                    createdAt.setText(diffMinutes + " Minutes ago");
            } else{
                createdAt.setText(diffSeconds + " Seconds ago");
            }

        }

        if(!user.equals(Notification_Handler.getHandle())) {
            smsText.setTextColor(mContext.getResources().getColor(R.color.Light_Blue));
        }
        else{
            smsText.setTextColor(mContext.getResources().getColor(R.color.Black));
        }

        return row;
    }
}
