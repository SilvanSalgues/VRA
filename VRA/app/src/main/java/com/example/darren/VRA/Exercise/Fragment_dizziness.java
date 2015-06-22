package com.example.darren.VRA.Exercise;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.darren.VRA.Database.Database_Manager;
import com.example.darren.VRA.R;

import java.util.Calendar;

/**
 * Created by Darren
 */
public class Fragment_dizziness extends Fragment {


    public Fragment_dizziness(){
        super();
    }

    public Fragment_dizziness newInstance(int exercise_id) {
        Fragment_dizziness fragment = new Fragment_dizziness();
        Bundle bundle = new Bundle(1);
        bundle.putInt("exercise_id", exercise_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    View InputFragmentView;
    Button submit;
    TextView dizziness_date, value;
    SeekBar dizziness_seekBar;
    Fragment myFragment;
    Database_Manager db;
    int exercise_id;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        InputFragmentView = inflater.inflate(R.layout.dizziness, container, false);
        db = new Database_Manager(getActivity());

        exercise_id = getArguments().getInt("exercise_id");
        Log.d("exercise_id", "" + exercise_id);

        value = (TextView) InputFragmentView.findViewById(R.id.value);
        dizziness_date = (TextView) InputFragmentView.findViewById(R.id.dizziness_date);
        dizziness_seekBar = (SeekBar) InputFragmentView.findViewById(R.id.dizziness_seekBar);
        submit = (Button) InputFragmentView.findViewById(R.id.dizziness_submit);
        submit.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                db.open();
                db.updateDizziness(exercise_id, dizziness_seekBar.getProgress());
                db.close();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                myFragment = new Fragment_exercise_intro();
                ft.replace(R.id.content_layout, myFragment)
                        .addToBackStack(null);
                ft.commit();
            }
        });

        Calendar rightNow = Calendar.getInstance();
        dizziness_date.setText(rightNow.get(Calendar.DAY_OF_MONTH) + "/" + (rightNow.get(Calendar.MONTH)+1) + "/" + rightNow.get(Calendar.YEAR));

        dizziness_seekBar.setMax(10);
        dizziness_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged (SeekBar seekBar,int progress, boolean fromUser) {
            value.setText("Dizziness : " + progress);
            }

            @Override
            public void onStartTrackingTouch (SeekBar seekBar){
            }

            @Override
            public void onStopTrackingTouch (SeekBar seekBar){
            }
        });
        return InputFragmentView;
    }
}
