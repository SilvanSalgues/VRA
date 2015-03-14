// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.example.darren.VRA.Main;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.darren.VRA.R;

public class Fragment_menu extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.menu, container, false);
    }
}

