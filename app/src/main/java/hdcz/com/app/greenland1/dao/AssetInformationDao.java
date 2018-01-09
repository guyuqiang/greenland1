package hdcz.com.app.greenland1.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.internal.bind.SqlDateTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import hdcz.com.app.greenland1.bean.AssetInformationBean;
import hdcz.com.app.greenland1.util.ReturnMessage;

/**
 * Created by guyuqiang on 2018/1/4.10:31
 */

public class AssetInformationDao {
    public AssetInformationDao (){
    }
    //存储数据
    public ReturnMessage saveAssetInformation(AssetInformationBean ab, SQLiteDatabase db){
        ReturnMessage message = new ReturnMessage();
        String sql = "insert into hdcz_assetpand (asset_code,asset_name,asset_wpdphoto,asset_pdphoto,asset_usename,asset_type,asset_spex,asset_unit,asset_bedepart,asset_uselocation,asset_savelocation,asset_status,asset_pandstatus,asset_deffind1) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        db.execSQL(sql,new String [] {ab.getAsset_code(),ab.getAsset_name(),ab.getAsset_wpdphoto(),ab.getAsset_pdphoto(),ab.getAsset_usename(),ab.getAsset_type(),ab.getAsset_spex(),ab.getAsset_unit(),ab.getAsset_belongdepart(),ab.getAsset_uselocation(),ab.getAsset_savelocation(),ab.getAsset_status(),ab.getAsset_pandstatus(),ab.getAsset_deffind1()});
        return message;
    }
    //根据盘点编码和盘点状态查询所有数据
    public List<AssetInformationBean> getAssetByPdcode(String pdcode,String pdstatus,SQLiteDatabase db){
        List<AssetInformationBean> assetlist = new ArrayList<AssetInformationBean>();
        String sql = "select * from hdcz_assetpand where asset_deffind1 = ? and asset_pandstatus = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{pdcode,pdstatus});
        while (cursor.moveToNext()){
            AssetInformationBean ab = new AssetInformationBean();
            ab.setAsset_code(cursor.getString(cursor.getColumnIndex("asset_code")));
            ab.setAsset_name(cursor.getString(cursor.getColumnIndex("asset_name")));
            ab.setAsset_wpdphoto(cursor.getString(cursor.getColumnIndex("asset_wpdphoto")));
            ab.setAsset_pdphoto(cursor.getString(cursor.getColumnIndex("asset_pdphoto")));
            ab.setAsset_usename(cursor.getString(cursor.getColumnIndex("asset_usename")));
            ab.setAsset_type(cursor.getString(cursor.getColumnIndex("asset_type")));
            ab.setAsset_spex(cursor.getString(cursor.getColumnIndex("asset_spex")));
            ab.setAsset_unit(cursor.getString(cursor.getColumnIndex("asset_unit")));
            ab.setAsset_belongdepart(cursor.getString(cursor.getColumnIndex("asset_bedepart")));
            ab.setAsset_uselocation(cursor.getString(cursor.getColumnIndex("asset_uselocation")));
            ab.setAsset_savelocation(cursor.getString(cursor.getColumnIndex("asset_savelocation")));
            ab.setAsset_status(cursor.getString(cursor.getColumnIndex("asset_status")));
            ab.setAsset_pandstatus(cursor.getString(cursor.getColumnIndex("asset_pandstatus")));
            ab.setAsset_deffind1(cursor.getString(cursor.getColumnIndex("asset_deffind1")));
            assetlist.add(ab);
        }
        cursor.close();
        return assetlist;
    }
    //根据盘点编码和盘点状态获取数据的数量
    public String getDataCount(String pandcode, String pandstatus,SQLiteDatabase db){
        String sql = "select * from hdcz_assetpand where asset_deffind1 = ? and asset_pandstatus = ?";
        Cursor cursor = db.rawQuery(sql,new String [] {pandcode,pandstatus});
        int num = 0;
        if(cursor.moveToFirst()){
            num = cursor.getCount();
        }
        return num+"";
    }
    //根据盘点编码获取数据的数量
    public Integer getDataCount(String pandcode,SQLiteDatabase db){
        String sql = "select * from hdcz_assetpand where asset_deffind1 = ? ";
        Cursor cursor = db.rawQuery(sql,new String [] {pandcode});
        int num = 0;
        if(cursor.moveToFirst()){
            num = cursor.getCount();
        }
        return num;
    }
    //根据盘点编码和资产编码修改此条资产的盘点状态和资产状态
   public void EditStatus(String pandcode,String assetcode,String pandstatus,String assetstatus,SQLiteDatabase db){
       Log.e("编辑值-------------:",pandcode+assetcode+pandstatus+assetstatus);
        String sql = "update hdcz_assetpand set asset_pandstatus = ? ,asset_status=?  where asset_deffind1 =? and asset_code = ?";
        db.execSQL(sql,new String []{pandstatus,assetstatus,pandcode,assetcode});
   }
}
