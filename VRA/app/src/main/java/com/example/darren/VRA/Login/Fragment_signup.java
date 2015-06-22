package com.example.darren.VRA.Login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.darren.VRA.Database.Database_Manager;
import com.example.darren.VRA.Main.Activity_container;
import com.example.darren.VRA.R;

public class Fragment_signup extends Fragment {

    Button up_signup, up_create;
    EditText up_name, up_email, up_pass, up_verifypass;
    FragmentManager fm;
    Database_Manager db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View InputFragmentView = inflater.inflate(R.layout.signup, container, false);

        db = new Database_Manager(getActivity());
        up_signup = (Button) InputFragmentView.findViewById(R.id.up_signin);
        up_create = (Button) InputFragmentView.findViewById(R.id.up_create);
        up_name = (EditText) InputFragmentView.findViewById(R.id.up_name);
        up_email = (EditText) InputFragmentView.findViewById(R.id.up_email);
        up_pass = (EditText) InputFragmentView.findViewById(R.id.up_pass);
        up_verifypass = (EditText) InputFragmentView.findViewById(R.id.up_verify_pass);

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

                String pass = up_pass.getText().toString();
                String verify = up_verifypass.getText().toString();

                if (pass.equals(verify)) {
                    db.open();                // Open Database
                    db.insertUser("" + up_name.getText(), "" + up_email.getText(), "" + up_pass.getText());


                    db.Exercise_Result_Setup(db.SQLiteDatabase, db.isUserLoggedIn());

                    db.close();                // Close Database

                    Toast.makeText(getActivity().getApplicationContext(), "Successfully added user", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), Activity_container.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Passwords Failed to Match",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        return InputFragmentView;
    }
}