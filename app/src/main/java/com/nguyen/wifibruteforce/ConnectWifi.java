package com.nguyen.wifibruteforce;


public class ConnectWifi {
    private static final int MIN_STRING_LENGTH = 8;
    private static int MAX_STRING_LENGTH = 63;   //user can change (63 is limit for wifi password)

    //try to make char array but wrong typing, " instead of ' :))
    String[] passCharsString =
            {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    String[] passCharsExString = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "+", "=", "~", "`", "[",
            "]", "{", "}", "|", "\\", ":", ";", "\'", "\"", "<", ">", ".", "?", "/"}; //special characters


    private char[] initPass(int numberOfChars) {
        String init = "aaaaaaaa";
        for (int i = init.length(); i < numberOfChars; i++) {
            StringBuilder s = new StringBuilder(init);
            s.append("a");
            init = s.toString();
        }
        return init.toCharArray();
    }

    private char[] charArrFromStringArr(String[] stringArr) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars;
        for (String s : stringArr) {
            stringBuilder.append(s);

        }
        chars = stringBuilder.toString().toCharArray();
        return chars;
    }

    public char[] getPassChars() {
        return charArrFromStringArr(passCharsString);
    }

    private void changeOneChar(char oneCharOfPass, int position) {
        char[] c = getPassChars();
        for (int i = 0; i < passCharsString.length; i++) {
            oneCharOfPass = c[i];
        }
    }

    public void methodBruteforce() {
        String pass = "00000000";   //8 characters
        char[] passChars = pass.toCharArray();

//        MAX_STRING_LENGTH = 30; //default
//        for (int i = MIN_STRING_LENGTH; i < MAX_STRING_LENGTH; i++) {
//            for (int j = 0; j < passChars.length; j++) {
//                char temp = passChars[j];
//
//            }
//        }

        for (int i = 0; i < pass.length(); i++) {

        }

    }
}
