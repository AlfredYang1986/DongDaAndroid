package com.blackmirror.dongda.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import com.blackmirror.dongda.data.model.db.UserInfoDbBean

/**
 * Create By Ruge at 2018-06-13
 */
class AYSQLiteHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Create the "user_profile" table
        db.execSQL("create table user_profile (" +
                "id integer primary key autoincrement, " +
                "is_current integer, " +
                "user_id varchar(50), " +
                "auth_token varchar(50), " +
                "screen_name varchar(50), " +
                "screen_photo varchar(50) )")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    fun insertProfile(bean: UserInfoDbBean): Long {
        val cv = ContentValues()
        cv.put(COLUM_USER_PROFILE_IS_CURRENT, bean.is_current)
        cv.put(COLUM_USER_PROFILE_SCREEN_NAME, bean.screen_name)
        cv.put(COLUM_USER_PROFILE_SCREEN_PHOTO, bean.screen_photo)
        cv.put(COLUM_USER_PROFILE_USER_ID, bean.user_id)
        cv.put(COLUM_USER_PROFILE_AUTH_TOKEN, bean.auth_token)
        return writableDatabase.insert(TABLE_USER_PROFILE, null, cv)
    }

    fun updateProfile(bean: UserInfoDbBean): Long {
        val user_id = bean.user_id
        deleteProfile(user_id)
        return insertProfile(bean)
    }

    fun queryProfile(user_id: String): UserInfoDbBean {
        //execSQL("select * from user_profile where user_id=" + user_id);
        val c = readableDatabase.query(TABLE_USER_PROFILE, null,
                "$COLUM_USER_PROFILE_USER_ID=?", arrayOf(user_id), null, null, null)
        c.moveToFirst()
        return cursor2Profile(c)
    }

    fun deleteProfile(user_id: String?): Long {
        return writableDatabase.delete(TABLE_USER_PROFILE, "$COLUM_USER_PROFILE_USER_ID=?",
                arrayOf(user_id ?: "")).toLong()
    }

    fun currentProfile(): UserInfoDbBean {
        val c = readableDatabase.query(TABLE_USER_PROFILE, null,
                "$COLUM_USER_PROFILE_IS_CURRENT=?", arrayOf("1"), null, null, null)
        return cursor2Profile(c)
    }

    fun resetCurrentUser(bean: UserInfoDbBean): Long {
        writableDatabase.delete(TABLE_USER_PROFILE, "$COLUM_USER_PROFILE_IS_CURRENT=?", arrayOf("1"))
        return insertProfile(bean)
    }

    protected fun cursor2Profile(c: Cursor): UserInfoDbBean {
        val bean = UserInfoDbBean()
        if (c.moveToNext()) {
            bean.is_current = c.getInt(c.getColumnIndex(COLUM_USER_PROFILE_IS_CURRENT))
            bean.user_id = c.getString(c.getColumnIndex(COLUM_USER_PROFILE_USER_ID))
            bean.auth_token = c.getString(c.getColumnIndex(COLUM_USER_PROFILE_AUTH_TOKEN))
            bean.screen_name = c.getString(c.getColumnIndex(COLUM_USER_PROFILE_SCREEN_NAME))
            bean.screen_photo = c.getString(c.getColumnIndex(COLUM_USER_PROFILE_SCREEN_PHOTO))
        }
        return bean
    }

    companion object {
        private val DB_NAME = "dongda.sqlite"
        private val VERSION = 1
        private val TABLE_USER_PROFILE = "user_profile"
        private val COLUM_USER_PROFILE_INDEX = "index"
        private val COLUM_USER_PROFILE_IS_CURRENT = "is_current"
        private val COLUM_USER_PROFILE_USER_ID = "user_id"
        private val COLUM_USER_PROFILE_AUTH_TOKEN = "auth_token"
        private val COLUM_USER_PROFILE_SCREEN_NAME = "screen_name"
        private val COLUM_USER_PROFILE_SCREEN_PHOTO = "screen_photo"
    }
}

