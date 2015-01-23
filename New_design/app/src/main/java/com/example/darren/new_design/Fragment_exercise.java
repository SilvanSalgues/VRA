package com.example.darren.new_design;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Fragment_exercise extends Fragment {

    Handler handler;
    Runnable counter;
    long timer_lenght = 0;
    int totalseconds = 0;
    int exercise = 0;

    Exercise_Timer mycounter;
    TextView timer;
    TextView exercise_no;

    Button Pause_btn, Stop_btn;
    Boolean TimerActive = false;

    Fragment ExerciseFragment;

    // Holds a list of exercises
    List<Exercise_properties> exerc = new ArrayList<>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View InputFragmentView = inflater.inflate(R.layout.exercise, container, false);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (savedInstanceState == null){
            ExerciseFragment = new Exercise_Type2();
            ft.replace(R.id.exercise_container, ExerciseFragment);
            ft.commit();
        }

        if (ExerciseFragment instanceof Exercise_Type1) {
            Exercise_Type1 fragment = (Exercise_Type1) ExerciseFragment.getParentFragment();// (Exercise_Type1) fm.findFragmentById(R.id.exercise_container);
            // fragment.setColour("Blue");

            Context context = getActivity().getApplicationContext();
            CharSequence text = "Exercise_Type1";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        if (ExerciseFragment instanceof Exercise_Type2) {
            Exercise_Type2 fragment = (Exercise_Type2) fm.findFragmentById(R.id.exercise_container);
            //fragment.setColour("Red");

            Context context = getActivity().getApplicationContext();
            CharSequence text = "Exercise_Type2";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }


        // A layout used as a container to hold the exercise in the middle of the screen
        // eg could be holding a single focus point or multiple moving focus points depending on the exercise
        //exercise_container = (RelativeLayout) InputFragmentView.findViewById(R.id.exercise_container);

        // Selected exercise to be placed in the exercise_container
        //exerciseType = getActivity().getLayoutInflater().inflate(R.layout.exercise_type_2, exercise_container, false);
        //exercise_container.addView(exerciseType);

        timer = (TextView)  InputFragmentView.findViewById(R.id.timer_txt);
        exercise_no = (TextView) InputFragmentView.findViewById(R.id.exercise_no);
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
        Stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment myFragment = new Fragment_exercise_intro();
                ft.add(R.id.content_layout, myFragment);
                ft.commit();

            }
        });

        exerc.add(new Exercise_properties(10, "White", 0.3f));
        mycounter = new Exercise_Timer();
        settingup_exercise();   //Sets up the individual properties of the exercise and starts the Timer

        return InputFragmentView;
    }

    //runs without a timer by reposting this handler at the end of the runnable
    public void RefreshTimerText()
    {
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

    private void settingup_exercise() {
        exercise_no.setText("Exercise Name - 'Number " + (exercise + 1) + "'");          // Sets the Exercise_No text box as the current exercise number
        //Speed.setText("Speed : " + exerc.get(exercise).speed + "Hz");   // Sets the Speed text box as the speed of head movement of the exercise
        timer_lenght = exerc.get(exercise).getDuration() * 1000;                       // Gets the time of the exercis
        mycounter.setDuration(timer_lenght);
        mycounter.Start();
        RefreshTimerText();
    }
}

