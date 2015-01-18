package com.example.darren.new_design;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

/**
 * Adapter to bind a ToDoItem List to a view
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
     * Returns the view for a specific item on the list
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final Type_SMS currentItem = getItem(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
        }

        row.setTag(currentItem);
        final CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkToDoItem);
        checkBox.setText(currentItem.getText());
        checkBox.setChecked(false);
        checkBox.setEnabled(true);

        checkBox.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (checkBox.isChecked()) {
					checkBox.setEnabled(false);
                    if (mContext instanceof Activity_container) {
                        Activity_container activity = (Activity_container) mContext;
                        FragmentManager fm = activity.getFragmentManager();

                        Fragment_messenger fragment = (Fragment_messenger)fm.findFragmentById(R.id.content_layout);
                        fragment.checkItem(currentItem);

                    }
				}
			}
		});
		return row;
	}

}
