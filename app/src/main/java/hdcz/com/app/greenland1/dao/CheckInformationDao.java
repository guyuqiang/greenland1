package hdcz.com.app.greenland1.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hdcz.com.app.greenland1.bean.CheckInformationBean;

/**
 * Created by guyuqiang on 2018/1/14.10:43
 */

public class CheckInformationDao {
    //存入数据
    public void saveCheckInformation(CheckInformationBean checkInformationBean, SQLiteDatabase db){
        String slq = "insert into hdcz_checkinformation (pand_code,pand_fqr,pand_fqsj,pand_lx,pand_pdsj,pand_panduser,pand_deffind1,pand_deffind2) values(?,?,?,?,?,?,?,?)";
        db.execSQL(slq,new String []{checkInformationBean.getCode(),checkInformationBean.getFqr(),checkInformationBean.getFqsj(),checkInformationBean.getPdlx(),checkInformationBean.getPdsj(),checkInformationBean.getPdr(),checkInformationBean.getDeffind1(),checkInformationBean.getDeffind2()});
    }
    //根据user查询所有数据
    public List<CheckInformationBean> getCheckAllByUser(String user,SQLiteDatabase db){
        List<CheckInformationBean> list = new ArrayList<>();
        String sql = "select * from hdcz_checkinformation where pand_panduser = ?";
        Cursor cursor = db.rawQuery(sql,new String [] {user});
        while (cursor.moveToNext()){
            CheckInformationBean checkInformationBean = new CheckInformationBean();
            checkInformationBean.setCode(cursor.getString(cursor.getColumnIndex("pand_code")));
            checkInformationBean.setFqr(cursor.getString(cursor.getColumnIndex("pand_fqr")));
            checkInformationBean.setFqsj(cursor.getString(cursor.getColumnIndex("pand_fqsj")));
            checkInformationBean.setPdlx(cursor.getString(cursor.getColumnIndex("pand_lx")));
            checkInformationBean.setPdsj(cursor.getString(cursor.getColumnIndex("pand_pdsj")));
            checkInformationBean.setPdr(cursor.getString(cursor.getColumnIndex("pand_panduser")));
            checkInformationBean.setDeffind1(cursor.getString(cursor.getColumnIndex("pand_deffind1")));
            checkInformationBean.setDeffind2(cursor.getString(cursor.getColumnIndex("pand_deffind2")));
            list.add(checkInformationBean);
        }
        return list;
    }
    //根据user和盘点编码删除此条盘点信息
    public void deleteCheckByCodeAndUser(String pandcode,String user,SQLiteDatabase db){
        String sql = "DELETE FROM hdcz_checkinformation WHERE pand_code = ? and pand_panduser = ?";
        db.execSQL(sql,new String [] {pandcode,user});
    }
}
