package com.cskaoyan.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String getMD5(String origin){
        String input = origin + "mall";
        String result = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = input.getBytes();
            byte[] digest = md5.digest(bytes);
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                int i = b & 0x000000FF;
                String s1 = Integer.toHexString(i);
                if(s1.length() == 1){
                    stringBuffer.append("0");
                }
                stringBuffer.append(s1);
            }
            result = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
}
