package com.example.darren.new_design;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.ArrayList;
import java.util.List;

public class Fragment_exercise_intro extends Fragment implements YouTubePlayer.OnInitializedListener{

    Fragment myFragment;
    TextView Duration, exercise_date;
    Button start_exercise;
    ImageView btn_info_intro;
    int exercise = 0;

    //VideoView videoView;
    //int position = 0;
    //ProgressDialog progressDialog;
    //MediaController mediaControls;

    TextView exercise_name;
    com.bluejamesbond.text.DocumentView general_desc;

    List<Exercise_properties> exerc = new ArrayList<>();    // Holds a list of exercises
    List<Exercise_description> exdesc = new ArrayList<>();    // Holds a list of exercises descriptions
    Database_Manager db;
    Cursor cur;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View InputFragmentView = inflater.inflate(R.layout.exercise_intro, container, false);

        /*String ExName = "Side to side head rotation exercise, 6-8 feet away";
        String Description = "Place tablet on a shelf or ledge at roughly eye level. Stand 6-8 feet away. " +
                "The screen has an ‘E’ letter in the middle of the screen. Move your head from side to side " +
                "while focusing on the letter. If the letter starts to go out of focus then slow down. Continue " +
                "this exercise until timer has finished.";*/

        db = new Database_Manager(getActivity());
        db.open();

        cur = db.getExerciseDescriptions();
        cur.moveToPosition(0);
        exdesc.add(new Exercise_description("","",""));
        do{
        exdesc.add(new Exercise_description(cur.getString(1), cur.getString(2), cur.getString(3)));
        }while (cur.moveToNext());

        db.close();

        exerc.add(new Exercise_properties(1, 1, "Morning Exercise#1",   exdesc.get(1).getName(), exdesc.get(1).getDescription(), exdesc.get(1).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion1, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Morning Exercise#1",   exdesc.get(2).getName(), exdesc.get(2).getDescription(), exdesc.get(2).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion3, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Morning Exercise#2",   exdesc.get(1).getName(), exdesc.get(1).getDescription(), exdesc.get(1).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion1, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Morning Exercise#2",   exdesc.get(2).getName(), exdesc.get(2).getDescription(), exdesc.get(2).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion3, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Afternoon Exercise#1", exdesc.get(1).getName(), exdesc.get(1).getDescription(), exdesc.get(1).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion1, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Afternoon Exercise#1", exdesc.get(2).getName(), exdesc.get(2).getDescription(), exdesc.get(2).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion3, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Afternoon Exercise#2", exdesc.get(1).getName(), exdesc.get(1).getDescription(), exdesc.get(1).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion1, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Afternoon Exercise#2", exdesc.get(2).getName(), exdesc.get(2).getDescription(), exdesc.get(2).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion3, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Afternoon Exercise#2", exdesc.get(3).getName(), exdesc.get(3).getDescription(), exdesc.get(3).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion1, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Afternoon Exercise#2", exdesc.get(4).getName(), exdesc.get(4).getDescription(), exdesc.get(4).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion3, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Afternoon Exercise#2", exdesc.get(5).getName(), exdesc.get(5).getDescription(), exdesc.get(5).getIntro_Video(), new Exercise_Type2(), 60, R.drawable.illusion1, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Afternoon Exercise#2", exdesc.get(6).getName(), exdesc.get(6).getDescription(), exdesc.get(6).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion3, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Afternoon Exercise#2", exdesc.get(7).getName(), exdesc.get(7).getDescription(), exdesc.get(7).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion1, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Afternoon Exercise#2", exdesc.get(8).getName(), exdesc.get(8).getDescription(), exdesc.get(8).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion3, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Evening Exercise",     exdesc.get(1).getName(), exdesc.get(1).getDescription(), exdesc.get(1).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion1, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Evening Exercise",     exdesc.get(2).getName(), exdesc.get(2).getDescription(), exdesc.get(2).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion3, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Evening Exercise",     exdesc.get(9).getName(), exdesc.get(9).getDescription(), exdesc.get(9).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion1, 0.3f));
        exerc.add(new Exercise_properties(1, 1, "Evening Exercise",     exdesc.get(10).getName(), exdesc.get(10).getDescription(), exdesc.get(10).getIntro_Video(), new Exercise_Type1(), 60, R.drawable.illusion3, 0.3f));


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
                        "may make you dizzy but the dizziness should settle when you finish the exercise." +
                "\n");
                diaBox.show();
            }
        });
        //initialize the VideoView
        //videoView  = (VideoView) InputFragmentView.findViewById(R.id.videoView);

