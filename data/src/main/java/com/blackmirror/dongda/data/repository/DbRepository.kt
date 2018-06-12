package com.blackmirror.dongda.data.repository

import com.blackmirror.dongda.base.AYApplication
import com.blackmirror.dongda.data.db.AYSQLiteHelper
import com.blackmirror.dongda.data.model.db.UserInfoDbBean

/**
 * Create By Ruge at 2018-05-09
 */
class DbRepository {
    private val helper: AYSQLiteHelper

    init {
        helper = AYSQLiteHelper(AYApplication.appContext)
    }

    companion object {

        fun insertProfile(bean: UserInfoDbBean): Long {
            return DbRepository.instance.helper.insertProfile(bean)
        }

        fun updateProfile(bean: UserInfoDbBean): Long {
            return DbRepository.instance.helper.insertProfile(bean)
        }

        fun queryProfile(user_id: String): UserInfoDbBean {
            return DbRepository.instance.helper.queryProfile(user_id)
        }

        fun deleteProfile(user_id: String): Long {
            return DbRepository.instance.helper.deleteProfile(user_id)
        }

        fun currentProfile(): UserInfoDbBean {
            return DbRepository.instance.helper.currentProfile()

        }

        fun resetCurrentUser(bean: UserInfoDbBean): Long {
            return DbRepository.instance.helper.resetCurrentUser(bean)

        }

        private val instance: DbRepository
            get() = DbRepository()
    }
}
