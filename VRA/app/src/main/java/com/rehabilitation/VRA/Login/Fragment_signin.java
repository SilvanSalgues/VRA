// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.rehabilitation.VRA.Login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rehabilitation.VRA.Database.Database_Manager;
import com.rehabilitation.VRA.Main.Activity_container;
import com.example.darren.VRA.R;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Fragment_signin extends Fragment {

    Button in_signin, in_Login, forgottenPass;
    FragmentManager fm;
    EditText in_email, in_pass;
    Database_Manager db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View InputFragmentView = inflater.inflate(R.layout.signin, container, false);

        db = new Database_Manager(getActivity());
        db.open();

        in_signin = (Button) InputFragmentView.findViewById(R.id.in_signup);
        in_Login = (Button) InputFragmentView.findViewById(R.id.in_Login);
        in_email = (EditText) InputFragmentView.findViewById(R.id.in_email);
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

        forgottenPass = (Button) InputFragmentView.findViewById(R.id.forgottenPass);
        forgottenPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (in_email.getText().toString().length() == 0) {
                    in_email.setError("EMAIL is required!");
                } else {

                    Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                //TODO (jos || tariq) check with Darren if this is actually working.
                                sendEmail("darrenmcn@gmail.com", "darrenmcn@gmail.com", "VRA Forgotten Password", "Pass");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();

                }
            }
        });


        in_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if( in_email.getText().toString().length() == 0 ) {
                    in_email.setError("EMAIL is required!");
                }

                else if ( in_pass.getText().toString().length() == 0 ){
                    in_pass.setError( "PASSWORD is required!" );
                }

                else{
                    // Checking if the username and password is correct - if so toast message and go to next activity
                    if (db.getUserLogin(in_email.getText().toString(), in_pass.getText().toString())) {

                        Toast.makeText(getActivity().getApplicationContext(), "Successful Login", Toast.LENGTH_SHORT).show();
                        db.LoginUSER(db.getUserID(in_email.getText().toString(), in_pass.getText().toString()));
                        db.close();

                        Intent intent = new Intent(getActivity(), Activity_container.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    // otherwise - toast saying error !
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
                if (in_pass.getText().toString().length() == 0)
                    in_pass.setError("PASSWORD is required!");
            }
        });

        in_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // if (count == 0)
                //     in_email.setError( "USERNAME is required!" );

                if (in_email.getText().toString().length() == 0)
                    in_email.setError("USERNAME is required!");
            }
        });

        return InputFragmentView;
    }

    private void sendEmail(String recipientAddress, String recipientName, String subject, String message) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("my-admin-account@gmail.com", "PicSoup Admin"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientAddress, recipientName));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
            Log.d("Email ","Sent email to "+recipientAddress);
        } catch (Exception e) {
            Log.d("Failed to send email", "" + e);
        }
    }

}