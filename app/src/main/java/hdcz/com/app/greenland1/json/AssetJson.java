package hdcz.com.app.greenland1.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hdcz.com.app.greenland1.bean.AssetInformationBean;
import hdcz.com.app.greenland1.bean.CheckInformationBean;

/**
 * Created by guyuqiang on 2018/1/4.14:13
 */

public class AssetJson {
    public AssetJson(){
    }
    //json数据转换成string 对象数据
    public List<AssetInformationBean> getAssetByJson(String json,String pandcode,String pdstatus){
        List<AssetInformationBean> assetlist = new ArrayList<AssetInformationBean>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                AssetInformationBean asset = new AssetInformationBean();
                asset.setAsset_name(jsonObject.getString("资产名称"));
                asset.setAsset_code(jsonObject.getString("资产编码"));
                asset.setAsset_usename(jsonObject.getString("使用人"));
                asset.setAsset_type(jsonObject.getString("资产分类"));
                asset.setAsset_spex(jsonObject.getString("规格型号"));
                asset.setAsset_unit(jsonObject.getString("计量单位"));
                asset.setAsset_belongdepart(jsonObject.getString("所属部门"));
                asset.setAsset_savelocation(jsonObject.getString("存放地点"));
                asset.setAsset_uselocation(jsonObject.getString("使用地点"));
                asset.setAsset_deffind1(pandcode);
                asset.setAsset_pandstatus(pdstatus);
                assetlist.add(asset);
            }
        }catch (Exception e){
            e.printStackTrace();
    }
        return assetlist;
    }
    public List<CheckInformationBean> getCheckinformationByJson(String json){
        List<CheckInformationBean> list = new ArrayList<CheckInformationBean>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                CheckInformationBean checkInformationBean = new CheckInformationBean();
                checkInformationBean.setCode(jsonObject.getString("盘点编码"));
                checkInformationBean.setPdlx(jsonObject.getString("盘点类型"));
                checkInformationBean.setFqr(jsonObject.getString("发起人"));
                checkInformationBean.setFqsj(jsonObject.getString("申请时间"));
                checkInformationBean.setPdsj(jsonObject.getString("盘点时间"));
                list.add(checkInformationBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
