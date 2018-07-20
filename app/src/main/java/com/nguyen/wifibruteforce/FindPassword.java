package com.nguyen.wifibruteforce;

import android.os.AsyncTask;
import android.util.Log;

class FindPassword {
    private char[] charset;

    public int min; //var added for min char length
    public int max; //var added for max char length
    private String pass; //var added for max char length

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public FindPassword() {
        charset = "12".toCharArray();
        min = 8; //char min start
        max = 9; //char max end
    }

    public void generate(String str, int pos, int length) { //ex: str = ""; pos = 0; length = 2    charset="abc"

        if (length == 0) {
            System.out.println(str);
            setPass(str);
        } else {

            //This if statement resets the char position back to the very first character in the character set ('a'), which makes this a complete solution to an all combinations bruteforce!
            if (pos != 0) {
                pos = 0;
            }

            for (int i = pos; i < charset.length; i++) {
//                System.out.println(i + "///" +(length));
                generate(str + charset[i], i, length - 1);

//                System.out.println(i + "/" +(length));
//                if ((str + charset[i]).equals("GTU")) {
//                    System.out.println("0000000000000000000000+++++++++++++++++++++++");
//                    break;
//                }

//                System.out.println("------------------------------------------------------"+str);
//                System.out.println(pos + "-*-*-" + length);
            }
        }

    }
}
