package com.example.asus.liangpinstore.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mxy2018/01/30
 */
public class MD5Utils {
    private static String cartOrder;

    public static void md5sCart(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            cartOrder = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String getCartInfo(String controlStr, String methodStr, String cartTime) {
        cartOrder = "";
        md5sCart(methodStr + controlStr + cartTime+""+"liangpin");
        md5sCart(cartOrder);
        return cartOrder;
    }
}
