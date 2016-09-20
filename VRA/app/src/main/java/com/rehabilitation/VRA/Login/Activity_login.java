// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.rehabilitation.VRA.Login;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rehabilitation.VRA.Main.Activity_container;
import com.rehabilitation.VRA.Database.Database_Manager;
import com.example.darren.VRA.R;


public class Activity_login extends AppCompatActivity {

    Fragment myFragment;
    Database_Manager db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        db = new Database_Manager(this);
        db.open();
        if(db.isUserLoggedIn() != -1){
            Intent intent = new Intent(this, Activity_container.class);
            startActivity(intent);
            finish();
        }
        db.close();

        // Hides the action bar that would usual be at the top of the layout
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if (savedInstanceState == null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            myFragment = new Fragment_signin();
            ft.add(R.id.content_login, myFragment);
            ft.commit();
        }
    }
}
