package hdcz.com.app.greenland1.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import hdcz.com.app.greenland1.bean.PersonBean;
import hdcz.com.app.greenland1.db.DBOpenHelper;

/**
 * Created by guyuqiang on 2017/12/29.15:36
 */

public class PersonDao {
    public PersonDao() {
    }
    //用户增加数据
    public void savePserson(PersonBean personBean,SQLiteDatabase db){
        String sql="insert into person(name, password) values(?,?)";
        db.execSQL(sql,new String []{personBean.getName(),personBean.getPassword()});
    }
    //根据name查询用户数据
    public PersonBean getPersonByName(String name,SQLiteDatabase db){
        PersonBean personBean = new PersonBean();
        String sql ="SELECT * FROM person WHERE name = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{name});
        if(cursor.moveToFirst()){
            personBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
            personBean.setName(cursor.getString(cursor.getColumnIndex("name")));
            personBean.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            cursor.close();
            return personBean;
        }else{
            cursor.close();
            return null;
        }
    }
    //根据用户名更新数据
    public void  updatePersonByName(String name,String password,SQLiteDatabase db){
        String sql = "UPDATE person SET password = ? WHERE name = ?";
        db.execSQL(sql,new String[]{password,name});
    }
    //根据用户名和密码查询数据
    public PersonBean checkPerson(String name,String password,SQLiteDatabase db){
        PersonBean personBean = new PersonBean();
        String sql="SELECT * FROM person WHERE name = ? and password = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{name,password});
        if(cursor.moveToFirst()){
            personBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
            personBean.setName(cursor.getString(cursor.getColumnIndex("name")));
            personBean.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            cursor.close();
            return personBean;
        }else{
            cursor.close();
            return null;
        }
    }

}
