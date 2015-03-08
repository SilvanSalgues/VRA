package com.example.darren.new_design;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Adapter to bind a Type_SMS List to a view
 */
public class Adapter_SMS extends ArrayAdapter<Type_SMS> {

    private LayoutInflater inflater;
    /**
     * Adapter context
     */
    Context mContext;

    public Adapter_SMS(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        mContext = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Returns the view for a specific SMS on the list
     */
    @Override
    public View getView(int position, View row, ViewGroup parent)
    {
        final Type_SMS currentItem = getItem(position);

        String user = "" + currentItem.getUser();

        if(user.equals(Notification_Handler.getHandle())){
            row = inflater.inflate(R.layout.row_sms_sent, parent, false);
        }
        else
        {
            row = inflater.inflate(R.layout.row_sms_received, parent, false);
        }

        row.setTag(currentItem);
        TextView smsText = (TextView) row.findViewById(R.id.smsText);
        smsText.setText(currentItem.getText());

        if(user.equals(Notification_Handler.getHandle()))
        {
            smsText.setText("S: " + currentItem.getText());
        }
        else
        {
            smsText.setText("R: " + currentItem.getText());
            smsText.setTextColor(Color.parseColor("#ff8ae3f6"));
        }
        return row;
    }
}
