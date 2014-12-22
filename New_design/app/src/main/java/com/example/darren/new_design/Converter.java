package com.example.darren.new_design;

import java.lang.*;

public class Converter {
    public static int PRINTABLE_ASCII_MIN = 0x20; // ' '
    public static int PRINTABLE_ASCII_MAX = 0x7E; // '~'

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

    // Bytes to Unsigned Long (Most Significant Bit)
    public static long BytesToUnsignedLong_MSB(byte[] data, int offset, int length) {
        long value = 0;
        for (int i = 0; i < length; i++)
        {
            value += (long) data[i + offset] << 8;
        }
        return value;
    }

    // Bytes to Unsigned Long (Least Significant Bit)
    public static long BytesToUnsignedlong_LSB(byte[] data, int offset, int length) {
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
}
