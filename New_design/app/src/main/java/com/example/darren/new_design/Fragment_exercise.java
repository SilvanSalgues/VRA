package com.example.darren.new_design;

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

import java.util.ArrayList;
import java.util.List;


public class Fragment_exercise extends Fragment {

    int exercise = 0;

    public View InputFragmentView;
    public long remainingTime;
    public TextView timer;
    Boolean TimerActive = true;

    TextView exercise_name;
    Button Pause_btn, Stop_btn;
    Fragment ExerciseFragment;

    Database_Manager db;
    Cursor cur;

    List<Exercise_properties> exerc = new ArrayList<>();    // Holds a list of exercises
    static CountDownTimer countDownTimer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        InputFragmentView = inflater.inflate(R.layout.exercise, container, false);
        db = new Database_Manager(getActivity());
        db.open();

        cur = db.getExerciseDescriptions();
        cur.moveToPosition(4);//exercise);
        Log.d("Cursor","" + cur.getString(1));

        String ExName = cur.getString(1);
        String Description = cur.getString(2);
        db.close();


        /*String ExName = "Side to side head rotation exercise, 6-8 feet away";
        String Description = "Place tablet on a shelf or ledge at roughly eye level. Stand 6-8 feet away. " +
                "The screen has an ‘E’ letter in the middle of the screen. Move your head from side to side " +
                "while focusing on the letter. If the letter starts to go out of focus then slow down. Continue " +
                "this exercise until timer has finished.";*/

        exerc.add(new Exercise_properties(ExName,Description,R.drawable.test, new Exercise_Type1(), 60, R.drawable.illusion1, 0.3f));
        exerc.add(new Exercise_properties(ExName,Description,R.drawable.test, new Exercise_Type2(), 60, R.drawable.illusion3, 0.3f));


        timer = (TextView)  InputFragmentView.findViewById(R.id.timer_txt);
        timer .setText(String.valueOf(remainingTime / 1000));


        remainingTime = exerc.get(exercise).getDuration() * 1000;                        // Gets the time of the exercise
        countDownTimer = new MyCountDownTimer();
        countDownTimer.start();

        exercise_name = (TextView) InputFragmentView.findViewById(R.id.exercise_name);

        Pause_btn = (Button) InputFragmentView.findViewById(R.id.Pause_btn);
        Pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimerActive) {
                    Pause_btn.setText("CONTINUE");
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

        Bundle parameters = new Bundle();
        int bgGIF = exerc.get(exercise).getBackground();
        parameters.putInt("bgGIF", bgGIF);

        Log.i("Fragment_exercise", "exercise type " + exerc.get(exercise).getType());
        ExerciseFragment = exerc.get(exercise).getType();
        ExerciseFragment.setArguments(parameters);
        ft.replace(R.id.exercise_container, ExerciseFragment)
                .addToBackStack(null);
        ft.commit();

        exercise_name.setText("Exercise | " + exerc.get(exercise).getName());  // Sets the Exercise_No text box as the current exercise number
        //Speed.setText("Speed : " + exerc.get(exercise).speed + "Hz");        // Sets the Speed text box as the speed of head movement of the exercise

        return InputFragmentView;
    }

    public class MyCountDownTimer extends CountDownTimer {

        long minutes;
        long seconds;

        public MyCountDownTimer() {
            super(remainingTime, 1000);     // 1000 is an interval of one second
        }

        @Override
        public void onFinish() {
            timer.setText("Time's up!");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            remainingTime = millisUntilFinished;             //Gets the remaining time left on the timer in seconds
            minutes = (remainingTime /1000) / 60;       // Breaks the number of seconds down into minutes
            seconds = (remainingTime /1000) % 60;       // Gets the remaining seconds excluding the minutes
            timer.setText(String.format("Remaining %d:%02d", minutes, seconds));  // Updates the timer text box
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        InputFragmentView = null; // now cleaning up!
        countDownTimer.cancel();
        countDownTimer = null;
 //       ExerciseFragment.onDestroyView();

        Log.d("Fragment Exercise", "DestroyView Called");
    }
}



