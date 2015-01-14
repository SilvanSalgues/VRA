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

    public Adapter_Contact(Context context, ArrayList<Type_Contact> d){
        data = d;
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

        View v = convertView;
        v = inflater.inflate(R.layout.row_contact, null);

        TextView contact_name = (TextView)v.findViewById(R.id.contact_name);
        TextView phone_number = (TextView)v.findViewById(R.id.new_messages);

        final Type_Contact Contact = data.get(position);
        contact_name.setText(Contact.getName());
        phone_number.setText(Contact.getPhone());
        return v;
    }
}