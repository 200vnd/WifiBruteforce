package com.nguyen.wifibruteforce;


public class ConnectWifi {
    private static final int MIN_STRING_LENGTH = 8;
    private static int MAX_STRING_LENGTH = 63;   //user can change (63 is limit for wifi password)

    private char[] charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    private char[] charsetEx =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_+=~`[]{}|\\:;\"'<>,.?/ ".toCharArray();


    private char[] initPass(int numberOfChars) {
        String init = "aaaaaaaa";
        for (int i = init.length(); i < numberOfChars; i++) {
            StringBuilder s = new StringBuilder(init);
            s.append("a");
            init = s.toString();
        }
        return init.toCharArray();
    }


    private void changeOneChar(char oneCharOfPass) {
//        char[] c = getCharSet();
//        for (int i = 0; i < c.length; i++) {
//            oneCharOfPass = c[i];
    }


    public void methodBruteforce() {
        char[] pass = initPass(8);  //8 characters


//        MAX_STRING_LENGTH = 30; //default
//        for (int i = MIN_STRING_LENGTH; i < MAX_STRING_LENGTH; i++) {
//            for (int j = 0; j < passChars.length; j++) {
//                char temp = passChars[j];
//
//            }
//        }

        String testPass = "aaaaaa9a";

        for (int i = 0; i < pass.length; i++) {
            changeOneChar(pass[i]);
            if (String.valueOf(pass).equals(testPass)) {
                System.out.println(i);
            }
        }

    }
}
