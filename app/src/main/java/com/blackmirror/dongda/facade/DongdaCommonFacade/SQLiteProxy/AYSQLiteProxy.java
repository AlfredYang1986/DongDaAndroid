package com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alfredyang on 27/05/2017.
 */
public class AYSQLiteProxy extends SQLiteOpenHelper {
    private static final String DB_NAME = "dongda.sqlite";
    private static final int VERSION = 1;

    private static final String TABLE_USER_PROFILE = "user_profile";
    private static final String COLUM_USER_PROFILE_INDEX = "index";
    private static final String COLUM_USER_PROFILE_IS_CURRENT = "is_current";
    private static final String COLUM_USER_PROFILE_USER_ID = "user_id";
    private static final String COLUM_USER_PROFILE_AUTH_TOKEN = "auth_token";
    private static final String COLUM_USER_PROFILE_SCREEN_NAME = "screen_name";
    private static final String COLUM_USER_PROFILE_SCREEN_PHOTO = "screen_photo";

    public AYSQLiteProxy(Context context) {
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

    public long insertProfile(AYDaoUserProfile p) {
        ContentValues cv = new ContentValues();
        cv.put(COLUM_USER_PROFILE_IS_CURRENT, p.getIs_current());
        cv.put(COLUM_USER_PROFILE_SCREEN_NAME, p.getScreen_name());
        cv.put(COLUM_USER_PROFILE_SCREEN_PHOTO, p.getScreen_photo());
        cv.put(COLUM_USER_PROFILE_USER_ID, p.getUser_id());
        cv.put(COLUM_USER_PROFILE_AUTH_TOKEN, p.getAuth_token());
        return getWritableDatabase().insert(TABLE_USER_PROFILE, null, cv);
    }

    public long updateProfile(AYDaoUserProfile p) {
        String user_id = p.getUser_id();
        deleteProfile(user_id);
        return insertProfile(p);
    }

    public AYDaoUserProfile queryProfile(String user_id) {
        //execSQL("select * from user_profile where user_id=" + user_id);
        Cursor c = getReadableDatabase().query(TABLE_USER_PROFILE, null,
                COLUM_USER_PROFILE_USER_ID + "=?", new String[] {user_id}, null, null, null);
        c.moveToFirst();
        return cursor2Profile(c);
    }

    public long deleteProfile(String user_id) {
        return getWritableDatabase().delete(TABLE_USER_PROFILE, COLUM_USER_PROFILE_USER_ID + "=?", new String[] {user_id});
    }

    public AYDaoUserProfile currentProfile() {
        Cursor c = getReadableDatabase().query(TABLE_USER_PROFILE, null,
                COLUM_USER_PROFILE_IS_CURRENT + "=?", new String[] {"1"}, null, null, null);
        c.moveToFirst();
        return cursor2Profile(c);
    }

    public long resetCurrentUser(AYDaoUserProfile p) {
        getWritableDatabase().delete(TABLE_USER_PROFILE, COLUM_USER_PROFILE_IS_CURRENT + "=?", new String[] {"1"});
        return insertProfile(p);
    }

    protected AYDaoUserProfile cursor2Profile(Cursor c) {
        AYDaoUserProfile result = null;
        if (!(c.isBeforeFirst() || c.isAfterLast())) {
            AYDaoUserProfile tmp = new AYDaoUserProfile();
            tmp.setIs_current(c.getInt(c.getColumnIndex(COLUM_USER_PROFILE_IS_CURRENT)));
            tmp.setUser_id(c.getString(c.getColumnIndex(COLUM_USER_PROFILE_USER_ID)));
            tmp.setAuth_token(c.getString(c.getColumnIndex(COLUM_USER_PROFILE_AUTH_TOKEN)));
            tmp.setScreen_name(c.getString(c.getColumnIndex(COLUM_USER_PROFILE_SCREEN_NAME)));
            tmp.setScreen_photo(c.getString(c.getColumnIndex(COLUM_USER_PROFILE_SCREEN_PHOTO)));
            result = tmp;
        }
        return result;
    }
}
