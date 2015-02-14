package com.example.darren.new_design;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class Exercise_Type  extends Fragment {

    int Colour;

    // imagearray
    // imageid
    // for each imagearray set image id

    // These two arrays hold information about the different pixel sizes the focus point can be and the log value of these sizes
    float[] fontsize = {9.9969913803f, 12.5854665423f, 15.8441637534f, 19.9466205854f, 25.1113079486f, 31.6132645265f, 39.7987437114f, 50.1036531064f, 63.0767686849f};
    //float[] logMAR = {-0.3f, -0.2f, -0.1f, 0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f};
    public int starting_size;
    public int layout_id;

    View InputFragmentView;
    public Exercise_Type(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        GetLayout();
        InputFragmentView = inflater.inflate(layout_id, container, false);
        Colour = this.getArguments().getInt("bgColour");
        starting_size = (int) (fontsize[0] * pixel_density());      // Starting size is the smallest the focus point can be and the first position in the array

        //Toast toast = Toast.makeText(getActivity(), "bgColour :" + myValue , Toast.LENGTH_SHORT);
        //toast.show();

        //GifRun w = new  GifRun();
        //SurfaceView v = (SurfaceView) InputFragmentView.findViewById(R.id.surfaceView);
        //w.LoadGiff(v, getActivity(), R.drawable.illusion1);

        //ImageView GIFimage = (ImageView) InputFragmentView.findViewById(R.id.GIFimage);
        //GIFimage.setAdjustViewBounds(true);
        //GIFimage.setScaleType(ImageView.ScaleType.CENTER);
        //GIFimage.setImageDrawable(getResources().getDrawable(R.anim.my_animation));
        //AnimationDrawable frameAnimation = (AnimationDrawable) GIFimage.getDrawable();
        // Start the animation (looped playback by default).
        //frameAnimation.start();


        //RelativeLayout replaceLayout = (RelativeLayout) InputFragmentView.findViewById(R.id.replaceLayout);
        //replaceLayout.setBackgroundColor(Colour);

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
}
