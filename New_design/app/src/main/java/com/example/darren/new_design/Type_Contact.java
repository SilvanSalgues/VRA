package com.example.darren.new_design;

public class Type_Contact {
    private String contactName;
    private String contactPhone;

    public Type_Contact(String st1, String st2){
        contactName = st1;
        contactPhone = st2;
    }

    public String getName(){
        return contactName;
    }

    public String getPhone(){
        return contactPhone;
    }

    public void setName(String st1){
        contactName = st1;
    }

    public void setPhone(String st2){
        contactPhone = st2;
    }
}