package hdcz.com.app.greenland1.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import hdcz.com.app.greenland1.db.DBOpenHelper;

/**
 * Created by guyuqiang on 2017/12/29.13:59
 */

public class DBUtil {
    private Context mcontext;
    public DBUtil(Context context){
        this.mcontext=context;
    }
    DBOpenHelper dbOpenHelper = new DBOpenHelper(mcontext,"my_db",null,1);
    //插入数据
   public void saveData(String sql){
      SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
      db.execSQL(sql);
   }
   //修改数据
    public void editData(String sql){
       SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
       db.execSQL(sql);
    }
    //删除数据
    public void  deleteData(String sql){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.execSQL(sql);
    }
    //查询数据条数
    public long getCount(String tablename){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT * FROM "+tablename,null);
        long result = cursor.getCount();
        cursor.close();
        return result;
    }
}
