package com.example.myapplication.mvpnetworklibrary.network.okHttp.utils;

import java.io.FileInputStream;
import java.security.MessageDigest;

public class MD5Utile {

    public static String md5sum(String inStr)
    {
        MessageDigest md5 = null;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e)
        {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++)
        {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * @param fileInputStream
     * @return
     */
    public static String md5sum(FileInputStream fileInputStream)
    {
        byte[] buffer = new byte[1024];
        int numRead = 0;
        MessageDigest md5;
        try
        {

            md5 = MessageDigest.getInstance("MD5");
            while ((numRead = fileInputStream.read(buffer)) > 0)
            {
                md5.update(buffer, 0, numRead);
            }
            fileInputStream.close();
//            return toHexString(md5.digest());
            return MD5(md5.digest());
        } catch (Exception e)
        {
            System.out.println("error");
            return null;
        }
    }

    public final static String MD5(byte[] md)
    {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try
        {
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++)
            {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e)
        {
            return null;
        }
    }
}
