package com.example.darren.new_design;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment_signup extends Fragment {

    TextView up_signup;
    Button up_create;
    FragmentManager fm;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View InputFragmentView = inflater.inflate(R.layout.signup, container, false);

        up_signup = (TextView) InputFragmentView.findViewById(R.id.up_signin);
        up_create = (Button) InputFragmentView.findViewById(R.id.up_create);

        fm = getFragmentManager();
        up_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right)
                        .replace(R.id.content_login, new Fragment_signin())
                        .addToBackStack(null)
                        .commit();
            }
        });

        up_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent
                        (getActivity(), Activity_container.class);
                startActivity(intent);
            }
        });
        return InputFragmentView;
    }
}