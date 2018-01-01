package hdcz.com.app.greenland1.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by guyuqiang on 2017/12/30.17:33
 */

public class SteamTool {
    //从流中读取数据
    public static String  read (InputStream instream) throws Exception{
        String data = "";
        // 创建字节输出流对象
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        // 定义缓冲区
        byte [] buffer = new byte[1024];
        // 定义读取的长度
        int len = 0;
        // 按照缓冲区的大小，循环读取
        while ((len=instream.read(buffer))!=-1){
            // 根据读取的长度写入到os对象中
            outstream.write(buffer,0,len);
        }
        instream.close();
        data = new String(outstream.toByteArray());
        return data;
    }
}
