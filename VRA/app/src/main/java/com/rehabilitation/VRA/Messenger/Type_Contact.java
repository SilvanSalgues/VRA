// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.rehabilitation.VRA.Messenger;

public class Type_Contact {
    String contact_name;
    String NumberOfSMS;
    int icon;

    public Type_Contact(String contact_name, String NumberOfSMS, int icon){
        this.contact_name = contact_name;
        this.NumberOfSMS = NumberOfSMS;
        this.icon = icon;
    }

    public String getName(){return contact_name;}
    public String getNumberOfSMS(){
        return NumberOfSMS;
    }
    public int getIcon(){return icon;}
    public void setName(String contact_name){
        this.contact_name = contact_name;
    }
}