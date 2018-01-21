package hdcz.com.app.greenland1.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hdcz.com.app.greenland1.bean.AssetInformationBean;

/**
 * Created by guyuqiang on 2018/1/14.18:53
 */

public class HistoryAssetInfoDao {
    //存入数据
    public void saveAsset(AssetInformationBean ab, SQLiteDatabase db) {
        String sql = "insert into hdcz_serachasset (asset_code,asset_name,asset_usename,asset_type,asset_spex,asset_unit,asset_bedepart,asset_uselocation,asset_savelocation,asset_status,asset_pandstatus,asset_deffind1) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        db.execSQL(sql, new String[]{ab.getAsset_code(), ab.getAsset_name(), ab.getAsset_usename(), ab.getAsset_type(), ab.getAsset_spex(), ab.getAsset_unit(), ab.getAsset_belongdepart(), ab.getAsset_uselocation(), ab.getAsset_savelocation(), ab.getAsset_status(), ab.getAsset_pandstatus(), ab.getAsset_deffind1()});
    }

    //根据搜索值查询数据
    public List<AssetInformationBean> getAssetAll(String serachvalues, SQLiteDatabase db) {
        String sql = "";
        List<AssetInformationBean> list = new ArrayList<>();
        Cursor cursor = null;
        if ("".equals(serachvalues) || serachvalues == null || "null".equals(serachvalues)) {
            sql = "select * from hdcz_serachasset ";
            cursor = db.rawQuery(sql, new String[]{});
        } else {
            sql = "select * from hdcz_serachasset where asset_code = ? or asset_name = ? or asset_bedepart = ? or asset_usename = ?";
            cursor = db.rawQuery(sql, new String[]{serachvalues, serachvalues, serachvalues, serachvalues});
        }
        while (cursor.moveToNext()) {
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
            list.add(ab);
        }
        return list;
    }

    //获取数据的数量
    public Integer getDataCount(SQLiteDatabase db) {
        String sql = "select * from hdcz_serachasset ";
        Cursor cursor = db.rawQuery(sql, new String[]{});
        int num = 0;
        if (cursor.moveToFirst()) {
            num = cursor.getCount();
        }
        return num ;
    }
    //删除数据
    public  void  deleteData(SQLiteDatabase db){
        String slq = "DELETE FROM hdcz_serachasset";
        db.execSQL(slq);
    }
}
