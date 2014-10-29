package com.example.darren.new_design;

import android.widget.ImageButton;
import android.app.ActionBar;
import android.view.View;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class frame extends FragmentActivity{

    ImageButton exercise_btn, revise_btn, sensor_btn, email_btn, home_btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        // Hides the action bar that would usual be at the top of the layout
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        home_btn = (ImageButton) findViewById(R.id.home_btn);
        exercise_btn = (ImageButton) findViewById(R.id.exercise_btn);
        revise_btn = (ImageButton) findViewById(R.id.revise_btn);
        sensor_btn = (ImageButton) findViewById(R.id.sensor_btn);
        email_btn = (ImageButton) findViewById(R.id.email_btn);

        home_btn.setOnClickListener(BtnOnClickListener);
        exercise_btn.setOnClickListener(BtnOnClickListener);
        revise_btn.setOnClickListener(BtnOnClickListener);
        sensor_btn.setOnClickListener(BtnOnClickListener);
        email_btn.setOnClickListener(BtnOnClickListener);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (savedInstanceState == null){
            home myFragment = new home();
            ft.add(R.id.content_layout, myFragment);
            ft.commit();
        }
    }

    ImageButton.OnClickListener BtnOnClickListener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment newFragment;
            enabletabs();
            if(v == home_btn){
                home_btn.setEnabled(false);
                newFragment = new home();
            }
            else if(v == exercise_btn){
                exercise_btn.setEnabled(false);
                newFragment = new exercise();
            }
            else if(v == revise_btn){
                revise_btn.setEnabled(false);
                newFragment = new revise();
            }
            else if(v == sensor_btn){
                sensor_btn.setEnabled(false);
                newFragment = new sensor();
            }
            else if(v == email_btn){
                email_btn.setEnabled(false);
                newFragment = new email();
            }
            else{

                home_btn.setEnabled(false);
                newFragment = new home();
            }

            toggleicons(home_btn);
            toggleicons(exercise_btn);
            toggleicons(revise_btn);
            toggleicons(sensor_btn);
            toggleicons(email_btn);

            FragmentManager fm1 = getSupportFragmentManager();
            FragmentTransaction ft1 = fm1.beginTransaction();
            //ft1.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            ft1.replace(R.id.content_layout, newFragment)
                    .addToBackStack(null)
                    .commit();

            //FragmentTransaction transaction = getFragmentManager().beginTransaction();

        }

    };

    void enabletabs(){
        home_btn.setEnabled(true);
        exercise_btn.setEnabled(true);
        revise_btn.setEnabled(true);
        sensor_btn.setEnabled(true);
        email_btn.setEnabled(true);
    }

    void toggleicons(ImageButton button)
    {
        if (button.isEnabled()){

            if(button.getId() == R.id.exercise_btn)
            {
                button.setBackground(getResources().getDrawable(R.drawable.icon_white_exercise));
            }
            else if(button.getId() == R.id.revise_btn)
            {
                button.setBackground(getResources().getDrawable(R.drawable.icon_white_progress));
            }
            else if(button.getId() == R.id.sensor_btn)
            {
                button.setBackground(getResources().getDrawable(R.drawable.icon_white_sensor));
            }
            else if(button.getId() == R.id.email_btn)
            {
                button.setBackground(getResources().getDrawable(R.drawable.icon_white_contact));
            }
            else
                button.setBackground(getResources().getDrawable(R.drawable.icon_white_house));
        }
        else{
            if(button.getId() == R.id.exercise_btn)
            {
                button.setBackground(getResources().getDrawable(R.drawable.icon_trans_exercise));
            }
            else if(button.getId() == R.id.revise_btn)
            {
                button.setBackground(getResources().getDrawable(R.drawable.icon_trans_progress));
            }
            else if(button.getId() == R.id.sensor_btn)
            {
                button.setBackground(getResources().getDrawable(R.drawable.icon_trans_sensor));
            }
            else if(button.getId() == R.id.email_btn)
            {
                button.setBackground(getResources().getDrawable(R.drawable.icon_trans_contact));
            }
            else
                button.setBackground(getResources().getDrawable(R.drawable.icon_trans_house));
        }
    }

}
