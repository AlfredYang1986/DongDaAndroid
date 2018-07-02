package com.blackmirror.dongda.base

import com.blackmirror.dongda.utils.isNumber
import org.junit.Test

/**
 * Create By Ruge at 2018-07-02
 */
class KotlinTest {
    @Test
    fun testString() {
        var s="12ss"
        println(s.isNumber())
    }
}