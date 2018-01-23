package hdcz.com.app.greenland1.picture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by guyuqiang on 2018/1/11.15:25
 */

public class ImageHelper {
    //将位图转换为二进制
    public byte[] bitmapToBytes(Bitmap bitmap) {
        if(bitmap==null||"".equals(bitmap)||"null".equals(bitmap)){
            return new byte[0];
        }else {
            //获得大小
            int size = bitmap.getWidth() * bitmap.getHeight() * 4;
            //创建一个字节数组输出流，大小为size
            ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
            try {
                //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                //将字节数组输出流转化为字节数组
                byte[] imagedata = bos.toByteArray();
                return imagedata;
            } catch (Exception e) {
                return new byte[0];
            } finally {
                try {
                    bitmap.recycle();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //字节数组转换为位图
    public Bitmap bolbToBitmap(byte[] bytes) {
        Bitmap imagebitmap = null;
        if (bytes != null) {
            //字节数组转换为位图
            imagebitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {

        }
        return imagebitmap;
    }
}
