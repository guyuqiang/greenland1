package hdcz.com.app.greenland1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by guyuqiang on 2017/12/29.13:12
 */

public class DBOpenHelper extends SQLiteOpenHelper{
   public DBOpenHelper (Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
       super(context,name,null,version);
   }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE person (id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),password VARCHAR(64))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("");
    }
}