/*        YouTubePlayerSupportFragment mYoutubePlayerFragment = new YouTubePlayerSupportFragment();
        mYoutubePlayerFragment.initialize(DeveloperKey.DEVELOPER_KEY, this);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.youtube_Fragment, mYoutubePlayerFragment);
        fragmentTransaction.commit();*/

        //YouTubePlayerView youTubeView = (YouTubePlayerView) InputFragmentView.findViewById(R.id.youtube_Fragment);
        //youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);

        YouTubePlayerFragment youTubePlayerFragment = YouTubePlayerFragment.newInstance();
        youTubePlayerFragment.initialize(DeveloperKey.DEVELOPER_KEY, this);/*new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

                if (!wasRestored) {

                    player.setFullscreen(true);
                    player.loadVideo("pOcgcPUp1_g");
                    player.play();
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                // TODO Auto-generated method stub

            }
        });*/
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();

        exercise_name = (TextView) InputFragmentView.findViewById(R.id.exercise_name);
        exercise_name.setText(exerc.get(exercise).getName());

        general_desc = (com.bluejamesbond.text.DocumentView) InputFragmentView.findViewById(R.id.general_desc);
        general_desc.setText(exerc.get(exercise).getDescription());

        //mediaControls = new MediaController(getActivity());
        //mediaControls.setAnchorView(videoView);

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

        //try {
        //    //set the media controller in the VideoView
        //    videoView.setMediaController(mediaControls);
        //
        //    //set the uri of the video to be played
        //    videoView.setVideoURI(Uri.parse("rtsp://r7---sn-5hn7su7l.c.youtube.com/CiILENy73wIaGQn41yn1cCDnpBMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp"));//"https://www.youtube.com/watch?v=" + exerc.get(exercise).getIntro_Video()));//"http://youtu.be/s8XK3uziOxQ")); //"android.resource://" + getActivity().getPackageName() + "/" + exerc.get(exercise).getIntro_Video()));

        //} catch (Exception e) {
        //
        //    Log.e("Error", e.getMessage());
        //    e.printStackTrace();
        //}

        //videoView.requestFocus();
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        //videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

        //    public void onPrepared(MediaPlayer mediaPlayer) {
        //        // close the progress bar and play the video
        //        progressDialog.dismiss();
        //        //if we have a position on savedInstanceState, the video playback should start from here
        //        videoView.seekTo(position);

        //        if (position != 0) {
        //            //if we come from a resumed activity, video playback will be paused
        //            videoView.pause();
        //        }
        //    }
        //});

        //exercise_date = (TextView) InputFragmentView.findViewById(R.id.exercise_date);
        //String currentDateandTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", java.util.Locale.getDefault()).format(new Date());
        //exercise_date.setText(currentDateandTime);

        Duration = (TextView) InputFragmentView.findViewById(R.id.Duration);
        Duration.setText("Duration : " +exerc.get(exercise).getDuration());

        start_exercise = (Button) InputFragmentView.findViewById(R.id.start_exercise);
        start_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //videoView.pause();
                //videoView.clearFocus();
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

/*
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
*/

  @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if(!wasRestored){
            player.cueVideo(exerc.get(exercise).getIntro_Video());//"nCgQDjiotG0");
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        if (result.isUserRecoverableError()) {
            result.getErrorDialog(this.getActivity(), 1).show();
        } else {
            Toast.makeText(this.getActivity(),
                    "YouTubePlayer.onInitializationFailure(): " + result.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
    private AlertDialog CreateDialog( String message ){
        return new AlertDialog.Builder(getActivity())
                //set message, title, and icon
                .setTitle("Guide for Exercise")
                .setMessage(message)
                .create();
    }

}
