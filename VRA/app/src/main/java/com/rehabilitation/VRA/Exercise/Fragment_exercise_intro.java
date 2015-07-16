// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.rehabilitation.VRA.Exercise;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rehabilitation.VRA.Database.Database_Manager;
import com.rehabilitation.VRA.Messenger.DeveloperKey;
import com.example.darren.VRA.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fragment_exercise_intro extends Fragment implements YouTubePlayer.OnInitializedListener{

    Fragment myFragment;
    TextView exercise_name, day, Duration;
    Button start_exercise;
    ImageView btn_info_intro;
    com.bluejamesbond.text.DocumentView general_desc;
    int cur_index, exercise_id;

    static List<Exercise_properties> exerc = new ArrayList<>();    // Holds a list of exercises
    static List<Exercise_description> exdesc = new ArrayList<>();    // Holds a list of exercises descriptions
    Database_Manager db;
    Cursor cur_desc, cur_list;

    View InputFragmentView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        InputFragmentView = inflater.inflate(R.layout.exercise_intro, container, false);


        /*String ExName = "Side to side head rotation cur_index, 6-8 feet away";
        String Description = "Place tablet on a shelf or ledge at roughly eye level. Stand 6-8 feet away. " +
                "The screen has an ‘E’ letter in the middle of the screen. Move your head from side to side " +
                "while focusing on the letter. If the letter starts to go out of focus then slow down. Continue " +
                "this cur_index until timer has finished.";*/

        db = new Database_Manager(getActivity());
        db.open();

        exercise_id = db.getExerciseId(db.isUserLoggedIn());

        /* Due to the _id in the sqlite starting at 1 and the cursor index starting at 0
        * the cur_index is one less than the exercise_id*/
        cur_index = exercise_id -1;
        Log.d("Exercise ID intro", "" + cur_index);

        // Get all Exercise Descriptions from database
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
            if (cur_list.getString(4).equals("Exercise_Type1")){
                Type = new Exercise_Type2();
            }
            else{
                Type = new Exercise_Type1();
            }
            exerc.add(new Exercise_properties(cur_list.getInt(0), cur_list.getInt(1), cur_list.getString(2), cur_list.getInt(3), Type, cur_list.getInt(5), cur_list.getInt(6), cur_list.getInt(7)));
        }while (cur_list.moveToNext());
        //-----------------------------------------------------------------------------------------

        db.close();

        Log.d("exerc size", "" + exerc.size());
        day = (TextView) InputFragmentView.findViewById(R.id.day);
        day.setText("Day " + exerc.get(cur_index).getDay() + ": " + exerc.get(cur_index).getTimeOfDay());
        btn_info_intro = (ImageView) InputFragmentView.findViewById(R.id.btn_info_intro);
        btn_info_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = CreateDialog("To improve balance, you have to challenge it. This" +
                        " means you will be unsteady when doing exercises in this application and " +
                        "there is a risk that you might fall. This risk must be minimised to ensure your safety." +
                        "\n       So this week when performing exercises, your therapist recommends:" +

                        "\n\n -Having someone beside you that is able to steady you." +
                        "\n -Having a chair(s)/ couch/ counter beside you to hold/lightly touch" +

                        "\n\nOnly do the exercises in the way your therapist has taught you. If at any time " +
                        "feel unsteady please make sure you have a firm support such as suggested above " +
                        "to help you regain your balance. The aim is to decrease your reliance on the support while remaining SAFE." +

                        "\n\nDuring exercises, don’t forget to blink. If you neck becomes sore use a heating pad or " +
                        "ice pack. If it is not better after three days call your physiotherapist. The exercises " +
                        "may make you dizzy but the dizziness should settle when you finish the cur_index." +
                        "\n");
                diaBox.show();
            }
        });

        YouTubePlayerFragment youTubePlayerFragment = YouTubePlayerFragment.newInstance();
        youTubePlayerFragment.initialize(DeveloperKey.DEVELOPER_KEY, this);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.youtube_fragment, youTubePlayerFragment).commit();

        exercise_name = (TextView) InputFragmentView.findViewById(R.id.exercise_name);
        exercise_name.setText(exdesc.get(exerc.get(cur_index).getexerciseNum()).getName());

        // Loads custom widget for Textview that allows for justification of text
        general_desc = (com.bluejamesbond.text.DocumentView) InputFragmentView.findViewById(R.id.general_desc);
        general_desc.setText(exdesc.get(exerc.get(cur_index).getexerciseNum()).getDescription());

        Duration = (TextView) InputFragmentView.findViewById(R.id.Duration);
        Duration.setText("Duration : " +exerc.get(cur_index).getDuration());

        start_exercise = (Button) InputFragmentView.findViewById(R.id.start_exercise);
        start_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.open();
                String currentDateandTime = new SimpleDateFormat("HH:mm dd/MM/yyyy", java.util.Locale.getDefault()).format(new Date());
                db.updateLastActive(db.isUserLoggedIn(), currentDateandTime);
                db.close();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                myFragment = new Fragment_exercise();
                ft.replace(R.id.content_layout, myFragment)
                        .addToBackStack(null);
                ft.commit();
            }
        });

        return InputFragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        exerc.clear();
        exdesc.clear();
        InputFragmentView = null; // now cleaning up!
    }

    // If the Youtube Player has been successfully created, load the video for the current cur_index you are on.
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        // If a video has not already been loaded into the player
        if(!wasRestored)
        {
            // The cueVideo method takes in the youtube video url identifier in a String format. Exercise 1 for example => pOcgcPUp1_g
            player.cueVideo(exdesc.get(exerc.get(cur_index).getexerciseNum()).getIntro_Video());
        }
    }

    // If the Youtube Player has failed to be created return error dialog and or Toast notification of the problem
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        if (result.isUserRecoverableError()) {
            result.getErrorDialog(this.getActivity(), 1).show();
        }
        else {
            Toast.makeText(this.getActivity(), "YouTubePlayer.onInitializationFailure(): " + result.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    // Dialog Creation method for the information button in the corner of the screen
    private AlertDialog CreateDialog( String message ){
        return new AlertDialog.Builder(getActivity())
                //set message, title
                .setTitle("Guide for Exercise")
                .setMessage(message)
                .create();
    }

}
