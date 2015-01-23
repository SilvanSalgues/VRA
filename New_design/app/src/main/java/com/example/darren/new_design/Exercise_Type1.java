package com.example.darren.new_design;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


public class Exercise_Type1 extends Fragment{

    ImageView center_point;
    String Colour;

    // These two arrays hold information about the different pixel sizes the focus point can be and the log value of these sizes
    float[] fontsize = {9.9969913803f, 12.5854665423f, 15.8441637534f, 19.9466205854f, 25.1113079486f, 31.6132645265f, 39.7987437114f, 50.1036531064f, 63.0767686849f};
    float[] logMAR = {-0.3f, -0.2f, -0.1f, 0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View InputFragmentView = inflater.inflate(R.layout.exercise_type_1, container, false);

        center_point = (ImageView) InputFragmentView.findViewById(R.id.center_point);

        int starting_size = (int) (fontsize[0] * pixel_density());      // Starting size is the smallest the focus point can be and the first position in the array
        center_point.getLayoutParams().height=starting_size;           // Sets the height of the focus point image to the smallest size (square)
        center_point.getLayoutParams().width=starting_size;            // Sets the width of the focus point image to the smallest size

        return InputFragmentView;
    }

    float pixel_density() {
        // Source of focus point size
        // Sample width of screen in mm     : 280
        // Sample width of screen in pixels : 1280

        float inches = 280 / 25.4f; // There is 25.4 mm in an inch
        float xdpi = 1280 / inches; // How many pixels per inch (Over the width of the screen)

        float device_xdpi = getActivity().getResources().getDisplayMetrics().xdpi;  // Gets the devices xdpi ( The number of dots(pixels) per inch over the width of the device )
        return device_xdpi / xdpi;    // Returns the difference between sample device and actual device dpi
    }

    public void setColour( String colour ){

        this.Colour = colour;
 /*       Context context = getActivity().getApplicationContext();
        CharSequence text = "Set Colour - 1";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();*/
    }
}
