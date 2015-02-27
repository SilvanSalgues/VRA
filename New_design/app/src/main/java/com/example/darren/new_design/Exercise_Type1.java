package com.example.darren.new_design;

import android.widget.ImageView;

public class Exercise_Type1 extends Exercise_Type{

    public Exercise_Type1(){
        super();
    }

    @Override
    public void GetLayout(){
        this.layout_id = R.layout.exercise_type_1;
    }

    @Override
    public void Addwidgets(){
        ImageView center_point = (ImageView) InputFragmentView.findViewById(R.id.center_point);

        center_point.getLayoutParams().height=starting_size;           // Sets the height of the focus point image to the smallest size (square)
        center_point.getLayoutParams().width=starting_size;            // Sets the width of the focus point image to the smallest size
    }
}
