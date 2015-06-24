// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.rehabilitation.VRA.Main;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.rehabilitation.VRA.Database.Database_Manager;
import com.rehabilitation.VRA.Exercise.Fragment_exercise_intro;
import com.rehabilitation.VRA.Home.Fragment_home;
import com.rehabilitation.VRA.Messenger.Fragment_messenger;
import com.rehabilitation.VRA.Login.Activity_login;
import com.example.darren.VRA.R;
import com.rehabilitation.VRA.Revise.Fragment_revise;
import com.rehabilitation.VRA.Sensor.Fragment_sensor;

public class Activity_container extends Activity {

    ImageButton exercise_btn, revise_btn, sensor_btn,messenger_btn, home_btn, logout_btn;
    Fragment newFragment;
    Database_Manager db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        db = new Database_Manager(this);
        // Hides the action bar that would usual be at the top of the layout
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        home_btn = (ImageButton) findViewById(R.id.home_btn);
        exercise_btn = (ImageButton) findViewById(R.id.exercise_btn);
        revise_btn = (ImageButton) findViewById(R.id.revise_btn);
        sensor_btn = (ImageButton) findViewById(R.id.sensor_btn);
        messenger_btn = (ImageButton) findViewById(R.id.messenger_btn);
        logout_btn = (ImageButton) findViewById(R.id.logout_btn);

        home_btn.setOnClickListener(BtnOnClickListener);
        exercise_btn.setOnClickListener(BtnOnClickListener);
        revise_btn.setOnClickListener(BtnOnClickListener);
        sensor_btn.setOnClickListener(BtnOnClickListener);
        messenger_btn.setOnClickListener(BtnOnClickListener);
        logout_btn.setOnClickListener(BtnOnClickListener);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (savedInstanceState == null){
            newFragment = new Fragment_home();
            ft.replace(R.id.content_layout, newFragment);
            ft.commit();
        }
    }

    public void load_exercise_intro(){
        enabletabs();
        exercise_btn.setEnabled(false);
        newFragment = new Fragment_exercise_intro();

        toggleicons(home_btn);
        toggleicons(exercise_btn);
        toggleicons(revise_btn);
        toggleicons(sensor_btn);
        toggleicons(messenger_btn);


        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction ft1 = fm1.beginTransaction();
        ft1.replace(R.id.content_layout, newFragment)
                .addToBackStack(null);
        ft1.commit();
    }

    ImageButton.OnClickListener BtnOnClickListener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            enabletabs();
            if(v == home_btn){
                home_btn.setEnabled(false);
                newFragment = new Fragment_home();
            }
            else if(v == exercise_btn){
                exercise_btn.setEnabled(false);
                newFragment = new Fragment_exercise_intro();
            }
            else if(v == revise_btn){
                revise_btn.setEnabled(false);
                newFragment = new Fragment_revise();
            }
            else if(v == sensor_btn){
                sensor_btn.setEnabled(false);
                newFragment = new Fragment_sensor();
            }
            else if(v == messenger_btn){
                messenger_btn.setEnabled(false);
                newFragment = new Fragment_messenger();
            }
            else if(v == logout_btn){
                db.open();
                if(db.isUserLoggedIn() != -1){
                    db.LogoutUSER(db.isUserLoggedIn());
                }
                db.close();
                Intent intent = new Intent(getApplicationContext(), Activity_login.class);
                startActivity(intent);
                finish();
            }
            else{
                home_btn.setEnabled(false);
                newFragment = new Fragment_home();
            }

            toggleicons(home_btn);
            toggleicons(exercise_btn);
            toggleicons(revise_btn);
            toggleicons(sensor_btn);
            toggleicons(messenger_btn);


            FragmentManager fm1 = getFragmentManager();
            FragmentTransaction ft1 = fm1.beginTransaction();
            ft1.replace(R.id.content_layout, newFragment)
                    .addToBackStack(null);
            ft1.commit();
        }

    };

    void enabletabs(){
        home_btn.setEnabled(true);
        exercise_btn.setEnabled(true);
        revise_btn.setEnabled(true);
        sensor_btn.setEnabled(true);
        messenger_btn.setEnabled(true);
    }

    void toggleicons(ImageButton button)
    {
        if (button.isEnabled()){

            if(button.getId() == R.id.exercise_btn)
            {
                button.setBackground(getResources().getDrawable(R.drawable.icon_exercise_unselected));
            }
            else if(button.getId() == R.id.revise_btn)
            {
                button.setBackground(getResources().getDrawable(R.drawable.icon_progress_unselected));
            }
            else if(button.getId() == R.id.sensor_btn)
            {
                button.setBackground(getResources().getDrawable(R.drawable.icon_sensor_unselected));
            }
            else if(button.getId() == R.id.messenger_btn)
            {
                button.setBackground(getResources().getDrawable(R.drawable.icon_messenger_unselected));
            }
            else
                button.setBackground(getResources().getDrawable(R.drawable.icon_home_unselected));
        }
        else{
            if(button.getId() == R.id.exercise_btn)
            {
                button.setBackground(getResources().getDrawable(R.drawable.icon_exercise_selected));
            }
            else if(button.getId() == R.id.revise_btn)
            {
                button.setBackground(getResources().getDrawable(R.drawable.icon_progress_selected));
            }
            else if(button.getId() == R.id.sensor_btn)
            {
                button.setBackground(getResources().getDrawable(R.drawable.icon_sensor_selected));
            }
            else if(button.getId() == R.id.messenger_btn)
            {
                button.setBackground(getResources().getDrawable(R.drawable.icon_messenger_selected));
            }
            else
                button.setBackground(getResources().getDrawable(R.drawable.icon_home_selected));
        }
    }

}
