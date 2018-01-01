package hdcz.com.app.greenland1.dao;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import hdcz.com.app.greenland1.util.GetUtil;
import hdcz.com.app.greenland1.util.SteamTool;

/**
 * Created by guyuqiang on 2017/12/30.18:19
 */

public class LoginDao {
    public String logIn(String url) {
        String message = "";
        //获取请求
        HttpURLConnection con = GetUtil.GetConn(url);
        try {
            if (con.getResponseCode() != 200) {
                InputStream is = con.getInputStream();
                try {
                    message = SteamTool.read(is);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}
