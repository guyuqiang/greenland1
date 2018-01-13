package hdcz.com.app.greenland1.webservice;

import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


/**
 * Created by guyuqiang on 2018/1/12.11:27
 */

public class WebServiceDao {
    //定义获取手机信息的SoapAction与命名空间,作为常量
    String namespace = "webservices.blog.weaver.com.cn/";
    //登陆相关信息
    String log_methodname = "checkUserBynameAndPassword";
    String log_logurl = "http://125.69.90.196:10089//services/GetInventoryDataService";
    String log_soapAction = "webservices.blog.weaver.com.cn/checkUserBynameAndPassword";
    public String userLog(String name,String password,String url){
        String result = "";
        //指定webservice的命名空间和调用方法
        SoapObject soapObject = new SoapObject(namespace,log_methodname);
        //设置需要传入的参数
        soapObject.addProperty("username",name);
        soapObject.addProperty("password",password);
        //生成调用webservice方法的soap请求信息，并指定soap版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        //设置是否调用的是dotnet开发的webservice
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);
        //获取网络链接
        HttpTransportSE transport = new HttpTransportSE(log_logurl);
        try {
            //调用webservice
            transport.call(namespace,envelope);
        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        //获取返回结果
        result = object.getProperty(0).toString();
        Log.e("-----------++++++++",result);
        return result;
    }
}
