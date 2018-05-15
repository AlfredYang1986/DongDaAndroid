package com.blackmirror.dongda.data;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.blackmirror.dongda.data.repository.ApplyAndEnrolRepositoryImpl;
import com.blackmirror.dongda.data.utils.LogUtils;
import com.blackmirror.dongda.domain.model.ApplyServiceDomainBean;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.blackmirror.dongda.data.test", appContext.getPackageName());
    }

    @Test
    public void test(){
        ApplyAndEnrolRepositoryImpl repository = new ApplyAndEnrolRepositoryImpl();
        repository.apply("fff","xxx","","17610279929","北京")
                .subscribe(new Observer<ApplyServiceDomainBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ApplyServiceDomainBean bean) {
                        if (bean.isSuccess){
                            LogUtils.d("onNext success");
                        }else {
                            LogUtils.d("onNext fail");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("onError "+e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
