package com.example.darren.new_design;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Fragment_exercise extends Fragment {

    Handler handler;
    Runnable counter;
    long timer_lenght = 0;
    int totalseconds = 0;
    int exercise = 1;

    Exercise_Timer mycounter;
    TextView timer, exercise_name;

    Button Pause_btn, Stop_btn;
    Boolean TimerActive = false;
    Fragment ExerciseFragment;

    List<Exercise_properties> exerc = new ArrayList<>();    // Holds a list of exercises

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View InputFragmentView = inflater.inflate(R.layout.exercise, container, false);

        exerc.add(new Exercise_properties("Exercise Name 1",new Exercise_Type1(), 60, Color.CYAN, 0.3f));
        exerc.add(new Exercise_properties("Exercise Name 2",new Exercise_Type2(), 60, Color.argb(255,100,100,100), 0.3f));


        timer = (TextView)  InputFragmentView.findViewById(R.id.timer_txt);
        exercise_name = (TextView) InputFragmentView.findViewById(R.id.exercise_name);

        Pause_btn = (Button) InputFragmentView.findViewById(R.id.Pause_btn);
        Pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimerActive) {

                    mycounter.Stop();
                    Pause_btn.setText("Continue Exercise");
                    TimerActive = false;
                }
                else{
                    mycounter.Start();
                    Pause_btn.setText("Pause Exercise");
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
                ft.add(R.id.content_layout, myFragment);
                ft.commit();
            }
        });

        mycounter = new Exercise_Timer();

        settingup_exercise();   //Sets up the individual properties of the exercise and starts the Timer
        return InputFragmentView;
    }

    //runs without a timer by reposting this handler at the end of the runnable
    public void RefreshTimerText() {
        TimerActive = true;
        handler = new Handler();
        counter = new Runnable(){
            public void run(){
                totalseconds = mycounter.getCurrentTime();                  //Gets the remaining time left on the timer in seconds
                int minutes = totalseconds / 60;                            // Breaks the number of seconds down into minutes
                int seconds = totalseconds % 60;                            // Gets the remaining seconds excluding the minutes
                timer.setText(String.format("Remaining %d:%02d", minutes, seconds));        // Updates the timer text box
                handler.postDelayed(this, 100);

                //if ((mycounter.getCurrentTime() == 0) && (exercise < 4))
                //{
                //    exercise++;
                //    settingup_exercise();
                //}
                if (mycounter.getCurrentTime() == 0)
                {
                    //changeactivity();
                    mycounter.setDuration(timer_lenght);
                }
            }
        };
        handler.postDelayed(counter, 100);
    }

    private void settingup_exercise(){

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Bundle parameters = new Bundle();
        int bgColour = exerc.get(exercise).getBackground();
        parameters.putInt("bgColour", bgColour);

        Log.i("Fragment_exercise", "exercise type " + exerc.get(exercise).getType());
        ExerciseFragment = exerc.get(exercise).getType();
        ExerciseFragment.setArguments(parameters);
        ft.replace(R.id.exercise_container, ExerciseFragment);
        ft.commit();

        exercise_name.setText(exerc.get(exercise).getName());         // Sets the Exercise_No text box as the current exercise number
        //Speed.setText("Speed : " + exerc.get(exercise).speed + "Hz");                 // Sets the Speed text box as the speed of head movement of the exercise
        timer_lenght = exerc.get(exercise).getDuration() * 1000;                        // Gets the time of the exercise
        mycounter.setDuration(timer_lenght);
        mycounter.Start();
        RefreshTimerText();
    }
}

