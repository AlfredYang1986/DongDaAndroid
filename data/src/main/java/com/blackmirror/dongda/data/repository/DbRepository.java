package com.blackmirror.dongda.data.repository;

import com.blackmirror.dongda.base.AYApplication;
import com.blackmirror.dongda.data.db.AYSQLiteHelper;
import com.blackmirror.dongda.data.model.db.UserInfoDbBean;

/**
 * Create By Ruge at 2018-06-13
 */
public class DbRepository {
    private AYSQLiteHelper helper;

    public DbRepository() {
        helper=new AYSQLiteHelper(AYApplication.appContext);
    }

    public static long insertProfile(UserInfoDbBean bean){
        return DbRepository.getInstance().helper.insertProfile(bean);
    }

    public static long updateProfile(UserInfoDbBean bean) {
        return DbRepository.getInstance().helper.insertProfile(bean);
    }

    public static UserInfoDbBean queryProfile(String user_id) {
        return DbRepository.getInstance().helper.queryProfile(user_id);
    }

    public static long deleteProfile(String user_id) {
        return DbRepository.getInstance().helper.deleteProfile(user_id);
    }

    public static UserInfoDbBean currentProfile() {
        return DbRepository.getInstance().helper.currentProfile();

    }

    public static long resetCurrentUser(UserInfoDbBean bean) {
        return DbRepository.getInstance().helper.resetCurrentUser(bean);

    }

    private static DbRepository getInstance(){
        return new DbRepository();
    }
}
