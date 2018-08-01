package com.nguyen.wifibruteforce;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

class FindPassword {
    private char[] charset;
    public int min; //var added for min char length
    public int max; //var added for max char length
    public ArrayList<String> arrBFTemp; //5000 results of generate()

    private int countList = 0;

    //TODO: make getter setter

    public FindPassword() {
        charset = "abc".toCharArray();
        //        charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
//        charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_+=~`[]{}|\\:;\"'<>,.?/ ".toCharArray();
        min = 3; //char min start
        max = 5; //char max end (max - 1)
        arrBFTemp = new ArrayList<>();
    }

    public void generate(String str, int pos, int length) {
        if (countList == 50) {
            Log.d("classs", "str: " + str);
            Log.d("classs", "pos: " + pos);
            Log.d("classs", "length: " + length);
            return;
        }
        if (length == 0) {
            System.out.println(str);
            arrBFTemp.add(str);
            countList += 1;
        } else {

            //This if statement resets the char position back to the very first character in the character set ('a'), which makes this a complete solution to an all combinations bruteforce!
            if (pos != 0) {
                pos = 0;
            }

            for (int i = pos; i < charset.length; i++) {

//                System.out.println(i + "///" +(length));
                generate(str + charset[i], i, length - 1);

            }
        }

    }
}
