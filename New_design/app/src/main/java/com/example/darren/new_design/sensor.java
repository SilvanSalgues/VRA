package com.example.darren.new_design;

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
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class sensor extends Fragment {

    //private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;

    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    Fragment newFragment;

    float accelX = 0;
    float accelY = 0;
    float accelZ = 0;

    float gyroX = 0;
    float gyroY = 0;
    float gyroZ = 0;

    private boolean bound;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;

    private RFduinoService rfduinoService;

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


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //view = new Cubesurfaceview(getActivity());
        View InputFragmentView = inflater.inflate(R.layout.sensor, container, false);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (savedInstanceState == null){
            newFragment = new object_3D();
            ft.replace(R.id.container_3D, newFragment);
            ft.commit();
        }

        bluetooth_start();

        // TextViews
        deviceInfoText = (TextView) InputFragmentView.findViewById(R.id.deviceInfo);
        timestamp = (TextView) InputFragmentView.findViewById(R.id.timestamp);
        packetnumber = (TextView) InputFragmentView.findViewById(R.id.packetnumber);
        accelData = (TextView) InputFragmentView.findViewById(R.id.accelData);
        batteryVoltage = (TextView) InputFragmentView.findViewById(R.id.batteryVoltage);
        gyroData = (TextView) InputFragmentView.findViewById(R.id.gyroData);

/*
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
*/

        return InputFragmentView;
    }


    private final ServiceConnection rfduinoServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            rfduinoService = ((RFduinoService.LocalBinder) service).getService();
            if (rfduinoService.initialize()) {
                //if (rfduinoService.connect(bluetoothDevice.getAddress())) {
                //}
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
           /* if (RFduinoService.ACTION_CONNECTED.equals(action))
            {

            }
            else if (RFduinoService.ACTION_DISCONNECTED.equals(action))
            {

            }
            else */

            if (RFduinoService.ACTION_DISCONNECTED.equals(action)) {
                onStop();
                onStart();
            }
            if (RFduinoService.ACTION_DATA_AVAILABLE.equals(action))
            {
                addData(intent.getByteArrayExtra(RFduinoService.EXTRA_DATA));
            }
        }
    };



