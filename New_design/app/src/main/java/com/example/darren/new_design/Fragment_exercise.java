package com.example.darren.new_design;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

    List<Exercise_properties> exerc = new ArrayList<>();    // Holds a list of exercises
    static CountDownTimer countDownTimer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        InputFragmentView = inflater.inflate(R.layout.exercise, container, false);

        exerc.add(new Exercise_properties("Exercise Name 1",R.drawable.test, new Exercise_Type1(), 60, R.drawable.illusion1, 0.3f));
        exerc.add(new Exercise_properties("Exercise Name 2",R.drawable.test ,new Exercise_Type2(), 60, R.drawable.illusion3, 0.3f));


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
                    Pause_btn.setText("Continue Exercise");
                    countDownTimer.cancel();
                    TimerActive = false;
                }
                else{
                    Pause_btn.setText("Pause Exercise");
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

        exercise_name.setText(exerc.get(exercise).getName());                  // Sets the Exercise_No text box as the current exercise number
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



