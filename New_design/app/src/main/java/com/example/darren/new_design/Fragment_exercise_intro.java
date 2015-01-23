package com.example.darren.new_design;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment_exercise_intro extends Fragment {

    Button start_exercise;
    Fragment myFragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View InputFragmentView = inflater.inflate(R.layout.exercise_intro, container, false);


        start_exercise = (Button) InputFragmentView.findViewById(R.id.start_exercise);
        start_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                myFragment = new Fragment_exercise();
                ft.add(R.id.content_layout, myFragment);
                ft.commit();

            }
        });
        return InputFragmentView;
    }
}
