// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.example.darren.VRA.Exercise;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.darren.VRA.R;

public class Exercise_Type1 extends Exercise_Type{

    public Exercise_Type1(){
        super();
    }

    @Override
    public Exercise_Type newInstance(int GIF) {
        Exercise_Type fragment = new Exercise_Type1();
        Bundle bundle = new Bundle(1);
        bundle.putInt("bgGIF", GIF);
        fragment.setArguments(bundle);
        return fragment;
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
