// Copyright © 2015 Darren McNeely. All Rights Reserved.

package com.example.darren.VRA.Sensor;

public class Bluetooth_Converter {
    public static int PRINTABLE_ASCII_MIN = 0x20; // ' '
    public static int PRINTABLE_ASCII_MAX = 0x7E; // '~'

    static float accelRes = (8.0f / 32768.0f) ;	// scale resolutions for the MPU6050 (scale set to ±8g, 16bit sample)
    static float gyro_sensitivity = 131; // Convert to deg/s

    public static boolean isPrintableAscii(int c) {
        return c >= PRINTABLE_ASCII_MIN && c <= PRINTABLE_ASCII_MAX;
    }

    public static String bytesToHex(byte[] data, int offset, int length) {
        if (length <= 0) {
            return "";
        }

        StringBuilder hex = new StringBuilder();
        for (int i = offset; i < offset + length; i++) {
            hex.append(String.format(" %02X", data[i] % 0xFF));
        }
        hex.deleteCharAt(0);
        return hex.toString();
    }

    // Bytes to Unsigned Long (Least Significant Bit)
    public static long BytesToUnsignedLong_LSB(byte[] data, int offset, int length) {
        long value = 0;
        for (int i = 0; i < length; i++)
        {
            value += (long)(data[i + offset] & 0xff) << (8 * ((length -1) -i));
        }
        return value;
    }

    // Get the Int value of a single or range of Bits
    public static int BitToInt(byte data, int offset, int length) {

        int value = 0;
        for (int i = 0; i < length; i++)
        {
            if(((data >> offset+i) & 1) == 1)
                value += Math.pow(2, i);
        }
        return value;
    }

    public static String bytesToAsciiMaybe(byte[] data, int offset, int length) {
        StringBuilder ascii = new StringBuilder();
        boolean zeros = false;
        for (int i = offset; i < offset + length; i++) {
            int c = data[i] & 0xFF;
            if (isPrintableAscii(c)) {
                if (zeros) {
                    return null;
                }
                ascii.append((char) c);
            } else if (c == 0) {
                zeros = true;
            } else {
                return null;
            }
        }
        return ascii.toString();
    }

    public static long timestamp_seconds(byte data[]){
        long MicroSec = BytesToUnsignedLong_LSB(data, 0, 4);
        return MicroSec/1000000;
    }
    public static String battery_voltage(byte data[]){
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

        return "" + BitToInt(data[11], 6, 2) + "." + BitToInt(data[11], 2, 4) + "" +  BitToInt(data[11], 0, 2) +"V";
    }

    public static int PactetNum(byte data[]){
        return data[4] & 0xFF; //Least significant bit - byte 4
    }

    public static float AccelX(byte data[]){
        return ((data[5] << 8) + (data[6] & 0xFF))* accelRes;
    }
    public static float AccelY(byte data[]){
        return ((data[7] << 8) + (data[8] & 0xFF)) * accelRes;
    }
    public static float AccelZ(byte data[]){
        return ((data[9] << 8) + (data[10] & 0xFF)) * accelRes;
    }

    /*public static double AccelXDeg(byte data[]){
        double angleDegX = Math.toDegrees(Math.atan2(AccelY(data), AccelZ(data)));
        // if (angleDegX > 180)    // Change the rotation value of the Accelerometer to +/- 180
        //     angleDegX -= 360;

        return angleDegX;
    }
    public static double AccelYDeg(byte data[]){
        double angleDegY = Math.toDegrees(Math.atan2(AccelZ(data), AccelX(data)));
        //if (angleDegY > 180)    // Change the rotation value of the Accelerometer to +/- 180
        //    angleDegY -= 360;

        return angleDegY;
    }
    public static double AccelZDeg(byte data[]){
        double angleDegZ = Math.toDegrees(Math.atan2(AccelX(data), AccelY(data)));
        //if (angleDegZ > 180)    // Change the rotation value of the Accelerometer to +/- 180
        //    angleDegZ -= 360;

        return angleDegZ;
    }*/


    public static float GyroX(byte data[]){
        return (((data[12] << 8) + (data[13] & 0xFF))/gyro_sensitivity) + 2; // +2 to initialise as 0
    }
    public static float GyroY(byte data[]){
        return (((data[14] << 8) + (data[15] & 0xFF))/gyro_sensitivity);
    }
    public static float GyroZ(byte data[]){
        return (((data[16] << 8) + (data[17] & 0xFF))/gyro_sensitivity);
    }
}
