package com.example.darren.new_design;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class Fragment_revise extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View InputFragmentView = inflater.inflate(R.layout.revise, container, false);

        LinearLayout Layout=(LinearLayout) InputFragmentView.findViewById(R.id.linear_exercise);

        View [] inflated = new View[7];
        TableLayout[] bg = new TableLayout[7];
        TextView[] day = new TextView [7];
        ImageView[][] tick = new ImageView[7][5];
        String [] Colours = new String[]{"#997375FA","#9973FAFA","#9973FA77","#99E8FA73","#99FAD873","#99FAA273","#99FA7373"};

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.setMargins(30, 0, 0, 0);


        for(int i = 0; i<7; i++) {
            inflated[i] = getActivity().getLayoutInflater().inflate(R.layout.exercise_day, null);

            //Cursor cursor =  db.getAllExercises(i,0);

            // Declaring components
            bg[i] = (TableLayout) inflated[i].findViewById(R.id.tableLayout);
            day[i] = (TextView) inflated[i].findViewById(R.id.day);

            tick[i][0] = (ImageView) inflated[i].findViewById(R.id.tick1);
            tick[i][1] = (ImageView) inflated[i].findViewById(R.id.tick2);
            tick[i][2] = (ImageView) inflated[i].findViewById(R.id.tick3);
            tick[i][3] = (ImageView) inflated[i].findViewById(R.id.tick4);
            tick[i][4] = (ImageView) inflated[i].findViewById(R.id.tick5);


            // Changing Values of the components

            day[i].setBackgroundColor(Color.parseColor(Colours[i]));
            day[i].setText("Day "+ (i+1));
            day[i].setLayoutParams(params);
            bg[i].setBackgroundColor(Color.parseColor(Colours[i]));


            tick[i][0].setBackground(getResources().getDrawable(R.drawable.unticked1));
            tick[i][1].setBackground(getResources().getDrawable(R.drawable.unticked1));
            tick[i][2].setBackground(getResources().getDrawable(R.drawable.unticked1));
            tick[i][3].setBackground(getResources().getDrawable(R.drawable.unticked1));
            tick[i][4].setBackground(getResources().getDrawable(R.drawable.unticked1));

            Layout.addView(inflated[i]);
        }
        tick[0][0].setBackground(getResources().getDrawable(R.drawable.ticked1));
        tick[0][1].setBackground(getResources().getDrawable(R.drawable.ticked1));
        tick[0][2].setBackground(getResources().getDrawable(R.drawable.ticked1));
        tick[0][3].setBackground(getResources().getDrawable(R.drawable.ticked1));
        tick[0][4].setBackground(getResources().getDrawable(R.drawable.exmarked1));

        tick[1][0].setBackground(getResources().getDrawable(R.drawable.ticked1));
        return InputFragmentView;
    }
}
