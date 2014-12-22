package com.example.darren.new_design;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;


public class home extends Fragment{

    ImageButton profile;
    ImageView imgFavorite;
    TextView Welcome;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View InputFragmentView = inflater.inflate(R.layout.home, container, false);


        Welcome = (TextView) InputFragmentView.findViewById(R.id.welcome);

        // Font path
        String fontPath = "fonts/pali-helvetica-regular.ttf";
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(this.getResources().getAssets(), fontPath);
        // Applying font
        Welcome.setTypeface(tf);

        int num = 150;
        GraphView.GraphViewData[] data = new GraphView.GraphViewData[7];
        data[0] =  new GraphView.GraphViewData(1, 9.0d);
        data[1] =  new GraphView.GraphViewData(2, 8.0d);
        data[2] =  new GraphView.GraphViewData(3, 5.0d);
        data[3] =  new GraphView.GraphViewData(4, 7.0d);
        data[4] =  new GraphView.GraphViewData(5, 4.0d);
        data[5] =  new GraphView.GraphViewData(6, 2.0d);
        data[6] =  new GraphView.GraphViewData(7, 1.0d);

                                // init example series data
        GraphViewSeries Dizziness_graph = new GraphViewSeries("Dizziness",  new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(200, 50, 00), 3), data);

        GraphView graphView = new LineGraphView(
                this.getActivity() // context
                , "Dizziness Progress" // heading
        );
        ((LineGraphView) graphView).setDrawBackground(true);
        graphView.getGraphViewStyle().setVerticalLabelsWidth(50);
        graphView.getGraphViewStyle().setLegendWidth(250);
        graphView.setManualYAxisBounds(10, 1);

        graphView.setHorizontalLabels(new String[] {"Week1", "Week2", "Week3", "Week4", "Week5", "Week6", "Week6"});
        graphView.setVerticalLabels(new String[] {"10", "9", "8", "7", "6", "5", "4", "3", "2", "1"});
        graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.WHITE);
        graphView.getGraphViewStyle().setVerticalLabelsColor(Color.WHITE);
        graphView.getGraphViewStyle().setTextSize(40);
        graphView.addSeries(Dizziness_graph); // data
        graphView.setShowLegend(true);

        LinearLayout layout = (LinearLayout) InputFragmentView.findViewById(R.id.layout);
        layout.addView(graphView);



        profile = (ImageButton) InputFragmentView.findViewById(R.id.profile);
        imgFavorite = (ImageView) InputFragmentView.findViewById(R.id.profile_pic);
        profile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                open();
            }
        });
        return InputFragmentView;
    }
    public void open(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 243;
        int targetHeight = 243;

        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(
                ((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(
                sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap
                        .getHeight()), new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bp = (Bitmap) data.getExtras().get("data");
        imgFavorite.setImageBitmap(getRoundedShape(bp));
    }
}