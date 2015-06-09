// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.example.darren.VRA.Sensor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darren.VRA.R;
import com.example.darren.VRA._3D_Rendering._3D_object;

import java.util.UUID;


public class Fragment_sensor extends Fragment implements BluetoothAdapter.LeScanCallback{

    float accelX = 0;
    float accelY = 0;
    float accelZ = 0;

    private boolean bound;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;

    private Bluetooth_RFduinoService rfduinoService;

    private TextView deviceInfoText;
    private TextView accelData;
    private TextView gyroData;

    TextView timestamp;
    TextView packetnumber;
    TextView batteryVoltage;

    ImageView arrow_nw;
    ImageView arrow_n;
    ImageView arrow_ne;
    ImageView arrow_w;
    ImageView arrow_center;
    ImageView arrow_e;
    ImageView arrow_sw;
    ImageView arrow_s;
    ImageView arrow_se;

    Fragment newFragment;

    LowPassFilter filterYaw = new LowPassFilter(0.03f);
    LowPassFilter filterPitch = new LowPassFilter(0.03f);
    LowPassFilter filterRoll = new LowPassFilter(0.03f);

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View InputFragmentView = inflater.inflate(R.layout.sensor, container, false);

        //Bluetooth in Android 4.3 is accessed via the BluetoothManager, rather than
        //the old static bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothManager manager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = manager.getAdapter();

        // 3d Cube
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (savedInstanceState == null) {
            newFragment = new _3D_object();
                       ft.replace(R.id.container_3D, newFragment);
                       ft.commit();
                   }

        //Check if the device has bluetooth LE capabilities and enable bluetooth if it has not been already.
        enable_bluetooth();

        // TextViews
        deviceInfoText = (TextView) InputFragmentView.findViewById(R.id.d_Info);
        timestamp = (TextView) InputFragmentView.findViewById(R.id.timestamp);
        packetnumber = (TextView) InputFragmentView.findViewById(R.id.packetnumber);
        accelData = (TextView) InputFragmentView.findViewById(R.id.accelData);
        batteryVoltage = (TextView) InputFragmentView.findViewById(R.id.batteryVoltage);
        gyroData = (TextView) InputFragmentView.findViewById(R.id.gyroData);

        arrow_nw = (ImageView) InputFragmentView.findViewById(R.id.arrow_nw) ;
        arrow_n  = (ImageView) InputFragmentView.findViewById(R.id.arrow_n);
        arrow_ne  = (ImageView) InputFragmentView.findViewById(R.id.arrow_ne);
        arrow_w  = (ImageView) InputFragmentView.findViewById(R.id.arrow_w);
        arrow_center  = (ImageView) InputFragmentView.findViewById(R.id.arrow_center);
        arrow_e  = (ImageView) InputFragmentView.findViewById(R.id.arrow_e);
        arrow_sw  = (ImageView) InputFragmentView.findViewById(R.id.arrow_sw);
        arrow_s  = (ImageView) InputFragmentView.findViewById(R.id.arrow_s);
        arrow_se  = (ImageView) InputFragmentView.findViewById(R.id.arrow_se);

        arrow_nw.setRotation(-45);
        arrow_ne.setRotation(45);
        arrow_w.setRotation(-90);
        arrow_e.setRotation(90);
        arrow_sw.setRotation(-135);
        arrow_s.setRotation(180);
        arrow_se.setRotation(135);

