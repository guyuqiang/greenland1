package hdcz.com.app.greenland1.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import hdcz.com.app.greenland1.db.DBOpenHelper;

/**
 * Created by guyuqiang on 2018/1/4.14:49
 */

public class GetDbUtil {
    public static  SQLiteDatabase getDb(Context context,int version,String datebase){
      DBOpenHelper dbOpenHelper = new DBOpenHelper(context,datebase,null,5);
     SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
     return  db;
    }
}
