package com.example.darren.new_design;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Darren on 14/12/2014.
 */

public class _3D_object extends Fragment {
    _3D_Cubesurfaceview view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = new _3D_Cubesurfaceview(getActivity());
        return view;
    }

}
