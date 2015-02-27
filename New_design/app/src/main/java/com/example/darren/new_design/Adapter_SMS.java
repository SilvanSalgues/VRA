package com.example.darren.new_design;


import android.content.Context;
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

        if (row == null) {
            row = inflater.inflate(R.layout.row_sms, parent, false);
        }

        row.setTag(currentItem);
        //final CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkToDoItem);
        TextView smsText = (TextView) row.findViewById(R.id.smsText);
        smsText.setText(currentItem.getText());
        //checkBox.setChecked(false);
        //checkBox.setEnabled(true);


//        checkBox.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                if (checkBox.isChecked()) {
//                    checkBox.setEnabled(false);
//                    if (mContext instanceof Activity_container) {
//                        Activity_container activity = (Activity_container) mContext;
//                        FragmentManager fm = activity.getFragmentManager();
//
//                        Fragment_messenger fragment = (Fragment_messenger)fm.findFragmentById(R.id.content_layout);
//                        fragment.checkItem(currentItem);
//
//                    }
//                }
//            }
//        });
        return row;
    }
}
