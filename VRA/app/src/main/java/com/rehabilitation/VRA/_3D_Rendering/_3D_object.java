// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.rehabilitation.VRA._3D_Rendering;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class _3D_object extends Fragment {
    _3D_Cubesurfaceview view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = new _3D_Cubesurfaceview(getActivity());
        return view;
    }

}
