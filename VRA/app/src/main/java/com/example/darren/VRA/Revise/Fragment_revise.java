package com.example.darren.VRA.Revise;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.darren.VRA.Database.Database_Manager;
import com.example.darren.VRA.Main.Activity_container;
import com.example.darren.VRA.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_revise extends Fragment {

    ExpandableListDataPump expump;
    ExpandableListView expandableListView;
    ArrayList<ExpandableListAdapter> expandableListAdapter;
    List<String> expandableListTitle;
    ArrayList<ArrayList<ExerciseList>> listAllExercises;

    LinearLayout Layout;
    View [] inflated = new View[7];
    TextView dayNo, WeekNo;

    RadioGroup radioGroup;
    RadioButton radioButton;

    ImageView btn_info_intro;
    Database_Manager db;
    AlertDialog diaBox;
    int ExerciseNum, ExerciseId;

    // Dialog Widgets
    TextView exerc_day_title, numberofexercises, title, description, paused, pausedAt, status, duration, dizziness;
    ImageView tick;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View InputFragmentView = inflater.inflate(R.layout.revise, container, false);
        db = new Database_Manager(getActivity());

        WeekNo = (TextView) InputFragmentView.findViewById(R.id.WeekNo);

        radioButton = (RadioButton) InputFragmentView.findViewById(R.id.radioWeek1);
        radioButton.toggle();

        // Open Extra Information Dialog when info button in the corner is selected
        btn_info_intro = (ImageView) InputFragmentView.findViewById(R.id.btn_info_intro);
        btn_info_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaBox = CreateDialog("Extra Information");
                diaBox.show();
            }
        });

        // Get the Linear Layout in which to dynamically load each day of exercises
        Layout =(LinearLayout) InputFragmentView.findViewById(R.id.linear_exercise);

        // The activity where the data for exercises of each day are pulled from the database
        // and arranged into the appropriate expandable list
        expump = new ExpandableListDataPump(getActivity());

        // Stores a list of every exercise split into a list for each day
        // SO a WHOLE day of exercises is added to this list
        listAllExercises = new ArrayList<>();

        // This is a list of expandable list adapters
        // The reason for this is that each day many have a different number of exercises that require on click listeners
        expandableListAdapter = new ArrayList<>();

        loadExercises(1);

        // The Radio Group Changes the Week to view the exercise lists
        radioGroup = (RadioGroup) InputFragmentView.findViewById(R.id.radioWeeks);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Layout.removeAllViews();
                listAllExercises.clear();
                expandableListAdapter.clear();

                // Find which radio button is selected
                if(checkedId == R.id.radioWeek1) {
                    WeekNo.setText("Week 1 Exercises");
                    loadExercises(1);
                }
                else if(checkedId == R.id.radioWeek2) {
                    WeekNo.setText("Week 2 Exercises");
                    loadExercises(2);
                }
                else if(checkedId == R.id.radioWeek3) {
                    WeekNo.setText("Week 3 Exercises");
                    loadExercises(3);
                }
                else if(checkedId == R.id.radioWeek4) {
                    WeekNo.setText("Week 4 Exercises");
                    loadExercises(4);
                }
                else if(checkedId == R.id.radioWeek5) {
                    WeekNo.setText("Week 5 Exercises");
                    loadExercises(5);
                }
                else if(checkedId == R.id.radioWeek6) {
                    WeekNo.setText("Week 6 Exercises");
                    loadExercises(6);
                }
                else {
                    Log.d("Error", "No data for Week");
                }
            }
        });

        return InputFragmentView;
    }


    private void loadExercises(int week)
    {
        // This for loop currently loads up 7 days
        for(int i = 0; i<7; i++) {

            // Holds the list of different times of the day exercises are set for . eg Morning Exercise#1
            expandableListTitle = new ArrayList<>();
            expandableListTitle.clear();

            // Adding a day of exercises from the expandableListDataPump class. Takes a parameter of the day of the week
            listAllExercises.add(expump.getData(week, i + 1));


            if (listAllExercises.get(i) != null){
                for (int pos = 0; pos < listAllExercises.get(i).size(); pos++) {
                    expandableListTitle.add(listAllExercises.get(i).get(pos).getExTitle());
                }
            }


            inflated[i] = getActivity().getLayoutInflater().inflate(R.layout.exercise_day, null);

            dayNo = (TextView)inflated[i].findViewById(R.id.day);
            dayNo.setText("Day " + (i + 1));
            expandableListView = (ExpandableListView) inflated[i].findViewById(R.id.expandableListView);
            Layout.addView(inflated[i]);

            expandableListAdapter.add(new ExpandableListAdapter(getActivity(), expandableListTitle, listAllExercises.get(i)));

            expandableListView.setAdapter(expandableListAdapter.get(i));


         /*   expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    Log.d("Revise", "Day " + (day + 1) + ":" + listAllExercises.get(day).get(groupPosition).getExTitle() + " List Expanded.");
                }
            });

            expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                @Override
                public void onGroupCollapse(int groupPosition) {
                    Log.d("Revise", "Day " + (day + 1) + ":" + expandableListTitle.get(groupPosition) + " List Collapsed.");

                }
            });
*/
            final int day = i;
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                    open_Dialog(day, groupPosition, childPosition);
                    return false;
                }
            });

        }
    }

    private void open_Dialog(int day, int groupPos, int childPos) {
        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.exercise_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        int week = 1;
        /*Log.d("Revise, Child List", "Day " + (day + 1) + ", Week " + week + ", Title " +
                expandableListTitle.get(groupPos) + ", ChildPosition" + childPos +
                "" + listAllExercises.get(day).get(groupPos).getExList().get(childPos));*/

        db.open();

        ExerciseNum = db.getExerciseNum(day + 1, week, expandableListTitle.get(groupPos), childPos);
        //Log.d("ExerciseNum","" + ExerciseNum);

        ExerciseId = db.getExerciseId(day + 1, week, expandableListTitle.get(groupPos), ExerciseNum);
        Log.d("Exercise ID Revise", "" + ExerciseId);

        int PauseCount = db.getPausedCount(db.isUserLoggedIn(), day + 1, week, expandableListTitle.get(groupPos), childPos);
        List<Integer> pausedTimes = db.getPausedTimes(db.isUserLoggedIn(), day + 1, week, expandableListTitle.get(groupPos), childPos);
        int statusNo = listAllExercises.get(day).get(groupPos).getExComplete().get(childPos);

        exerc_day_title = (TextView) dialog.findViewById(R.id.exerc_day_title);
        numberofexercises = (TextView) dialog.findViewById(R.id.numberofexercises);
        title = (TextView) dialog.findViewById(R.id.title);
        description = (TextView) dialog.findViewById(R.id.description);
        paused = (TextView) dialog.findViewById(R.id.paused);
        pausedAt = (TextView) dialog.findViewById(R.id.pausedAt);
        status = (TextView) dialog.findViewById(R.id.status);
        duration = (TextView) dialog.findViewById(R.id.duration);
        dizziness = (TextView) dialog.findViewById(R.id.dizziness);
        tick = (ImageView) dialog.findViewById(R.id.tick);


        Button load_exercise = (Button) dialog.findViewById(R.id.load_exercise);
        setOnClick(load_exercise, ExerciseId, dialog);

        exerc_day_title.setText("Day " + (day + 1) + " " + expandableListTitle.get(groupPos));
        numberofexercises.setText("Exericse " + (childPos + 1) + " of " + listAllExercises.get(day).get(groupPos).getExList().size());
        title.setText("" + listAllExercises.get(day).get(groupPos).getExList().get(childPos));
        description.setText("" + db.getExerciseDescription(ExerciseNum));
        duration.setText("Duration : " + db.getDuration(db.isUserLoggedIn(), day + 1, week, expandableListTitle.get(groupPos), childPos) + " seconds");
        dizziness.setText("Dizziness : " + db.getDizziness(db.isUserLoggedIn(), day + 1, week, expandableListTitle.get(groupPos), childPos) + " out of 10");


        if (statusNo == 1) {
            tick.setBackgroundResource(R.drawable.icon_green_tick);
            status.setText("Status : Completed");
        } else if (statusNo == 2) {
            tick.setBackgroundResource(R.drawable.icon_red_x);
            status.setText("Status : Missed");
        } else {
            tick.setBackgroundResource(R.drawable.icon_yellow_blank);
            status.setText("Status : To be completed");
        }


        paused.setText("No. of Times Paused : " + PauseCount);

        if(pausedTimes.isEmpty())
        {
            pausedAt.setText("");
        }
        else{

            String PauseString = "Paused at ";

            for (int pausedTime : pausedTimes)
                PauseString += pausedTime + ", ";
            pausedAt.setText(PauseString + "seconds");
        }
        db.close();

        dialog.show();
    }

    private AlertDialog CreateDialog( String message ){
        return new AlertDialog.Builder(getActivity())
                //set message, title
                .setTitle("Exercise Details")
                .setMessage(message)
                .create();
    }

    private void setOnClick(final Button btn, final int ExerciseId, final Dialog dialog){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                db.updateExerciseCount(db.isUserLoggedIn(), ExerciseId);
                ((Activity_container)getActivity()).load_exercise_intro();
                db.close();
                dialog.dismiss();
            }
        });
    }
}
