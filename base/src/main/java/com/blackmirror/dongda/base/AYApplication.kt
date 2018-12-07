package com.blackmirror.dongda.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatActivity
import kotlin.properties.Delegates

/**
 * Create By Ruge at 2018-12-06
 */
open class AYApplication : Application() {
    companion object {
        @JvmStatic
        var me: AYApplication by Delegates.notNull()
        @JvmStatic
        var appContext: Context by Delegates.notNull()
        var activityList: MutableList<Activity> by Delegates.notNull()

        @JvmStatic
        fun addActivity(activity: AppCompatActivity) {
            if (!activityList.contains(activity)) {
                activityList.add(activity)
            }
        }

        @JvmStatic
        fun removeActivity(activity: AppCompatActivity) {
            if (activityList.contains(activity)) {
                activityList.remove(activity)
            }
        }

        @JvmStatic
        fun finishAllActivity() {
            for (i in activityList.indices) {
                if (activityList[i] != null) {
                    activityList[i].finish()
                }
            }
            activityList.clear()
        }

        @JvmStatic
        fun finishActivity(activity: AppCompatActivity) {
            if (activityList.contains(activity)){
                activityList.remove(activity)
                activity.finish()
            }
        }

    }

    override fun onCreate() {
        super.onCreate()
        me = this
        appContext = applicationContext
        activityList = mutableListOf()
    }

}