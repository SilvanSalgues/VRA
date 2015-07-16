// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.rehabilitation.VRA.Messenger;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    Fragment_messenger fragment;

    public Adapter_SMS(Context context, int layoutResourceId, Fragment_messenger fragment) {
        super(context, layoutResourceId);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
        this.fragment = fragment;
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

            //if(user.equals(Notification_Handler.getHandle())){
            //
          //  if(!currentItem.getattachedId().isEmpty())
          //  {
                row = inflater.inflate(R.layout.row_sms_sent, parent, false);
           // }
//            else {
//                row = inflater.inflate(R.layout.row_sms_main, parent, false);
//
//                final EditText row_reply = (EditText) row.findViewById(R.id.row_reply);
//                Button row_send = (Button) row.findViewById(R.id.row_send);
//                row_send.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!row_reply.getText().toString().isEmpty())
//                        {
//                            Log.d("Row Text", "" + row_reply.getText());
//                            fragment.addItem(row_reply.getText().toString(), currentItem.getId());
//                            row_reply.setText("");
//                        }
//
//                    }
//                });
//            }
            //}
            //else
            //{
            //    row = inflater.inflate(R.layout.row_sms_received, parent, false);
            //}
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
