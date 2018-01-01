package hdcz.com.app.greenland1.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by guyuqiang on 2017/12/29.10:02
 */

public class MD5 {
    public static String getMD5(String context){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(context.getBytes());
            return getHashString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String getHashString(MessageDigest digest){
        StringBuilder builder = new StringBuilder();
        for(byte b:digest.digest()){
            builder.append(Integer.toHexString((b>>4)&0xf));
            builder.append(Integer.toHexString(b&0xf));
        }
        return builder.toString();
    }
}
