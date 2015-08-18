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

/**
 * Adapter to bind a Type_SMS List to a view
 */
public class Adapter_MainPost extends ArrayAdapter<Type_SMS> {

    /**
     * Adapter context
     */
    Context mContext;

    /**
     * Adapter View layout
     */
    int mLayoutResourceId;

    public Adapter_MainPost(Context context, int layoutResourceId) {
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

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();

                row = inflater.inflate(R.layout.row_maintopic, parent, false);
        }
        row.setTag(currentItem);

        // Places the message content
        TextView smsText = (TextView) row.findViewById(R.id.smsText);
        smsText.setText(currentItem.getText());

        // Places the current user name with the massage
        TextView username = (TextView) row.findViewById(R.id.username);
        username.setText(currentItem.getUsername());

        // Places the time since the message has been posted
        TextView createdAt = (TextView) row.findViewById(R.id.createdAt);
        if(currentItem.getCreatedAt() == null) {
            createdAt.setText("Sending..");
        }
        else {
            createdAt.setText(currentItem.getTimeSince());
        }

        return row;
    }
}
