package hdcz.com.app.greenland1.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.internal.bind.SqlDateTypeAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hdcz.com.app.greenland1.bean.AssetInformationBean;
import hdcz.com.app.greenland1.picture.ImageHelper;
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
        String sql = "insert into hdcz_assetpand (asset_code,asset_name,asset_usename,asset_type,asset_spex,asset_unit,asset_bedepart,asset_uselocation,asset_savelocation,asset_status,asset_pandstatus,asset_deffind1) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        db.execSQL(sql,new String [] {ab.getAsset_code(),ab.getAsset_name(),ab.getAsset_usename(),ab.getAsset_type(),ab.getAsset_spex(),ab.getAsset_unit(),ab.getAsset_belongdepart(),ab.getAsset_uselocation(),ab.getAsset_savelocation(),ab.getAsset_status(),ab.getAsset_pandstatus(),ab.getAsset_deffind1()});
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
            ab.setAsset_pdphoto(cursor.getBlob(cursor.getColumnIndex("asset_pdphoto")));
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
            ab.setAsset_deffind2(cursor.getString(cursor.getColumnIndex("asset_deffind2")));
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
   public void EditStatus(String pandcode, String assetcode, String pandstatus, String assetstatus, Bitmap pictiure , SQLiteDatabase db){
       //获取当前时间
       Date date = new Date(System.currentTimeMillis());
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       String date1 = sdf.format(date);
       //获取图片字节数组
       ImageHelper imageHelper = new ImageHelper();
       byte [] imagedata = imageHelper.bitmapToBytes(pictiure);
       ContentValues contentValues = new ContentValues();
       contentValues.put("asset_pandstatus",pandstatus);
       contentValues.put("asset_status",assetstatus);
       contentValues.put("asset_deffind2",date1);
       contentValues.put("asset_pdphoto",imagedata);
       String tablename = "hdcz_assetpand";
       String where = "asset_deffind1 = ? and asset_code = ?";
       String [] string = {pandcode,assetcode};
       db.update(tablename,contentValues,where,string);
//        String sql = "update hdcz_assetpand set asset_pandstatus = ? ,asset_status=?,asset_deffind2=?,asset_pdphoto=?  where asset_deffind1 =? and asset_code = ?";
//        db.execSQL(sql,new String []{pandstatus,assetstatus,date1,pandcode,assetcode});
   }
   //根据盘点编码删除数据
    public  void DeleteAsset(String pandcode,SQLiteDatabase db){
       String sql = "DELETE FROM hdcz_assetpand WHERE asset_deffind1 = ? ";
       db.execSQL(sql,new String [] {pandcode});
    }
    //根据盘点编码和资产编码把此条资产从已盘点中移除重新盘点
    public void deletAssetFromYpand(String pandcode,String assetcode,SQLiteDatabase db){
        String sql ="update hdcz_assetpand set asset_pandstatus = ? ,asset_status=?,asset_deffind2=?  where asset_deffind1 =? and asset_code = ?";
        db.execSQL(sql,new String[]{"0","null","null",pandcode,assetcode});
    }
}
