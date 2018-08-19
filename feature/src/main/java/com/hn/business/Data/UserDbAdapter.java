package com.hn.business.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class UserDbAdapter {
    public static final String COL_ID = "_id";
    public static final String COL_USERCODE = "UserCode";
    public static final String COL_USERNAME = "UserName";
    public static final String COL_NICKNAME = "NickName";

    private static final String TAG = "UserDbAdapter";
    private static final String DATABASE_NAME = "dba_plan";
    private static final String TABLE_NAME = "tbl_user";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "CREATE TABLE if not exists " + TABLE_NAME + "(" +
            COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
            COL_USERCODE + " TEXT," +
            COL_USERNAME + " TEXT," +
            COL_NICKNAME + " TEXT);";
    private final Context mCtx;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    //region 数据库相关操作方法
    public UserDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }
    //endregion

    public void open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    //region 数据的CRUD操作
    public void createUserInfo(UserInfoEntity item) {
        ContentValues values = new ContentValues();
        values.put(COL_USERCODE, item.getUserCode());
        values.put(COL_USERNAME, item.getUserName());
        values.put(COL_NICKNAME, item.getNickName());
        mDb.insert(TABLE_NAME, null, values);
    }

    //endregion

//    public UserInfoEntity fetchUserInfoById(int id) {
//        Cursor cursor = mDb.query(TABLE_NAME, new String[]{COL_ID, COL_USERCODE, COL_USERNAME, COL_NICKNAME}
//                , COL_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//
//        UserInfoEntity item = new UserInfoEntity(
//                cursor.getInt(cursor.getColumnIndex(COL_ID))
//                , cursor.getString(cursor.getColumnIndex(COL_USERCODE))
//                , cursor.getString(cursor.getColumnIndex(COL_USERNAME))
//                , cursor.getString(cursor.getColumnIndex(COL_NICKNAME))
//        );
//        cursor.close();
//
//        return item;
//    }

    public List<UserInfoEntity> fetchAllUserInfo() {
        //获取所有的数据
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{COL_ID, COL_USERCODE, COL_USERNAME, COL_NICKNAME}
                , null, null, null, null, null, null);

        List<UserInfoEntity> items = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
//                UserInfoEntity item = new UserInfoEntity(
//                        cursor.getInt(cursor.getColumnIndex(COL_ID))
//                        , cursor.getString(cursor.getColumnIndex(COL_USERCODE))
//                        , cursor.getString(cursor.getColumnIndex(COL_USERNAME))
//                        , cursor.getString(cursor.getColumnIndex(COL_NICKNAME))
//                );
//                items.add(item);
            }
            cursor.close();
        }

        return items;
    }

    public void UpdateUserInfo(UserInfoEntity item) {
        ContentValues values = new ContentValues();
        values.put(COL_USERCODE, item.getUserCode());
        values.put(COL_USERNAME, item.getUserName());
        values.put(COL_NICKNAME, item.getNickName());
        mDb.update(TABLE_NAME, values, COL_ID + "=?", new String[]{String.valueOf(item.getId())});
    }

    public void deleteProjectPlanById(int UserId) {
        mDb.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(UserId)});
    }

    //region 内部数据库操作类
    private class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version" + oldVersion + " to " + newVersion + ".");
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//            onCreate(db);
            // 使用for实现跨版本升级数据库
            for (int i = oldVersion; i < newVersion; i++) {
                switch (i) {

                    default:
                        break;
                }
            }
        }
    }
    //endregion
}
