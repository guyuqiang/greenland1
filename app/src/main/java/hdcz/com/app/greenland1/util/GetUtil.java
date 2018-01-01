package hdcz.com.app.greenland1.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by guyuqiang on 2017/12/30.17:58
 */

public class GetUtil {
    public static HttpURLConnection GetConn(String path){
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(path);
            try {
                connection = (HttpURLConnection) url.openConnection();
                //设置请求方式,请求超时信息
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