/*
    @Override
    public void onLeScan(BluetoothDevice device, final int rssi, final byte[] scanRecord) {
        bluetoothAdapter.stopLeScan(this);
        bluetoothDevice = device;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                deviceInfoText.setText(BluetoothHelper.getDeviceInfoText(bluetoothDevice, rssi, scanRecord));
                Intent rfduinoIntent = new Intent(getActivity(), RFduinoService.class);
                getActivity().bindService(rfduinoIntent, rfduinoServiceConnection,  getActivity().BIND_AUTO_CREATE);
                bound = true;
            }
        });
    }
*/

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            bluetoothDevice = device;

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    deviceInfoText.setText(BluetoothHelper.getDeviceInfoText(bluetoothDevice, rssi, scanRecord));
                    Intent rfduinoIntent = new Intent(getActivity(), RFduinoService.class);
                    getActivity().bindService(rfduinoIntent, rfduinoServiceConnection,  getActivity().BIND_AUTO_CREATE);
                    bound = true;
                }
            });
        }
    };

    @Override
    public void onStart() {
        super.onStart();

        // Find Device
        //bluetoothAdapter.startLeScan(new UUID[]{RFduinoService.UUID_SERVICE}, sensor.this);
        bluetoothAdapter.startLeScan(leScanCallback);


        //registerReceiver(bluetoothStateReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        getActivity().registerReceiver(rfduinoReceiver, RFduinoService.getIntentFilter());

    }

    @Override
    public void onStop() {                       //When the app is closed, this runs
        super.onStop();

        //bluetoothAdapter.stopLeScan(this);
        //bluetoothAdapter.stopScan(leScanCallback);

        //unregisterReceiver(bluetoothStateReceiver);
        getActivity().unregisterReceiver(rfduinoReceiver);

        if(bound) {     // IF a connection has been made to the RFduino service
            getActivity().unbindService(rfduinoServiceConnection);    // Disconnect RFduino service
            bound = false;                              // Boolean to show if the service is connected
        }

        //bluetoothAdapter.disable();
    }






    private void addData(byte[] data) {

        // Packet size: 18bytes
        //| timestamp 4bytes | packetNumber 1byte | accelData 6bytes | batteryVoltage 1byte | gyroData 6bytes |

        long seconds_passed = MicroSectoSec(Converter.BytesToUnsignedlong_LSB(data, 0, 4));
        timestamp.setText("TimeStamp (4 bytes): " + seconds_passed/ 60 + " minutes " + seconds_passed % 60 + " seconds");
        packetnumber.setText("Packet number (1 bytes): " + Converter.BytesToUnsignedlong_LSB(data, 4, 1));
        batteryVoltage.setText("Battery Voltage (1 bytes): " + battery_voltage(data [11]));

        //Log.d(TAG,"Most significant bit / signed Integer : " + Converter.ByteToSignedInt_MSB(new byte[]{(byte) 125}, 0));
        //Log.d(TAG,"Least significant bit / signed Integer : " + Converter.ByteToSignedInt_LSB(new byte[]{(byte) 125}, 0));

        average(data);
    }

    public void average(byte[] data){

        float accelRes = (8.0f / 32768.0f) ;	// scale resolutions for the MPU6050 (scale set to ±8g, 16bit sample)

        // accelerometer xyz 6-bytes
        final short ax = (short)(Converter.BytesToUnsignedLong_MSB(data, 5, 1)  + Converter.BytesToUnsignedlong_LSB(data, 6, 1));
        accelX = ax * accelRes;
        final short ay = (short)(Converter.BytesToUnsignedLong_MSB(data, 7, 1) + Converter.BytesToUnsignedlong_LSB(data, 8, 1));
        accelY = ay * accelRes;
        final short az = (short)(Converter.BytesToUnsignedLong_MSB(data, 9, 1) + Converter.BytesToUnsignedlong_LSB(data, 10, 1));
        accelZ = az * accelRes;

        accelData.setText("accelData (6 bytes): "
                + "\nX: " + accelX
                + "\nY: " + accelY
                + "\nZ: " + accelZ);


        Get_Gyro_Data(data);



        //Converts the already acquired accelerometer data into 3D euler angles
        //double ACCEL_XANGLE = 57.295*Math.atan((float) ACCEL_YOUT / Math.sqrt(Math.pow((float) ACCEL_ZOUT, 2) + Math.pow((float) ACCEL_XOUT, 2)));
        //double ACCEL_YANGLE = 57.295*Math.atan((float)-ACCEL_XOUT/ Math.sqrt(Math.pow((float)ACCEL_ZOUT,2)+Math.pow((float)ACCEL_YOUT,2)));


        //TextView angle_x = (TextView) findViewById(R.id.angle_x);
        //TextView angle_y = (TextView) findViewById(R.id.angle_y);
        //angle_x.setText("angle_x :" + ACCEL_XANGLE);
        //angle_y.setText("angle_y :" + ACCEL_YANGLE);

        //Log.d(TAG, "ACCEL_XANGLE : " + ACCEL_XANGLE);
        //Log.d(TAG, "ACCEL_YANGLE : " + ACCEL_YANGLE);

        //arrow_direction_statements();
    }

    void Get_Gyro_Data(byte[] data)
    {
        // The Gyroscope gives angular speed or velocity of the device
        float gyro_sensitivity =131;
        // Gyroscope xyz 6-bytes
        final short gx = (short)(Converter.BytesToUnsignedLong_MSB(data, 12, 1)  + Converter.BytesToUnsignedlong_LSB(data, 13, 1));
        gyroX = gx/gyro_sensitivity;
        final short gy = (short)(Converter.BytesToUnsignedLong_MSB(data, 14, 1) + Converter.BytesToUnsignedlong_LSB(data, 15, 1));
        gyroY = gy/gyro_sensitivity;
        final short gz = (short)(Converter.BytesToUnsignedLong_MSB(data, 16, 1) + Converter.BytesToUnsignedlong_LSB(data, 17, 1));
        gyroZ = gz/gyro_sensitivity;

        gyroData.setText("GyroData (6 bytes): "
                + "\nX: " + (int)gyroX
                + "\nY: " + (int)gyroY
                + "\nZ: " + (int)gyroZ);
    }



    public void arrow_direction_statements(){

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
    public String battery_voltage(byte data){
        /* Battery voltage is saved in one byte: 11 | 0011 | 00 -> 3.30V
        * |11|   first 2 bits store mantisa [0-3]
        * |0011| next 4 bits store first digit of exponent [0-9]
        * |00|   last 2 bits store second digit of exponent (0 or 5)
        *        0 if [0-4], 1 if [5-9]
        * example: 11 | 0011 | 00 this is 3.30V
        *          mantisa: 3
        *          exponent first digit:  3
        *          exponent second digit: 0
        */

        String finalvalue;
        finalvalue = "" + Converter.BitToInt(data, 6, 2) + "." + Converter.BitToInt(data, 2, 4) + "" +  Converter.BitToInt(data, 0, 2) +"V";
        return finalvalue;
    }

    public long MicroSectoSec(long microsec){
        return microsec /1000000;
    }

    public void bluetooth_start(){
        // Bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // force enables Bluetooth.

        if (bluetoothAdapter == null)
        {
            Toast.makeText( getActivity(), "Device does not have bluetooth", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        else if (!bluetoothAdapter.isEnabled()) {
            // Force Enable Bluetooth
            bluetoothAdapter.enable();

            // Enable Bluetooth using Request
            //int REQUEST_ENABLE_BT = 1;
            //Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivityForResult(enableIntent, REQUEST_ENABLE_BT);

            Toast.makeText( getActivity(), "Bluetooth Enabled", Toast.LENGTH_SHORT).show();
        }

        mHandler = new Handler();

        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(getActivity(), "ble_not_supported", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(getActivity(), "error_bluetooth_not_supported", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }
    }
}

