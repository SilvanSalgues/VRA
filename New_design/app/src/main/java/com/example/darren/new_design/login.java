package com.example.darren.new_design;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;


public class login extends Activity {

    FragmentManager fm = getFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    Fragment myFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        // Hides the action bar that would usual be at the top of the layout
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if (savedInstanceState == null){
            myFragment = new signin();
            ft.add(R.id.content_login, myFragment);
            ft.commit();
        }
    }
}
