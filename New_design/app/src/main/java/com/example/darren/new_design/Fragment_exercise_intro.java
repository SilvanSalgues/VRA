package com.example.darren.new_design;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

public class Fragment_exercise_intro extends Fragment {

    Fragment myFragment;
    TextView Duration, exercise_date;
    Button start_exercise;
    int exercise = 0;

    VideoView videoView;
    int position = 0;
    ProgressDialog progressDialog;
    MediaController mediaControls;

    List<Exercise_properties> exerc = new ArrayList<>();    // Holds a list of exercises

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View InputFragmentView = inflater.inflate(R.layout.exercise_intro, container, false);

        exerc.add(new Exercise_properties("Exercise Name 1",R.drawable.test, new Exercise_Type1(), 60, R.drawable.illusion1, 0.3f));
        exerc.add(new Exercise_properties("Exercise Name 2",R.drawable.test, new Exercise_Type2(), 60, R.drawable.illusion3, 0.3f));


        //initialize the VideoView
        videoView  = (VideoView) InputFragmentView.findViewById(R.id.videoView);

        mediaControls = new MediaController(getActivity());
        mediaControls.setAnchorView(videoView);

        // create a progress bar while the video file is loading
        //progressDialog = new ProgressDialog(getActivity());
        // set a title for the progress bar
        //progressDialog.setTitle("Android Video View Example");
        // set a message for the progress bar
        //progressDialog.setMessage("Loading...");
        //set the progress bar not cancelable on users' touch
        //progressDialog.setCancelable(false);
        // show the progress bar
        //progressDialog.show();

        try {
            //set the media controller in the VideoView
            videoView.setMediaController(mediaControls);

            //set the uri of the video to be played

            videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + exerc.get(exercise).getIntro_Video()));

        } catch (Exception e) {

            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoView.requestFocus();
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                // close the progress bar and play the video
                //progressDialog.dismiss();
                //if we have a position on savedInstanceState, the video playback should start from here
                videoView.seekTo(position);

                if (position != 0) {
                    //if we come from a resumed activity, video playback will be paused
                    videoView.pause();
                }
            }
        });

        //exercise_date = (TextView) InputFragmentView.findViewById(R.id.exercise_date);
        //String currentDateandTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", java.util.Locale.getDefault()).format(new Date());
        //exercise_date.setText(currentDateandTime);

        Duration = (TextView) InputFragmentView.findViewById(R.id.Duration);
        Duration.setText("Duration : " +exerc.get(exercise).getDuration());

        start_exercise = (Button) InputFragmentView.findViewById(R.id.start_exercise);
        start_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();
                videoView.clearFocus();
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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //we use onSaveInstanceState in order to store the video playback position for orientation change
        savedInstanceState.putInt("Position", videoView.getCurrentPosition());
        videoView.pause();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //we use onRestoreInstanceState in order to play the video playback from the stored position
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("Position");
            videoView.seekTo(position);
        }
    }
}
