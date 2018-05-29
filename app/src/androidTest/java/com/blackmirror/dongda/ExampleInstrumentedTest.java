package com.blackmirror.dongda;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.blackmirror.dongda", appContext.getPackageName());
    }

    @Test
    public void test(){
        Calendar calendar = Calendar.getInstance();
        int year =calendar.get(Calendar.YEAR);

        int month=calendar.get(Calendar.MONTH)+1;

        int day =calendar.get(Calendar.DAY_OF_MONTH);




        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);

        a.set(Calendar.MONTH,month);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int next = a.get(Calendar.DATE);



        /**获取日历实例**/
        Calendar cld = Calendar.getInstance();
        /**设置日历成当月的第一天**/
        cld.set(Calendar.DAY_OF_MONTH,1);
        //星期对应数字
        int i = cld.get(Calendar.DAY_OF_WEEK);

        cld.set(Calendar.MONTH,month);
        cld.set(Calendar.DAY_OF_MONTH,1);
        //星期对应数字
        int i1 = cld.get(Calendar.DAY_OF_WEEK);

        Log.d("xcx","month "+month+"\n"+" maxDate "+maxDate+"\n"+" DAY_OF_WEEK "+i+"\n"+"DAY_OF_WEEK next "+i1);

        Log.d("xcx", "next: "+next);
    }
}
