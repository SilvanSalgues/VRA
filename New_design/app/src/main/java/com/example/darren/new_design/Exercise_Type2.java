package com.example.darren.new_design;

import android.widget.ImageView;

public class Exercise_Type2 extends Exercise_Type {

    public Exercise_Type2(){
        super();
    }

    @Override
    public void GetLayout(){
        this.layout_id = R.layout.exercise_type_2;
    }
    @Override
    public void Addwidgets() {
        ImageView left_point = (ImageView) InputFragmentView.findViewById(R.id.left_point);
        ImageView right_point = (ImageView) InputFragmentView.findViewById(R.id.right_point);


        left_point.getLayoutParams().height = starting_size;           // Sets the height of the focus point image to the smallest size (square)
        left_point.getLayoutParams().width = starting_size;            // Sets the width of the focus point image to the smallest size

        right_point.getLayoutParams().height = starting_size;
        right_point.getLayoutParams().width = starting_size;
    }
}
