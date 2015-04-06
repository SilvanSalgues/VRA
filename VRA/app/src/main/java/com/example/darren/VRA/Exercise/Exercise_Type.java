// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.example.darren.VRA.Exercise;

import android.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.darren.VRA.Database.Database_Manager;
import com.example.darren.VRA.R;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public abstract class Exercise_Type  extends Fragment {



    // These two arrays hold information about the different pixel sizes the focus point can be and the log value of these sizes
    float[] pointsize = {9.9969913803f, 12.5854665423f, 15.8441637534f, 19.9466205854f, 25.1113079486f, 31.6132645265f, 39.7987437114f, 50.1036531064f, 63.0767686849f};
    //float[] logMAR = {-0.3f, -0.2f, -0.1f, 0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f};

    public int starting_size;
    public int layout_id;

    int GIF;
    View InputFragmentView;
    GifImageView GIFimage;
    GifDrawable gifFromResource;
    Database_Manager db;

    public Exercise_Type newInstance(int GIF){ return null; }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        GetLayout();
        InputFragmentView = inflater.inflate(layout_id, container, false);
        db = new Database_Manager(getActivity());
        db.open();
        starting_size = (int) (pointsize[db.getpointsize(db.isUserLoggedIn()) -1] * pixel_density());      // Starting size is the smallest the focus point can be and the first position in the array
        db.close();

        GIFimage = (GifImageView) InputFragmentView.findViewById(R.id.GIFview);
        //GIFimage.setBackgroundColor(Color.BLUE);

        GIF = getArguments().getInt("bgGIF");

        if(gifFromResource == null) {
            //resource (drawable or raw)
            try {
                // Load gifs from an array of gifs from an array stored in images.xml
                TypedArray imgs = getResources().obtainTypedArray(R.array.list_images);
                gifFromResource = new GifDrawable(getResources(), imgs.getResourceId(GIF, -1));
                imgs.recycle();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        GIFimage.setBackground(gifFromResource);// R.drawable.illusion1

        Addwidgets();
        return InputFragmentView;
    }

    public void GetLayout(){}
    public void Addwidgets(){}

    public float pixel_density() {
        // Source of focus point size
        // Sample width of screen in mm     : 280
        // Sample width of screen in pixels : 1280

        float inches = 280 / 25.4f; // There is 25.4 mm in an inch
        float xdpi = 1280 / inches; // How many pixels per inch (Over the width of the screen)

        float device_xdpi = getActivity().getResources().getDisplayMetrics().xdpi;  // Gets the devices xdpi ( The number of dots(pixels) per inch over the width of the device )
        return device_xdpi / xdpi;    // Returns the difference between sample device and actual device dpi
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        InputFragmentView = null; // now cleaning up!
        gifFromResource.recycle();
        Log.d("Exercise Type", "DestroyView Called");
    }
}
