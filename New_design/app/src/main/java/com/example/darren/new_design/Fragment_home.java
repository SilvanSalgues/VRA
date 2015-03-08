package com.example.darren.new_design;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Fragment_home extends Fragment{

    ImageButton profile;
    TextView exercise_complete_number, last_active_time, Name;
    Button edit_profile;
    //PopupWindow popWindow;

    String email = "email@admin.com";

    Database_Manager db;
    Cursor curUser;

    NumberPicker numberPicker;
    EditText update_name,update_country, update_password, update_verify_pass;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View InputFragmentView = inflater.inflate(R.layout.home, container, false);

        //Welcome = (TextView) InputFragmentView.findViewById(R.id.welcome);

        // Font path
        //String fontPath = "fonts/pali-helvetica-bold.ttf";
        // Loading Font Face
        //Typeface tf = Typeface.createFromAsset(this.getResources().getAssets(), fontPath);
        // Applying font
        //Welcome.setTypeface(tf);

        db = new Database_Manager(getActivity());

        exercise_complete_number = (TextView) InputFragmentView.findViewById(R.id.exercise_complete_number);
        last_active_time = (TextView) InputFragmentView.findViewById(R.id.last_active_time);
        Name = (TextView) InputFragmentView.findViewById(R.id.Name);

        db.open();

        curUser = db.getUser(email);
        curUser.moveToFirst();
        Name.setText(curUser.getString(0));
        exercise_complete_number.setText("" + db.getExerciseCount(email));

        if (curUser.getString(3) != null)
        {
            last_active_time.setText(curUser.getString(3));
        }
        else last_active_time.setText("No Exercise Recorded");

        db.close();


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

        graph.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.CENTER);
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
        edit_profile = (Button) InputFragmentView.findViewById(R.id.edit_profile);

        edit_profile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                open_Dialog();
                //open_Popup();
                //popWindow.setFocusable(true); // Shows keyboard on edittext box
                //popupWindow.update();

//                Toast toast = Toast.makeText(getActivity(), "show dialog", Toast.LENGTH_SHORT);
//                toast.show();
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
        Bitmap sourceBitmap;
        sourceBitmap = scaleBitmapImage;
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

    /*void open_Popup(){

        LayoutInflater popSwitchView = (LayoutInflater) getActivity().getBaseContext().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View popupview = popSwitchView.inflate(R.layout.update_user, null);
        popWindow= new PopupWindow(popupview);
        popWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);


        //InstructBtn = (Button) popupview.findViewById(R.id.instructions_close);
        //InstructBtn.setOnClickListener(new View.OnClickListener() {     // If the button at the bottom of the popup is pressed
        //    @Override
        //    public void onClick(View v) {
        //        popupWindow.dismiss();                  // Closes popup window
        //        mycounter.Start();
        //    }
        //});



        //Drawable d = getResources().getDrawable(R.drawable.popbg);
        //popWindow.setBackgroundDrawable(d);

        popupview.post(new Runnable() {                 // Waits until the activity window has been displayed before loading the popup windows
            public void run() {
                popWindow.showAtLocation(getActivity().findViewById(R.id.home_layout), Gravity.CENTER, 0, 0);
                popWindow.setOutsideTouchable(false);
            }
        });
    }*/

    public void open_Dialog() {
        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.update_user);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        update_name = (EditText) dialog.findViewById(R.id.update_name);
        update_country = (EditText) dialog.findViewById(R.id.update_country);
        update_password = (EditText) dialog.findViewById(R.id.update_password);
        update_verify_pass = (EditText) dialog.findViewById(R.id.update_verify_pass);
        numberPicker = (NumberPicker) dialog.findViewById(R.id.numberPicker);

        numberPicker.setMaxValue(9);
        numberPicker.setMinValue(1);

        update_name.setText(curUser.getString(0));
        update_country.setText(curUser.getString(5));
        update_password.setText(curUser.getString(2));

        //dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        //TextView text = (TextView) dialog.findViewById(R.id.text);
        //text.setText("Android custom dialog example!");
        //ImageView image = (ImageView) dialog.findViewById(R.id.image);
        //image.setImageResource(R.drawable.ic_launcher);

        //Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        //dialogButton.setOnClickListener(new OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        dialog.dismiss();
        //    }
        //});

        dialog.show();
    }
}