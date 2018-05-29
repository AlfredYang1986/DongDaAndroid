package com.blackmirror.dongda.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.blackmirror.dongda.data.model.db.UserInfoDbBean;

/**
 * Created by alfredyang on 27/05/2017.
 */
public class AYSQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "dongda.sqlite";
    private static final int VERSION = 1;
    private static final String TABLE_USER_PROFILE = "user_profile";
    private static final String COLUM_USER_PROFILE_INDEX = "index";
    private static final String COLUM_USER_PROFILE_IS_CURRENT = "is_current";
    private static final String COLUM_USER_PROFILE_USER_ID = "user_id";
    private static final String COLUM_USER_PROFILE_AUTH_TOKEN = "auth_token";
    private static final String COLUM_USER_PROFILE_SCREEN_NAME = "screen_name";
    private static final String COLUM_USER_PROFILE_SCREEN_PHOTO = "screen_photo";

    public AYSQLiteHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the "user_profile" table
        db.execSQL("create table user_profile (" +
                "id integer primary key autoincrement, " +
                "is_current integer, " +
                "user_id varchar(50), " +
                "auth_token varchar(50), " +
                "screen_name varchar(50), " +
                "screen_photo varchar(50) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertProfile(UserInfoDbBean bean) {
        ContentValues cv = new ContentValues();
        cv.put(COLUM_USER_PROFILE_IS_CURRENT, bean.is_current);
        cv.put(COLUM_USER_PROFILE_SCREEN_NAME, bean.screen_name);
        cv.put(COLUM_USER_PROFILE_SCREEN_PHOTO, bean.screen_photo);
        cv.put(COLUM_USER_PROFILE_USER_ID, bean.user_id);
        cv.put(COLUM_USER_PROFILE_AUTH_TOKEN, bean.auth_token);
        return getWritableDatabase().insert(TABLE_USER_PROFILE, null, cv);
    }

    public long updateProfile(UserInfoDbBean bean) {
        String user_id = bean.user_id;
        deleteProfile(user_id);
        return insertProfile(bean);
    }

    public UserInfoDbBean queryProfile(String user_id) {
        //execSQL("select * from user_profile where user_id=" + user_id);
        Cursor c = getReadableDatabase().query(TABLE_USER_PROFILE, null,
                COLUM_USER_PROFILE_USER_ID + "=?", new String[]{user_id}, null, null, null);
        c.moveToFirst();
        return cursor2Profile(c);
    }

    public long deleteProfile(String user_id) {
        return getWritableDatabase().delete(TABLE_USER_PROFILE, COLUM_USER_PROFILE_USER_ID + "=?", new String[]{user_id});
    }

    public UserInfoDbBean currentProfile() {
        Cursor c = getReadableDatabase().query(TABLE_USER_PROFILE, null,
                COLUM_USER_PROFILE_IS_CURRENT + "=?", new String[]{"1"}, null, null, null);
        return cursor2Profile(c);
    }

    public long resetCurrentUser(UserInfoDbBean bean) {
        getWritableDatabase().delete(TABLE_USER_PROFILE, COLUM_USER_PROFILE_IS_CURRENT + "=?", new String[]{"1"});
        return insertProfile(bean);
    }

    protected UserInfoDbBean cursor2Profile(Cursor c) {
        UserInfoDbBean bean = new UserInfoDbBean();
        if (c.moveToNext()) {
            bean.is_current = c.getInt(c.getColumnIndex(COLUM_USER_PROFILE_IS_CURRENT));
            bean.user_id = c.getString(c.getColumnIndex(COLUM_USER_PROFILE_USER_ID));
            bean.auth_token = c.getString(c.getColumnIndex(COLUM_USER_PROFILE_AUTH_TOKEN));
            bean.screen_name = c.getString(c.getColumnIndex(COLUM_USER_PROFILE_SCREEN_NAME));
            bean.screen_photo = c.getString(c.getColumnIndex(COLUM_USER_PROFILE_SCREEN_PHOTO));
        }
        return bean;
    }
}
