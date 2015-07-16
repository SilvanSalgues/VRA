// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.rehabilitation.VRA.Home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.rehabilitation.VRA.Database.Database_Manager;
import com.example.darren.VRA.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Fragment_home extends Fragment{

    //static final int CAMERA_CODE = 101, GALLERY_CODE = 201, CROPING_CODE = 301;
    //Uri ImageUri;
    //File outPutFile = null;

    TextView exercise_complete_number, last_active_time, Name, useremail, Pixels;
    Button edit_profile;
    //Button camera_icon;
    //ImageButton profile;
    ImageView info_home;

    Database_Manager db;
    Cursor curUser;

    NumberPicker numberPicker;
    EditText update_name,update_country, update_password, update_verify_pass;

    float[] logMAR = {-0.3f, -0.2f, -0.1f, 0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f};

    AlertDialog diaBox;

    //int profile_image_size;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View InputFragmentView = inflater.inflate(R.layout.home, container, false);

        //profile_image_size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

        //outPutFile = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");

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
        useremail = (TextView) InputFragmentView.findViewById(R.id.useremail);

        db.open();


        curUser = db.getUser(db.isUserLoggedIn());
        curUser.moveToFirst();
        Name.setText(curUser.getString(0));
        useremail.setText(curUser.getString(1));
        exercise_complete_number.setText(curUser.getString(4));

        if (curUser.getString(3) != null)
        {
            last_active_time.setText(curUser.getString(3));
        }
        else last_active_time.setText("No Exercise Record");



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
        //series.setDrawDataPoints(true);

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


        //profile = (ImageButton) InputFragmentView.findViewById(R.id.profile);

        /*// The user already has a photo. Set the profile picture as that photo
        if (db.getUserImage(db.isUserLoggedIn()) != null && db.getUserImage(db.isUserLoggedIn()).length > 0){
            Bitmap photo = getImage(db.getUserImage(db.isUserLoggedIn()));
            profile.setImageBitmap(roundIMG(photo));
        }

        profile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageOption();
            }
        });
        camera_icon = (Button) InputFragmentView.findViewById(R.id.camera_icon);
        camera_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageOption();
            }
        });
*/
        edit_profile = (Button) InputFragmentView.findViewById(R.id.edit_profile);
        edit_profile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                open_Dialog();
            }
        });

        info_home = (ImageView) InputFragmentView.findViewById(R.id.info_home);
        info_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaBox = CreateDialog("Extra Information");
                diaBox.show();
            }
        });

        db.close();

        return InputFragmentView;
    }
   /* private void selectImageOption() {
        final CharSequence[] items = { "Capture Photo", "Choose from Gallery", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo")) {

                    try {
                        Log.d("Capture Image Intent", "Should be!");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp1.jpg");
                        ImageUri = Uri.fromFile(f);
                        Log.d("Temp File location", "" + ImageUri);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUri);
                        startActivityForResult(intent, CAMERA_CODE);
                    }
                    catch(ActivityNotFoundException e){
                        //display an error message
                        String errorMessage = "Device doesn't support capturing images!";
                        Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
                        toast.show();
                    }

                } else if (items[item].equals("Choose from Gallery")) {

                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_CODE);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }*/

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        {

            // If the Galley has produced a result run this code
            if (requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK && null != data) {

                ImageUri = data.getData();
                Log.d("Gallery Image URI : ", "" + ImageUri);

                cropIMG();

            }
            // If the Camera has produced a result then run this code
            else if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {

                //Log.d("Camera Image URI : ", "" + ImageUri);

                cropIMG();

            }
            // If the Cropping has been completed then run this code
            else if (requestCode == CROPING_CODE) {
                try {
                    if (outPutFile.exists()) {
                        Bitmap photo = decodeFile(outPutFile);

                        profile.setImageBitmap(roundIMG(photo));
                        Log.d("profile set", "Should be!");
                        //Storing Byte array in sqlite database
                        //SQLiteDatabase.open();
                        //byte [] storage = getBytes(photo);
                        //if (storage != null) {
                        //    SQLiteDatabase.updateUSERPhoto(SQLiteDatabase.isUserLoggedIn(), storage);
                        //}
                        //SQLiteDatabase.close();
                    } else {
                        Toast.makeText(getActivity(), "Error while saving image", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d("CROPING_CODE : ", "" + e);
                }
            }
            else
                Log.d("requestCode not right: ", "" + requestCode);
        }
    }*/

   /* private void cropIMG() {

        final ArrayList<CropingOption> cropOptions = new ArrayList<>();

        Log.d("Calling Crop Intent", "Should be!");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image*//*");

        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        int size = list.size();
        if (size == 0) {
            Toast.makeText(getActivity(), "Can't find image cropping app", Toast.LENGTH_SHORT).show();
        } else {
            intent.setData(ImageUri);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);

            //Create output file here
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outPutFile));

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, CROPING_CODE);
            } else {
                for (ResolveInfo res : list) {
                    final CropingOption co = new CropingOption();

                    co.title = getActivity().getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon = getActivity().getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);
                    co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    cropOptions.add(co);
                }

                CropingOptionAdapter adapter = new CropingOptionAdapter(getActivity().getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Croping App");
                builder.setCancelable(false);
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        startActivityForResult(cropOptions.get(item).appIntent, CROPING_CODE);
                    }
                });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        if (ImageUri != null) {
                            getActivity().getContentResolver().delete(ImageUri, null, null);
                            ImageUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }


    private Bitmap roundIMG(Bitmap scaleBitmapImage) {


        Bitmap targetBitmap = Bitmap.createBitmap(profile_image_size,
                profile_image_size, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(
                ((float) profile_image_size - 1) / 2,
                ((float) profile_image_size - 1) / 2,
                (Math.min(((float) profile_image_size), ((float) profile_image_size)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap;
        sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(
                sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap
                        .getHeight()), new Rect(0, 0, profile_image_size,
                        profile_image_size), null);
        return targetBitmap;
    }
    private Bitmap decodeFile(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < profile_image_size || height_tmp / 2 < profile_image_size)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        }
        catch (FileNotFoundException e) {
            //
        }
        return null;
    }*/

    private void open_Dialog() {
        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.update_user);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        db.open();

        curUser = db.getUser(db.isUserLoggedIn());
        curUser.moveToFirst();

        update_name = (EditText) dialog.findViewById(R.id.update_name);
        update_country = (EditText) dialog.findViewById(R.id.update_country);
        update_password = (EditText) dialog.findViewById(R.id.update_password);
        update_verify_pass = (EditText) dialog.findViewById(R.id.update_verify_pass);
        numberPicker = (NumberPicker) dialog.findViewById(R.id.numberPicker);
        Pixels = (TextView) dialog.findViewById(R.id.Pixels);

        numberPicker.setMaxValue(9);
        numberPicker.setMinValue(1);

        numberPicker.setValue(db.getpointsize(db.isUserLoggedIn()));
        Pixels.setText(logMAR[db.getpointsize(db.isUserLoggedIn())] + " LogMAR");

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Pixels.setText(logMAR[newVal - 1] + " LogMAR");
            }
        });

        update_name.setText(curUser.getString(0));
        update_country.setText(curUser.getString(5));
        update_password.setText(curUser.getString(2));
        update_verify_pass.setText(curUser.getString(2));

        Button SaveBtn = (Button) dialog.findViewById(R.id.SaveBtn);
        // if button is clicked, close the custom dialog
        SaveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if( update_name.getText().toString().length() == 0 ) {
                    update_name.setError("Username is required!");
                }
                else if ( update_password.getText().toString().length() == 0 ){
                    update_password.setError( "password is required!" );
                }
                else if (!update_verify_pass.getText().toString().equals( update_password.getText().toString()) ){
                    update_verify_pass.setError( "verify password does not match!" );
                }
                else {

                    db.updateUSER(db.isUserLoggedIn(), update_name.getText().toString(), update_password.getText().toString(), update_country.getText().toString(), numberPicker.getValue());
                    db.close();
                    Name.setText(update_name.getText().toString());
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
/*

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
*/


    private AlertDialog CreateDialog( String message ){
        return new AlertDialog.Builder(getActivity())
                //set message, title
                .setTitle("Exercise Details")
                .setMessage(message)
                .create();
    }
}