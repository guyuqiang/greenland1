package hdcz.com.app.greenland1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by guyuqiang on 2017/12/29.13:12
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String createperson = "CREATE TABLE person (id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),password VARCHAR(64))";
    private static final String createassetpand = "CREATE TABLE hdcz_assetpand(id INTEGER PRIMARY KEY AUTOINCREMENT,asset_code TEXT(64)," +
            " asset_name TEXT(64)," +
            "asset_wpdphoto BLOB," +
            "asset_pdphoto BLOB," +
            " asset_usename TEXT(64)," +
            " asset_type TEXT(64)," +
            "asset_spex TEXT(64)," +
            " asset_unit TEXT(64)," +
            " asset_bedepart TEXT(64)," +
            "asset_uselocation TEXT(128)," +
            "asset_savelocation TEXT(128)," +
            "asset_status TEXT(64)," +
            " asset_pandstatus TEXT(64)," +
            " asset_deffind1 TEXT(64)," +
            " asset_deffind2 TEXT(64))";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createperson);
        db.execSQL(createassetpand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists person");
        db.execSQL("drop table if exists hdcz_assetpand");
        onCreate(db);
    }
}