        return InputFragmentView;
    }


    private final ServiceConnection rfduinoServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            rfduinoService = ((Bluetooth_RFduinoService.LocalBinder) service).getService();
            if (rfduinoService.initialize()) {
                rfduinoService.connect(bluetoothDevice.getAddress());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            rfduinoService = null;
        }
    };

    private final BroadcastReceiver rfduinoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (Bluetooth_RFduinoService.ACTION_CONNECTED.equals(action))
            {
                bound = true;
//                timestamp.setVisibility(View.VISIBLE);
//                packetnumber.setVisibility(View.VISIBLE);
//                accelData.setVisibility(View.VISIBLE);
//                batteryVoltage.setVisibility(View.VISIBLE);
//                gyroData.setVisibility(View.VISIBLE);
            }
            if (Bluetooth_RFduinoService.ACTION_DISCONNECTED.equals(action)) {
//                timestamp.setVisibility(View.INVISIBLE);
//                packetnumber.setVisibility(View.INVISIBLE);
//                accelData.setVisibility(View.INVISIBLE);
//                batteryVoltage.setVisibility(View.INVISIBLE);
//                gyroData.setVisibility(View.INVISIBLE);
                onStop();
                onStart();
            }
            if (Bluetooth_RFduinoService.ACTION_DATA_AVAILABLE.equals(action))
            {
                addData(intent.getByteArrayExtra(Bluetooth_RFduinoService.EXTRA_DATA));
            }
        }
    };



    @Override
    public void onLeScan(BluetoothDevice device, final int rssi, final byte[] scanRecord) {
        bluetoothAdapter.stopLeScan(this);
        bluetoothDevice = device;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                deviceInfoText.setText(Bluetooth_Helper.getDeviceInfoText(bluetoothDevice, rssi, scanRecord));
                Intent rfduinoIntent = new Intent(getActivity(), Bluetooth_RFduinoService.class);
                getActivity().bindService(rfduinoIntent, rfduinoServiceConnection, Context.BIND_AUTO_CREATE);
                bound = true;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // Find Device
        if (bluetoothAdapter != null) {
            bluetoothAdapter.startLeScan(new UUID[]{Bluetooth_RFduinoService.UUID_SERVICE}, this);

            getActivity().registerReceiver(rfduinoReceiver, Bluetooth_RFduinoService.getIntentFilter());
        }
    }
    @Override
    public void onStop() {                       //When the app is closed, this runs
        super.onStop();

        bluetoothAdapter.stopLeScan(this);

        //unregisterReceiver(bluetoothStateReceiver);
        getActivity().unregisterReceiver(rfduinoReceiver);

        if(bound) {     // IF a connection has been made to the RFduino service
            getActivity().unbindService(rfduinoServiceConnection);    // Disconnect RFduino service
            bound = false;                              // Boolean to show if the service is connected
        }

    }
    @Override
    public void onResume() {      //When the app is brought back into focus
        super.onResume();

        //Check if the device has bluetooth LE capabilities and enable bluetooth if it has not been already.
        enable_bluetooth();

        onStop();
        onStart();
    }

    public void enable_bluetooth(){

        // Use this check to determine whether Bluetooth LE is supported on the device. Even though
        // the manifest will keep this application from installing on these devices

        if (!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(getActivity(), "No Bluetooth LE Support", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // force enables Bluetooth.

        if (bluetoothAdapter == null)
        {
            Toast.makeText( getActivity().getApplicationContext(), "Device does not have bluetooth", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        else if (!bluetoothAdapter.isEnabled()) {
            // Force Enable Bluetooth
            bluetoothAdapter.enable();
            Toast.makeText( getActivity().getApplicationContext(), "Bluetooth Enabled", Toast.LENGTH_SHORT).show();
        }
    }
    private void addData(byte[] data) {

        // Packet size: 18bytes
        //| timestamp 4bytes | packetNumber 1byte | accelData 6bytes | batteryVoltage 1byte | gyroData 6bytes |

        long seconds_passed = Bluetooth_Converter.timestamp_seconds(data);
        timestamp.setText("TimeStamp (4 bytes): " + seconds_passed/ 60 + " minutes " + seconds_passed % 60 + " seconds");


        packetnumber.setText("Packet number (1 bytes): " + Bluetooth_Converter.PactetNum(data));
        batteryVoltage.setText("Battery Voltage (1 bytes): " + Bluetooth_Converter.battery_voltage(data));

        float Heading = filterYaw.lowPass(Bluetooth_Converter.AccelX(data));
        float Pitch = filterPitch.lowPass(Bluetooth_Converter.AccelY(data));
        float Roll = filterRoll.lowPass(Bluetooth_Converter.AccelZ(data));

        accelData.setText("accelData (6 bytes): "   + "\nX: " + Heading// Bluetooth_Converter.AccelX(data)//AccelXDeg(data)//
                + "\nY: " + Pitch //Bluetooth_Converter.AccelY(data)
                + "\nZ: " + Roll); //Bluetooth_Converter.AccelZ(data));


        // The Gyroscope gives angular velocity of the device
        gyroData.setText("GyroData (6 bytes): " + "\nX: " + Bluetooth_Converter.GyroX(data)
                + "\nY: " + Bluetooth_Converter.GyroY(data)
                + "\nZ: " + Bluetooth_Converter.GyroZ(data));

        arrow_direction_statements(data);
    }
    public void arrow_direction_statements(byte[] data){

        accelX = Bluetooth_Converter.AccelX(data);
        accelY = Bluetooth_Converter.AccelY(data);
        accelZ = Bluetooth_Converter.AccelZ(data);
        // West
        if (accelX > 0.1f && (accelY  > -0.1 && accelY < 0.1))
        {
            arrow_w.setBackground(getResources().getDrawable(R.drawable.arrow_directional_on));
        }
        else
        {
            arrow_w.setBackground(getResources().getDrawable(R.drawable.arrow_directional));
        }

        // East
        if (accelX < -0.1f && (accelY > -0.1 && accelY < 0.1))
        {
            arrow_e.setBackground(getResources().getDrawable(R.drawable.arrow_directional_on));
        }
        else
        {
            arrow_e.setBackground(getResources().getDrawable(R.drawable.arrow_directional));
        }

        // Center
        if ((accelX < 0.1f && accelX > -0.1f) && (accelY < 0.1f && accelY > -0.1f))
        {
            arrow_center.setBackground(getResources().getDrawable(R.drawable.arrow_center_on));
        }
        else
        {
            arrow_center.setBackground(getResources().getDrawable(R.drawable.arrow_center));
        }

        // North West
        if (accelY < -0.1f && accelX > 0.1f){

            arrow_nw.setBackground(getResources().getDrawable(R.drawable.arrow_directional_on));
        }
        else
        {
            arrow_nw.setBackground(getResources().getDrawable(R.drawable.arrow_directional));
        }

        // North
        if (accelY < -0.1f && (accelX > -0.1f && accelX < 0.1f ))
        {
            arrow_n.setBackground(getResources().getDrawable(R.drawable.arrow_directional_on));
        }
        else
        {
            arrow_n.setBackground(getResources().getDrawable(R.drawable.arrow_directional));
        }

        // North East
        if (accelY < -0.1f && accelX < -0.1f){

            arrow_ne.setBackground(getResources().getDrawable(R.drawable.arrow_directional_on));
        }
        else
        {
            arrow_ne.setBackground(getResources().getDrawable(R.drawable.arrow_directional));
        }

        // South West
        if (accelY > 0.1f && accelX > 0.1f){

            arrow_sw.setBackground(getResources().getDrawable(R.drawable.arrow_directional_on));
        }
        else
        {
            arrow_sw.setBackground(getResources().getDrawable(R.drawable.arrow_directional));
        }

        // South
        if (accelY > 0.1f && (accelX > -0.1f && accelX < 0.1f ))
        {
            arrow_s.setBackground(getResources().getDrawable(R.drawable.arrow_directional_on));
        }
        else
        {
            arrow_s.setBackground(getResources().getDrawable(R.drawable.arrow_directional));
        }

        // South East
        if (accelY > 0.1f && accelX < -0.1f){

            arrow_se.setBackground(getResources().getDrawable(R.drawable.arrow_directional_on));
        }
        else
        {
            arrow_se.setBackground(getResources().getDrawable(R.drawable.arrow_directional));
        }

    }
}