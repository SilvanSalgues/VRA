package com.rehabilitation.VRA.Exercise;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rehabilitation.VRA.Database.Database_Manager;
import com.example.darren.VRA.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_exercise extends Fragment {

    int cur_index, exercise_id, duration;

    public View InputFragmentView;
    public long remainingTime;
    public TextView timer;
    Boolean TimerActive = true;

    TextView exercise_name;
    Button Pause_btn, Stop_btn;
    Fragment ExerciseFragment;

    List<Integer> pausetimes = new ArrayList<>();

    static List<Exercise_properties> exerc = new ArrayList<>();    // Holds a list of exercises
    static List<Exercise_description> exdesc = new ArrayList<>();    // Holds a list of exercises descriptions
    Database_Manager db;
    Cursor cur_desc, cur_list;

    static CountDownTimer countDownTimer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        InputFragmentView = inflater.inflate(R.layout.exercise, container, false);

        exdesc.clear();
        exerc.clear();

        db = new Database_Manager(getActivity());
        db.open();

        exercise_id = db.getExerciseId(db.isUserLoggedIn());
        cur_index = exercise_id -1;
        Log.d("exercise screen","" + cur_index);

        //Get all Exercise Descriptions from database
        //-----------------------------------------------------------------------------------------
        cur_desc = db.getExerciseDescriptions();
        cur_desc.moveToFirst();
        do{
            exdesc.add(new Exercise_description(cur_desc.getString(0), cur_desc.getString(1), cur_desc.getString(2)));
        }while (cur_desc.moveToNext());
        //-----------------------------------------------------------------------------------------

        // Get the list of all exercises from database
        //-----------------------------------------------------------------------------------------
        cur_list = db.getCompleteExerciseList();
        cur_list.moveToFirst();
        do{
            Exercise_Type Type;
            if (cur_list.getString(4).equals("Exercise_Type2")){
                Type = new Exercise_Type2();
            }
            else{
                Type = new Exercise_Type1();
            }

            exerc.add(new Exercise_properties(cur_list.getInt(0), cur_list.getInt(1), cur_list.getString(2), cur_list.getInt(3), Type, cur_list.getInt(5), cur_list.getInt(6), cur_list.getInt(7)));
        }while (cur_list.moveToNext());
        //-----------------------------------------------------------------------------------------

        Log.d("Number of Exercises", "" + exerc.size());
        db.close();


        timer = (TextView)  InputFragmentView.findViewById(R.id.timer_txt);
        timer .setText(String.valueOf(remainingTime / 1000));

        remainingTime = exerc.get(cur_index).getDuration() * 1000;   // Gets the time of the cur_index
        countDownTimer = new MyCountDownTimer();
        countDownTimer.start();

        exercise_name = (TextView) InputFragmentView.findViewById(R.id.exercise_name);

        Pause_btn = (Button) InputFragmentView.findViewById(R.id.Pause_btn);
        Pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimerActive) {
                    Pause_btn.setText("CONTINUE");

                    db.open();
                    db.updatePausedCount(exerc.get(cur_index).getDay(), exerc.get(cur_index).getWeek(), exerc.get(cur_index).getTimeOfDay(), exerc.get(cur_index).getexerciseNum());
                    db.close();

                    pausetimes.add(duration);
                    countDownTimer.cancel();
                    TimerActive = false;
                }
                else{
                    Pause_btn.setText("PAUSE");
                    countDownTimer = new MyCountDownTimer();
                    countDownTimer.start();
                    TimerActive = true;
                }
            }
        });

        Stop_btn = (Button) InputFragmentView.findViewById(R.id.Stop_btn);
        Stop_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                db.open();

                db.stoppedEx(exerc.get(cur_index).getDay(), exerc.get(cur_index).getWeek(), exerc.get(cur_index).getTimeOfDay(), exerc.get(cur_index).getexerciseNum(), duration);

                for (int pausetime : pausetimes ) {
                    db.insertPauseTime(db.isUserLoggedIn(), exerc.get(cur_index).getDay(), exerc.get(cur_index).getWeek(), exerc.get(cur_index).getTimeOfDay(), exerc.get(cur_index).getexerciseNum(), pausetime);
                    Log.d("Paused Times", "" + pausetime);
                }
                pausetimes.clear();

                db.close();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment myFragment = new Fragment_exercise_intro();
                ft.replace(R.id.content_layout, myFragment)
                        .addToBackStack(null);
                ft.commit();
            }
        });


        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ExerciseFragment = exerc.get(cur_index).getType().newInstance(exerc.get(cur_index).getGifposition());
        ft.replace(R.id.exercise_container, ExerciseFragment)
                .addToBackStack(null);
        ft.commit();


        exercise_name.setText("Exercise | " + exdesc.get(exerc.get(cur_index).getexerciseNum()).getName());  // Sets the Exercise_No text box as the current cur_index number
        //Speed.setText("Speed : " + exerc.get(cur_index).speed + "Hz");        // Sets the Speed text box as the speed of head movement of the cur_index

        return InputFragmentView;
    }

    public class MyCountDownTimer extends CountDownTimer {

        long minutes;
        long seconds;
        Fragment myFragment;


        public MyCountDownTimer() {
            super(remainingTime, 1000);     // 1000 is an interval of one second
        }

        @Override
        public void onFinish() {
            timer.setText("Time's up!");

            db.open();
            db.completeEx(exerc.get(cur_index).getDay(), exerc.get(cur_index).getWeek(), exerc.get(cur_index).getTimeOfDay(), exerc.get(cur_index).getexerciseNum(), duration);

            for (int pausetime : pausetimes ) {
                db.insertPauseTime(db.isUserLoggedIn(), exerc.get(cur_index).getDay(), exerc.get(cur_index).getWeek(), exerc.get(cur_index).getTimeOfDay(), exerc.get(cur_index).getexerciseNum(), pausetime);
                Log.d("Paused Times", "" + pausetime);
            }
            pausetimes.clear();

            //Log.d("cur_index", "" + cur_index);
            if (cur_index < exerc.size()) {
                db.IncrementExerciseCount(db.isUserLoggedIn(), exercise_id);
            }
            else{
                db.updateExerciseCount(db.isUserLoggedIn(), 0);
            }
            db.close();

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            myFragment = new Fragment_dizziness().newInstance(exercise_id);
            ft.replace(R.id.content_layout, myFragment)
                    .addToBackStack(null);
            ft.commit();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            remainingTime = millisUntilFinished;                    // Gets the remaining time left on the timer in seconds
            minutes = (remainingTime /1000) / 60;                   // Breaks the number of seconds down into minutes
            seconds = (remainingTime /1000) % 60;                   // Gets the remaining seconds excluding the minutes
            timer.setText(String.format("Remaining %d:%02d", minutes, seconds));  // Updates the timer text box

            duration = (int)(exerc.get(cur_index).getDuration() - (remainingTime /1000));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        InputFragmentView = null;   // now cleaning up!
        countDownTimer.cancel();
        countDownTimer = null;
        ExerciseFragment.onDestroyView();

        Log.d("Fragment Exercise", "DestroyView Called");
    }
}



