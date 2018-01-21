package hdcz.com.app.greenland1.webservice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import hdcz.com.app.greenland1.R;
import hdcz.com.app.greenland1.bean.AssetInformationBean;
import hdcz.com.app.greenland1.dao.AssetInformationDao;
import hdcz.com.app.greenland1.fragment.FragementXiaoXi;
import hdcz.com.app.greenland1.json.AssetJson;
import hdcz.com.app.greenland1.sharedpreferences.SharedHelper;
import hdcz.com.app.greenland1.util.GetDbUtil;
import hdcz.com.app.greenland1.util.ReturnMessage;

/**
 * Created by guyuqiang on 2018/1/4.11:44
 */

public class WebService {
    //获取资产数据
    public ReturnMessage getAssetByWebservice(Context context,  String pandcode) {
        ReturnMessage rm = new ReturnMessage();
//        SharedHelper sh = new SharedHelper();
//        Map<String, String> map = sh.getData();
//        final String url = map.get("ljtext");
        //判断数据是否已下载
        SQLiteDatabase db = GetDbUtil.getDb(context, 2, "my.db");
        AssetInformationDao assetInformationDao = new AssetInformationDao();
        int num1 = assetInformationDao.getDataCount(pandcode, db);
        if (num1 > 0) {
            rm.setStatus(0);
            rm.setMessage("资料已下载");
        } else {
//            //获取数据
//            final AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
//                @Override
//                protected String doInBackground(Void... voids) {
//                    assetDown(pandcode, url);
//                    return null;
//                }
//            };
//            task.execute();
            String assetJson = "[{\"资产名称\":\"笔记本\",\"资产编码\":\"NCGS-0001\",\"使用人\":\"李玲\",\"资产分类\":\"办公用品\",\"规格型号\":\"戴尔\",\"计量单位\":\"个\",\"所属部门\":\"财务部\",\"存放地点\":\"成都天府新区\",\"使用地点\":\"成都天府新区\"}," +
                    "{\"资产名称\":\"电脑桌\",\"资产编码\":\"NCGS-0002\",\"使用人\":\"王雪\",\"资产分类\":\"办公用品\",\"规格型号\":\"宜居之家\",\"计量单位\":\"张\",\"所属部门\":\"技术部\",\"存放地点\":\"成都锦江区\",\"使用地点\":\"成都锦江区\"}," +
                    "{\"资产名称\":\"汽车\",\"资产编码\":\"NCGS-0003\",\"使用人\":\"杨杰\",\"资产分类\":\"交通工具\",\"规格型号\":\"奥迪A8\",\"计量单位\":\"台\",\"所属部门\":\"办公室\",\"存放地点\":\"成都金牛区\",\"使用地点\":\"成都金牛区\"}," +
                    "{\"资产名称\":\"打印机\",\"资产编码\":\"NCGS-0004\",\"使用人\":\"张超\",\"资产分类\":\"办公用品\",\"规格型号\":\"小米\",\"计量单位\":\"个\",\"所属部门\":\"财务部\",\"存放地点\":\"成都天府新区\",\"使用地点\":\"成都天府新区\"}]";
            //json数据转换
            AssetJson assetJson1 = new AssetJson();
            List<AssetInformationBean> assetlist = assetJson1.getAssetByJson(assetJson,pandcode,"0");
            int num = 0;
            for (int i = 0; i <assetlist.size() ; i++) {
                //数据存入本地数据库中
                ReturnMessage message = assetInformationDao.saveAssetInformation(assetlist.get(i),db);
                num++;
            }
            if(assetlist.size()==num){
                rm.setStatus(num);
                rm.setMessage("下载完成！");
            }
        }
        return rm;
    }

//    public void assetDown(String pandcode, String url) {
//        //定义获取手机信息的SoapAction与命名空间,作为常量
//        String namespace = "webservices.blog.weaver.com.cn/";
//        //盘点相关信息
//        String pand_methodname = "findCptcapitalBypdmark";
//        String pand_soapAction = "webservices.blog.weaver.com.cn/findpdBypdr";
//        String pand_url = url + "//services/GetInventoryDataService";
//        //指定webservice的命名空间和调用方法
//        SoapObject soapObject = new SoapObject(namespace, pand_methodname);
//        //设置需要传入的参数
//        soapObject.addProperty("pandcode", pandcode);
//        //生成调用webservice方法的soap请求信息，并指定soap版本
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        envelope.bodyOut = soapObject;
//        //设置是否调用的是dotnet开发的webservice
//        envelope.dotNet = true;
//        envelope.setOutputSoapObject(soapObject);
//        //获取网络链接
//        HttpTransportSE transport = new HttpTransportSE(pand_url);
//        try {
//            //调用webservice
//            transport.call(namespace, envelope);
//            //获取返回的数据
//            SoapObject object = (SoapObject) envelope.bodyIn;
//            //获取返回结果
//            assetdown_result = object.getProperty(0).toString();
//            assetdown_status = "1";
//        } catch (HttpResponseException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        handler.sendEmptyMessage(0x001);
//    }
//
//    private Handler handler = new Handler() {
//        public void handleMessage(Message message) {
//            switch (message.what) {
//                case 0x001:
//                    if (Integer.parseInt(assetdown_status) == 1) {
//                        //json数据转换
//                        AssetJson assetJson1 = new AssetJson();
//                        List<AssetInformationBean> assetlist = assetJson1.getAssetByJson(assetdown_result, pandcode, "0");
//                        int num = 0;
//                        for (int i = 0; i < assetlist.size(); i++) {
//                            //数据存入本地数据库中
//                            SQLiteDatabase db = GetDbUtil.getDb(context, 2, "my.db");
//                            AssetInformationDao assetInformationDao = new AssetInformationDao();
//                            ReturnMessage message1 = assetInformationDao.saveAssetInformation(assetlist.get(i), db);
//                            num++;
//                        }
//                        if (assetlist.size() == num) {
//                            rm.setStatus(num);
//                            rm.setMessage("下载完成！");
//                        }
//                    }
//                    if (Integer.parseInt(assetdown_status) == 0) {
//                        Toast.makeText(context, "获取数据失败", Toast.LENGTH_SHORT).show();
//                    }
//            }
//        }
//    };
}
