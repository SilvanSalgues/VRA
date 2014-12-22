package com.example.darren.new_design;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Darren on 14/12/2014.
 */

public class object_3D extends Fragment {
    Cubesurfaceview  view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = new Cubesurfaceview(getActivity());
        return view;
    }

}
