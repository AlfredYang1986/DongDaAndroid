package com.blackmirror.dongda.data

import com.blackmirror.dongda.data.repository.LoginRepositoryImpl2
import org.junit.Test

/**
 * Create By Ruge at 2018-06-07
 */
class ServerTest {


    @Test
    fun testServer() {
        var r = LoginRepositoryImpl2()
        r.sendSms("17610279929")
                .subscribe(
                        { bean ->
                            println("success "+bean.toString())
                        }, {
                    println("error ${it.message}")
                })
    }
}