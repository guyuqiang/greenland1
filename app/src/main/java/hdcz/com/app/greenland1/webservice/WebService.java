package hdcz.com.app.greenland1.webservice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import hdcz.com.app.greenland1.bean.AssetInformationBean;
import hdcz.com.app.greenland1.dao.AssetInformationDao;
import hdcz.com.app.greenland1.json.AssetJson;
import hdcz.com.app.greenland1.util.GetDbUtil;
import hdcz.com.app.greenland1.util.ReturnMessage;

/**
 * Created by guyuqiang on 2018/1/4.11:44
 */

public class WebService {
    //获取资产数据
    public ReturnMessage getAssetByWebservice(Context context,String pandcode){
        ReturnMessage rm = new ReturnMessage();
        //判断数据是否已下载
        SQLiteDatabase db = GetDbUtil.getDb(context,2,"my.db");
        AssetInformationDao assetInformationDao = new AssetInformationDao();
        int num1 = assetInformationDao.getDataCount(pandcode,db);
        if(num1>0){
            rm.setStatus(0);
            rm.setMessage("资料已下载");
        }else{
            String assetJson = "[{\"资产名称\":\"笔记本\",\"资产编码\":\"NCGS-0001\",\"使用人\":\"李玲\",\"资产分类\":\"办公用品\",\"规格型号\":\"戴尔\",\"计量单位\":\"个\",\"所属部门\":\"财务部\",\"存放地点\":\"成都天府新区\",\"使用地点\":\"成都天府新区\"}," +
                    "{\"资产名称\":\"电脑桌\",\"资产编码\":\"NCGS-0002\",\"使用人\":\"王雪\",\"资产分类\":\"办公用品\",\"规格型号\":\"宜居之家\",\"计量单位\":\"张\",\"所属部门\":\"技术部\",\"存放地点\":\"成都锦江区\",\"使用地点\":\"成都锦江区\"}," +
                    "{\"资产名称\":\"汽车\",\"资产编码\":\"NCGS-0003\",\"使用人\":\"杨杰\",\"资产分类\":\"交通工具\",\"规格型号\":\"奥迪A8\",\"计量单位\":\"台\",\"所属部门\":\"办公室\",\"存放地点\":\"成都金牛区\",\"使用地点\":\"成都金牛区\"}," +
                    "{\"资产名称\":\"打印机\",\"资产编码\":\"NCGS-0004\",\"使用人\":\"张超\",\"资产分类\":\"办公用品\",\"规格型号\":\"小米\",\"计量单位\":\"个\",\"所属部门\":\"财务部\",\"存放地点\":\"成都天府新区\",\"使用地点\":\"成都天府新区\"}]";
            //json数据转换
            AssetJson assetJson1 = new AssetJson();
            List<AssetInformationBean> assetlist = assetJson1.getAssetByJson(assetJson,"NCGS-001","0");
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
}
