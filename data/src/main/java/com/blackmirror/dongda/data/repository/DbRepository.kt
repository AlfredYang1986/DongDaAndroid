package com.blackmirror.dongda.data.repository

import com.blackmirror.dongda.base.AYApplication
import com.blackmirror.dongda.data.db.AYSQLiteHelper
import com.blackmirror.dongda.data.model.db.UserInfoDbBean

/**
 * Create By Ruge at 2018-12-07
 */
class MySqliteHelper{
    companion object {
         val sqliteHelper: AYSQLiteHelper=AYSQLiteHelper(AYApplication.appContext)
    }
}

fun insertProfile(bean: UserInfoDbBean): Long {
    return MySqliteHelper.sqliteHelper.insertProfile(bean)
}

fun updateProfile(bean: UserInfoDbBean): Long {
    return MySqliteHelper.sqliteHelper.insertProfile(bean)
}

fun queryProfile(user_id: String): UserInfoDbBean {
    return MySqliteHelper.sqliteHelper.queryProfile(user_id)
}

fun deleteProfile(user_id: String): Long {
    return MySqliteHelper.sqliteHelper.deleteProfile(user_id)
}

fun currentProfile(): UserInfoDbBean {
    return MySqliteHelper.sqliteHelper.currentProfile()

}

fun resetCurrentUser(bean: UserInfoDbBean): Long {
    return MySqliteHelper.sqliteHelper.resetCurrentUser(bean)

}