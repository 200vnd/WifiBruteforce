package com.nguyen.wifibruteforce;

class FindPassword {
    private char[] charset;

    public int min; //var added for min char length
    public int max; //var added for max char length
    public String pass; //var added for max char length

    public FindPassword() {
        charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        min = 2; //char min start
        max = 4; //char max end
        pass = "";
    }

    public void generate(String str, int pos, int length) { //ex: str = ""; pos = 0; length = 2
//        System.out.println(str.toLowerCase());
        if (str.equals("GTU")) {
            System.out.println("-------------+++++++++++++++++++++++");
//                    break;
        }
//        System.out.println(pos + "---" + length);


        if (length == 0) {
            System.out.println(str);
            pass = str;

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

//    public static void main(String[] args) {
//        FindPassword bruteforce = new FindPassword();
//
//        for (int length = bruteforce.min; length < bruteforce.max; length++) // Change bruteforce.min and bruteforce.max for number of characters to bruteforce.
//            bruteforce.generate("", 0, length); //prepend_string, pos, length
//    }
}
