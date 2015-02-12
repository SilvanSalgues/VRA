package com.example.darren.new_design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class Adapter_Contact extends BaseAdapter{

    private LayoutInflater inflater;
    private ArrayList<Type_Contact> data;
    Context c;
    int p;

    public Adapter_Contact(Context context, ArrayList<Type_Contact> d){
        data = d;
        c = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        p = position;

        if (row == null) {
            row = inflater.inflate(R.layout.row_contact, null);
        }

        TextView contact_name = (TextView)row.findViewById(R.id.contact_name);
        TextView phone_number = (TextView)row.findViewById(R.id.new_messages);

        final Type_Contact Contact = data.get(position);
        contact_name.setText(Contact.getName());
        phone_number.setText(Contact.getPhone());


        row.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Perform an action when this list item is clicked
                //Toast.makeText(c, p, Toast.LENGTH_SHORT).show();
            }
        });
        return row;
    }
}