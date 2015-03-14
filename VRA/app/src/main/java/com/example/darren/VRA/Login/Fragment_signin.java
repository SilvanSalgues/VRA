// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.example.darren.VRA.Login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darren.VRA.Main.Activity_container;
import com.example.darren.VRA.Database.Database_Manager;
import com.example.darren.VRA.R;

public class Fragment_signin extends Fragment {

    TextView in_signin;
    Button in_Login;
    FragmentManager fm;
    EditText in_name, in_pass;
    Database_Manager db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View InputFragmentView = inflater.inflate(R.layout.signin, container, false);

        db = new Database_Manager(getActivity());
        db.open();

        in_signin = (TextView) InputFragmentView.findViewById(R.id.in_signup);
        in_Login = (Button) InputFragmentView.findViewById(R.id.in_Login);
        in_name = (EditText) InputFragmentView.findViewById(R.id.in_name);
        in_pass = (EditText) InputFragmentView.findViewById(R.id.in_pass);

        fm = getFragmentManager();
        in_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left)
                        .replace(R.id.content_login, new Fragment_signup())
                        .addToBackStack(null)
                        .commit();
            }
        });

        in_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if( in_name.getText().toString().length() == 0 ) {
                    in_name.setError("USERNAME is required!");
                }

                else if ( in_pass.getText().toString().length() == 0 ){
                    in_pass.setError( "PASSWORD is required!" );
                }

                else{
                    // Checking if the username and password is correct - if so toast message and go to next activity
                    if (db.getUserLogin(in_name.getText().toString(), in_pass.getText().toString())) {

                        Toast.makeText(getActivity().getApplicationContext(), "Successful Login", Toast.LENGTH_SHORT).show();
                        db.LoginUSER(db.getUserID(in_name.getText().toString(), in_pass.getText().toString()));
                        db.close();

                        Intent intent = new Intent(getActivity(), Activity_container.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    // otherwise - toast sayin error !
                    else {
                        Toast.makeText(getActivity().getApplicationContext(), "No matching username and password found",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        in_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // if (count == 0)
               //     in_name.setError( "USERNAME is required!" );

               if( in_pass.getText().toString().length() == 0 )
                   in_pass.setError( "PASSWORD is required!" );
            }
        });

        in_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // if (count == 0)
                //     in_name.setError( "USERNAME is required!" );

                if( in_name.getText().toString().length() == 0 )
                    in_name.setError( "USERNAME is required!" );
            }
        });

        return InputFragmentView;
    }
}