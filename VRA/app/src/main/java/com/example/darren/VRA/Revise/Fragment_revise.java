// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.example.darren.VRA.Revise;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.darren.VRA.Database.Database_Manager;
import com.example.darren.VRA.Exercise.ExerciseList;
import com.example.darren.VRA.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_revise extends Fragment {

    ExpandableListView expandableListView;
    ArrayList<ExpandableListAdapter> expandableListAdapter;
    List<String> expandableListTitle;
    ArrayList<ArrayList<ExerciseList>> listAllExercises;

    LinearLayout Layout;
    View [] inflated = new View[7];
    TextView day, WeekNo;

    RadioGroup radioGroup;
    RadioButton radioButton;

    ImageView btn_info_intro;
    Database_Manager db;
    AlertDialog diaBox;
    int ExerciseNum;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View InputFragmentView = inflater.inflate(R.layout.revise, container, false);
        db = new Database_Manager(getActivity());

        WeekNo = (TextView) InputFragmentView.findViewById(R.id.WeekNo);

        // The Radio Group Changes the Week to view the exercise lists
        radioGroup = (RadioGroup) InputFragmentView.findViewById(R.id.radioWeeks);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Find which radio button is selected
                if(checkedId == R.id.radioWeek2) {
                    WeekNo.setText("Week 2 Exercises");
                }
                else if(checkedId == R.id.radioWeek3) {
                    WeekNo.setText("Week 3 Exercises");
                }
                else if(checkedId == R.id.radioWeek4) {
                    WeekNo.setText("Week 4 Exercises");
                }
                else if(checkedId == R.id.radioWeek5) {
                    WeekNo.setText("Week 5 Exercises");
                }
                else if(checkedId == R.id.radioWeek6) {
                    WeekNo.setText("Week 6 Exercises");
                }
                else if(checkedId == R.id.radioWeek7) {
                    WeekNo.setText("Week 7 Exercises");
                }
                else {
                    WeekNo.setText("Week 1 Exercises");
                }
            }
        });

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
        ExpandableListDataPump expump = new ExpandableListDataPump(getActivity());

        // Stores a list of every exercise split into a list for each day
        // SO a WHOLE day of exercises is added to this list
        listAllExercises = new ArrayList<>();

        // This is a list of expandable list adapters
        // The reason for this is that each day many have a different number of exercises that require on click listeners
        expandableListAdapter = new ArrayList<>();

        // This for loop currently loads up 7 days
        for(int i = 0; i<7; i++) {

            // Holds the list of different times of the day exercises are set for . eg Morning Exercise#1
            expandableListTitle = new ArrayList<>();

            // Adding a day of exercises from the expandableListDataPump class. Takes a parameter of the day of the week
            listAllExercises.add(expump.getData(i+1));


            if (listAllExercises.get(i) != null){
                for (int pos = 0; pos < listAllExercises.get(i).size(); pos++) {
                    expandableListTitle.add(listAllExercises.get(i).get(pos).getExTitle());
                }
            }


            inflated[i] = getActivity().getLayoutInflater().inflate(R.layout.exercise_day, null);

            day = (TextView)inflated[i].findViewById(R.id.day);
            day.setText("Day " + (i+1));
            expandableListView = (ExpandableListView) inflated[i].findViewById(R.id.expandableListView);
            Layout.addView(inflated[i]);

            expandableListAdapter.add(new ExpandableListAdapter(getActivity(), expandableListTitle, listAllExercises.get(i)));

            expandableListView.setAdapter(expandableListAdapter.get(i));
            final int day = i;
            expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
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

            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    int week = 1;
                    Log.d("Revise, Child List", "Day " + (day + 1) + ", Week " + week + ", Title " +
                            expandableListTitle.get(groupPosition) + ", ChildPosition" + childPosition +
                            "" + listAllExercises.get(day).get(groupPosition).getExList().get(childPosition));

                    db.open();

                    ExerciseNum = db.getExerciseNum(day + 1, week, expandableListTitle.get(groupPosition), childPosition);
                    diaBox = CreateDialog(
                            "Day " + (day + 1) +
                                    "\n" + expandableListTitle.get(groupPosition) +
                                    "\n" + listAllExercises.get(day).get(groupPosition).getExList().get(childPosition) + "\n" +
                                    "\nExercise Description\n" + db.getExerciseDescription(ExerciseNum) + "\n" +
                                    "\nPaused: No Record" +
                                    "\nComplete: No Record");
                    db.close();
                    diaBox.show();
                    return false;
                }
            });

        }
        return InputFragmentView;
    }

    private AlertDialog CreateDialog( String message ){
        return new AlertDialog.Builder(getActivity())
                //set message, title
                .setTitle("Exercise Details")
                .setMessage(message)
                .create();
    }
}
