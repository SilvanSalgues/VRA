// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.example.darren.VRA.Revise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.darren.VRA.R;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    ArrayList<ExerciseList> expandableListDetail;

    public ExpandableListAdapter(Context context, List<String> expandableListTitle, ArrayList<ExerciseList> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(listPosition).getExList().get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View ChildView, ViewGroup parent) {

        if (ChildView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ChildView = layoutInflater.inflate(R.layout.exercise_list_item, null);
        }

        // Set the text for the child title
        TextView expandedListTextView = (TextView) ChildView.findViewById(R.id.expandedListItem);
        expandedListTextView.setText("" + getChild(listPosition, expandedListPosition));

        // Checks if the child exercise has been complete and sets the appropriate indicator
        final int expandListComplete = expandableListDetail.get(listPosition).getExComplete().get(expandedListPosition);
        ImageView tick = (ImageView) ChildView.findViewById(R.id.tick);

        if(expandListComplete == 1){
            tick.setBackgroundResource(R.drawable.icon_green_tick);
        }
        else if (expandListComplete == 2 ){
            tick.setBackgroundResource(R.drawable.icon_red_x);
        }
        else {
            tick.setBackgroundResource(R.drawable.icon_yellow_blank);
        }


        return ChildView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(listPosition).getExList().size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View GroupView, ViewGroup parent) {

        if (GroupView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            GroupView = layoutInflater.inflate(R.layout.exercise_list_group, null);
        }

        // Sets the group title
        String Title = getGroup(listPosition).toString();
        TextView listTitle = (TextView) GroupView.findViewById(R.id.listTitle);
        listTitle.setText(Title);

        //Set time for group
        TextView time = (TextView) GroupView.findViewById(R.id.time);
        time.setText(this.expandableListDetail.get(listPosition).getExTime());

        // Sets the arrow on the group depending on whether it is expanded or not
        ImageView groupHolder = (ImageView) GroupView.findViewById(R.id.groupHolder);
        if (isExpanded) {
            groupHolder.setImageResource(R.drawable.icon_white_arrow_up);
        } else {
            groupHolder.setImageResource(R.drawable.icon_white_arrow_down);
        }

        return GroupView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}