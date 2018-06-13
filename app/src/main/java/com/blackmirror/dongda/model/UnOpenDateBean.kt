package com.blackmirror.dongda.model

/**
 * Create By Ruge at 2018-05-16
 */
class UnOpenDateBean {
    var month: Int = 0
    var daySize: Int = 0//这个月的天数
    var curDay: Int = 0//当前日期
    var firstWeek: Int = 0//从第几个index开始

    var list: MutableList<String>? = null
}
