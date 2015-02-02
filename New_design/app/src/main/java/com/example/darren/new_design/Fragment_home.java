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
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Fragment_home extends Fragment{

    ImageButton profile;
    ImageView imgFavorite;
    TextView Welcome;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View InputFragmentView = inflater.inflate(R.layout.home, container, false);


        Welcome = (TextView) InputFragmentView.findViewById(R.id.welcome);

        // Font path
        String fontPath = "fonts/pali-helvetica-bold.ttf";
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(this.getResources().getAssets(), fontPath);
        // Applying font
        Welcome.setTypeface(tf);

        DataPoint[] data = new DataPoint[] {
                new DataPoint(1, 9.0d),
                new DataPoint(2, 8.0d),
                new DataPoint(3, 5.0d),
                new DataPoint(4, 7.0d),
                new DataPoint(5, 4.0d),
                new DataPoint(6, 2.0d),
                new DataPoint(7, 1.0d)};

        GraphView graph = (GraphView) InputFragmentView.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);

        series.setColor(Color.rgb(60, 121, 140));
        series.setThickness(8);
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.argb(200, 236, 239, 244));
        series.setDrawDataPoints(true);

        graph.addSeries(series);


        graph.getGridLabelRenderer().setNumVerticalLabels(10);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(1);
        graph.getViewport().setMaxY(10);

        graph.getGridLabelRenderer().setNumHorizontalLabels(7);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(7);


        profile = (ImageButton) InputFragmentView.findViewById(R.id.profile);
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
        int targetWidth = 206;
        int targetHeight = 206;

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
        profile.setImageBitmap(getRoundedShape(bp));
    }
}