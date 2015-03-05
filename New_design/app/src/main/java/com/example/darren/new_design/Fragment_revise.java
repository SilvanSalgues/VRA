package com.example.darren.new_design;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
                    //Log.d("expandableListTitle", "" + expandableListDetail.get(pos).getExTitle());
                    expandableListTitle.add(""+ expandableListDetail.get(pos).getExTitle());
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
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Day "+ (pos +1) +":"+ expandableListDetail.get(groupPosition).getExTitle() + " List Expanded.", Toast.LENGTH_SHORT).show();
                }
            });

            expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                @Override
                public void onGroupCollapse(int groupPosition) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Day "+ (pos +1) +":"+ expandableListTitle.get(groupPosition) + " List Collapsed.",Toast.LENGTH_SHORT).show();

                }
            });

            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            expandableListTitle.get(groupPosition)
                                    + " -> "
                                    + "Day "+ (pos +1) +":"+ expandableListDetail.get(groupPosition).getExList().get(childPosition),Toast.LENGTH_SHORT)
                            .show();
                    return false;
                }
            });

            //expandableListDetail.clear();
        }
//

//        TableLayout[] bg = new TableLayout[7];
//        TextView[] day = new TextView [7];
//        ImageView[][] tick = new ImageView[7][5];
//        String [] Colours = new String[]{"#997375FA","#9973FAFA","#9973FA77","#99E8FA73","#99FAD873","#99FAA273","#99FA7373"};
//
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT
//        );
//        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        params.setMargins(30, 0, 0, 0);
//
//

//
//            //Cursor cursor =  db.getAllExercises(i,0);
//
//            // Declaring components
//            bg[i] = (TableLayout) inflated[i].findViewById(R.id.tableLayout);
//            day[i] = (TextView) inflated[i].findViewById(R.id.day);
//
//            tick[i][0] = (ImageView) inflated[i].findViewById(R.id.tick1);
//            tick[i][1] = (ImageView) inflated[i].findViewById(R.id.tick2);
//            tick[i][2] = (ImageView) inflated[i].findViewById(R.id.tick3);
//            tick[i][3] = (ImageView) inflated[i].findViewById(R.id.tick4);
//            tick[i][4] = (ImageView) inflated[i].findViewById(R.id.tick5);
//
//
//            // Changing Values of the components
//
//            day[i].setBackgroundColor(Color.parseColor(Colours[i]));
//            day[i].setText("Day "+ (i+1));
//            day[i].setLayoutParams(params);
//            bg[i].setBackgroundColor(Color.parseColor(Colours[i]));
//
//
//            tick[i][0].setBackground(getResources().getDrawable(R.drawable.unticked1));
//            tick[i][1].setBackground(getResources().getDrawable(R.drawable.unticked1));
//            tick[i][2].setBackground(getResources().getDrawable(R.drawable.unticked1));
//            tick[i][3].setBackground(getResources().getDrawable(R.drawable.unticked1));
//            tick[i][4].setBackground(getResources().getDrawable(R.drawable.unticked1));
//
//            Layout.addView(inflated[i]);
//        }
//        tick[0][0].setBackground(getResources().getDrawable(R.drawable.ticked1));
//        tick[0][1].setBackground(getResources().getDrawable(R.drawable.ticked1));
//        tick[0][2].setBackground(getResources().getDrawable(R.drawable.ticked1));
//        tick[0][3].setBackground(getResources().getDrawable(R.drawable.ticked1));
//        tick[0][4].setBackground(getResources().getDrawable(R.drawable.exmarked1));
//
//        tick[1][0].setBackground(getResources().getDrawable(R.drawable.ticked1));

        return InputFragmentView;
    }
}
