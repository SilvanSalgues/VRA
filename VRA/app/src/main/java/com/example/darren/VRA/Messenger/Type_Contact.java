// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.example.darren.VRA.Messenger;

public class Type_Contact {
    private String contactName;
    private String NumberOfSMS;

    public Type_Contact(String st1, String st2){
        contactName = st1;
        NumberOfSMS = st2;
    }

    public String getName(){
        return contactName;
    }

    public String getNumberOfSMS(){
        return NumberOfSMS;
    }

    public void setName(String st1){
        contactName = st1;
    }

    public void setNumberOfSMS(String st2){
        NumberOfSMS = st2;
    }
}