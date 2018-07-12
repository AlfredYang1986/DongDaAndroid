package com.blackmirror.dongda.utils

import java.text.DecimalFormat

/**
 * Create By Ruge at 2018-07-02
 */
fun String.isNumber(): Boolean {
    try {
        val i = this.toInt()
        return true
    } catch (e: NumberFormatException) {

    }
    return false
}

fun String.getDoubleValue(): Double {
    var d = -1.0
    try {
        d = this.toDouble()
        return d
    } catch (e: NumberFormatException) {
        return d
    }
}

fun Double.formatNumber(): String {
    val decimalFormat = DecimalFormat("###################.###########")
    return decimalFormat.format(this)
}
