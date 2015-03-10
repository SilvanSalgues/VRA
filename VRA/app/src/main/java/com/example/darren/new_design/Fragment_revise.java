// Copyright © 2015 Darren McNeely. All Rights Reserved.

package com.example.darren.new_design;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Fragment_revise extends Fragment {

    ExpandableListView expandableListView;
    ArrayList<ExpandableListAdapter> expandableListAdapter;
    List<String> expandableListTitle;
    ArrayList<ExerciseList> expandableListDetail;
    ArrayList<ArrayList<ExerciseList>> ListofList;

    LinearLayout Layout;
    View [] inflated = new View[7];
    TextView day, WeekNo;

    RadioGroup radioGroup;
    RadioButton radioButton;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View InputFragmentView = inflater.inflate(R.layout.revise, container, false);
        WeekNo = (TextView) InputFragmentView.findViewById(R.id.WeekNo);

        radioGroup = (RadioGroup) InputFragmentView.findViewById(R.id.radioWeeks);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
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


        Layout =(LinearLayout) InputFragmentView.findViewById(R.id.linear_exercise);
        ExpandableListDataPump expump = new ExpandableListDataPump(getActivity());
        ListofList = new ArrayList<>();
        expandableListAdapter = new ArrayList<>();

        for(int i = 0; i<7; i++) {
            expandableListTitle = new ArrayList<>();

            ListofList.add(expump.getData(i+1)); // parameter is the day of the week

            expandableListDetail = ListofList.get(i);

            if (expandableListDetail != null){
                for (int pos = 0; pos < expandableListDetail.size(); pos++) {
                    expandableListTitle.add(expandableListDetail.get(pos).getExTitle());
                }
            }


            inflated[i] = getActivity().getLayoutInflater().inflate(R.layout.exercise_day, null);

            day = (TextView)inflated[i].findViewById(R.id.day);
            day.setText("Day " + (i+1));
            expandableListView = (ExpandableListView) inflated[i].findViewById(R.id.expandableListView);
            Layout.addView(inflated[i]);

            expandableListAdapter.add(new ExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail));

            expandableListView.setAdapter(expandableListAdapter.get(i));
            final int pos = i;
            expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    Log.d("Revise", "Day " + (pos + 1) + ":" + expandableListDetail.get(groupPosition).getExTitle() + " List Expanded.");
                }
            });

            expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                @Override
                public void onGroupCollapse(int groupPosition) {
                    Log.d("Revise", "Day " + (pos + 1) + ":" + expandableListTitle.get(groupPosition) + " List Collapsed.");

                }
            });

            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    Log.d("Revise, Child List", expandableListTitle.get(groupPosition) + " -> "
                            + "Day " + (pos + 1) + ":" + expandableListDetail.get(groupPosition).getExList().get(childPosition));

                    AlertDialog diaBox = CreateDialog(expandableListTitle.get(groupPosition) + " -> "
                            + "Day " + (pos + 1) + ":" + expandableListDetail.get(groupPosition).getExList().get(childPosition));
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
