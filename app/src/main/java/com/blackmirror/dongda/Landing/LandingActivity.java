package com.blackmirror.dongda.Landing;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.Home.HomeActivity.AYHomeActivity;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.AppConstant;
import com.blackmirror.dongda.Tools.BasePrefUtils;
import com.blackmirror.dongda.Tools.CalUtils;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.SnackbarUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.model.BaseServerBean;
import com.blackmirror.dongda.model.serverbean.ErrorInfoServerBean;
import com.blackmirror.dongda.model.serverbean.WeChatLoginServerBean;
import com.blackmirror.dongda.model.serverbean.WeChatUserInfoServerBean;
import com.blackmirror.dongda.model.uibean.ErrorInfoUiBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class LandingActivity extends AYActivity implements PlatformActionListener {

    final static String TAG = "Landing Activity";
    private RelativeLayout rl_phone_login;
    private RelativeLayout rl_wechat_login;
    private static final PublishSubject<? extends BaseServerBean> pb = PublishSubject.create();
    private Disposable disposable;
    private WeakReference<LandingActivity> reference;
    private Disposable completeDb;
    private Disposable errorDb;
    private Disposable cancelDb;

    public static PublishSubject getWeChatInfo() {
        return pb;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        reference=new WeakReference<>(this);
        //在setContentView之后调用
        initSystemBarColor();
        initView();
        //        requestPermissions();
        initData();
        initListener();
    }

    private void initView() {
        rl_phone_login = findViewById(R.id.rl_phone_login);
        rl_wechat_login = findViewById(R.id.rl_wechat_login);
    }

    private void initData() {
    }


    private void initListener() {

        disposable = pb.subscribe(new Consumer<BaseServerBean>() {
            @Override
            public void accept(BaseServerBean bean) throws Exception {
                if (bean != null && bean instanceof WeChatUserInfoServerBean) {
                    WeChatUserInfoServerBean infoBean = (WeChatUserInfoServerBean) bean;
                    LogUtils.d(infoBean.toString());
                }
            }
        });

        rl_phone_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, PhoneInputActivity.class);
                startActivity(intent);
            }
        });

        rl_wechat_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weChatLogin();
                //                startActivity(new Intent(LandingActivity.this, AYHomeActivity.class));
            }
        });

    }

    /**
     * 服务器成功回调
     *
     * @param arg
     */
    public void AYWeChatLoginCmdSuccess(JSONObject arg) {

        LogUtils.d("AYWeChatLoginCmdSuccess " + Thread.currentThread().getName());

        closeProcessDialog();

        LogUtils.d("LandingActivity wechat login " + arg.toString());
        ToastUtils.showShortToast("登陆成功!");
        WeChatLoginServerBean bean = JSON.parseObject(arg.toString(), WeChatLoginServerBean.class);
        if (bean != null && "ok".equals(bean.status) && bean.result != null) {
            if (bean.result.user != null) {
                LogUtils.d("cal ", bean.result.user.user_id+"   "+CalUtils.md5(bean.result.user.user_id));
                BasePrefUtils.setUserId(bean.result.user.user_id);
            }
            BasePrefUtils.setAuthToken(bean.result.auth_token);
            startActivity(new Intent(LandingActivity.this, AYHomeActivity.class));
            finish();
        } else if (bean != null && bean.error != null) {
            ToastUtils.showShortToast(bean.error.message);
        }

    }

    /**
     * 服务器失败回调
     *
     * @param arg
     */
    public void AYWeChatLoginCmdFailed(JSONObject arg) {
        closeProcessDialog();
        LogUtils.d("LandingActivity wechat failed " + arg.toString());
        //        Toast.makeText(this, sms_result.getErrorMessage(), LENGTH_LONG).show();
        //        sms_result = new SendSMSCodeResult(arg);
        ErrorInfoServerBean bean = JSON.parseObject(arg.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(bean);
        if (uiBean.code==10010){
            SnackbarUtils.show(rl_phone_login,uiBean.message);
        }else {
            ToastUtils.showShortToast(uiBean.message+"("+uiBean.code+")");
        }

    }

    private void weChatLogin() {
       /* if (!AYApplication.weChatApi.isWXAppInstalled()) {
            Toast.makeText(getApplicationContext(), "您还未安装微信!", Toast.LENGTH_SHORT).show();
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "dongda_wx_login";
        AYApplication.weChatApi.sendReq(req);*/
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        if (reference.get()!=null) {
            wechat.setPlatformActionListener(new PlatformActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> map) {
                    String userId = platform.getDb().getUserId();//获取用户账号
                    String userName = platform.getDb().getUserName();//获取用户名字
                    String userIcon = platform.getDb().getUserIcon();//获取用户头像
                    String userGender = platform.getDb().getUserGender(); //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
                    String token = platform.getDb().getToken();


                    final Map<String, String> m = new HashMap<>();

                    m.put("provide_uid", userId);
                    m.put("provide_token", token);
                    m.put("provide_screen_name", userName);
                    m.put("provide_name", "wechat");
                    m.put("provide_screen_photo", userIcon);


                    Observable.create(new ObservableOnSubscribe<String>() {
                        @Override
                        public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
//                            emitter.onNext("");
                            emitter.onComplete();
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<String>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    completeDb = d;
                                }

                                @Override
                                public void onNext(String s) {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                    showProcessDialog("正在登陆...");
                                    unSubscribe();
                                    LogUtils.d("Observable " + Thread.currentThread().getName());
                                    login(m);
                                }
                            });
                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {
                    errorDb = Observable.just(throwable.getMessage())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                    if (!isViewValid()) {
                                        return;
                                    }
                                    if (!TextUtils.isEmpty(s))
                                        ToastUtils.showShortToast(s);
                                }
                            });
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    cancelDb = Observable.just("")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s) throws Exception {
                                    if (!isViewValid()) {
                                        return;
                                    }
                                    ToastUtils.showShortToast("授权已取消");
                                }
                            });
                }
            });
        }
//        wechat.setPlatformActionListener(this);
        wechat.SSOSetting(false);
//        ShareSDK.unregisterPlatform(wechat);
        authorize(wechat, 1);

    }


    //授权
    private void authorize(Platform plat, int type) {
        if (plat.isAuthValid()) { //如果授权就删除授权资料
            plat.removeAccount(true);
        }
        plat.showUser(null);//授权并获取用户信息
    }

    private void unSubscribe() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }

        if (completeDb != null && !completeDb.isDisposed()) {
            completeDb.dispose();
            completeDb = null;
        }

        if (errorDb != null && !errorDb.isDisposed()) {
            errorDb.dispose();
            errorDb = null;
        }

        if (cancelDb != null && !cancelDb.isDisposed()) {
            cancelDb.dispose();
            cancelDb = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AppConstant.PERMISSION_REQUEST:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        LogUtils.d("xcx", permissions[i] + " granted");
                    } else {
                        LogUtils.d("xcx", permissions[i] + " denied");

                    }
                }
                break;
        }
    }

    @Override
    public void onComplete(Platform platform, int i, final HashMap<String, Object> map) {
        LogUtils.d("onComplete " + Thread.currentThread().getName());

        String userId = platform.getDb().getUserId();//获取用户账号
        String userName = platform.getDb().getUserName();//获取用户名字
        String userIcon = platform.getDb().getUserIcon();//获取用户头像
        String userGender = platform.getDb().getUserGender(); //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
        String token = platform.getDb().getToken();


        final Map<String, String> m = new HashMap<>();

        m.put("provide_uid", userId);
        m.put("provide_token", token);
        m.put("provide_screen_name", userName);
        m.put("provide_name", "wechat");
        m.put("provide_screen_photo", userIcon);


        completeDb = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("");
                emitter.onComplete();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        showProcessDialog("正在登陆...");
                        LogUtils.d("Observable " + Thread.currentThread().getName());
                        login(m);
                    }
                });



    }

    private void login(Map<String, String> map) {
        try {
            //            object.put("third", m);
            JSONObject o = new JSONObject(map);
            JSONObject object = new JSONObject();
            object.put("third", o);
            AYFacade facade = facades.get("LoginFacade");

            LogUtils.d("wechat " + object.toString());
            facade.execute("LoginWithWeChat", object);


        } catch (JSONException e) {
            closeProcessDialog();
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        errorDb = Observable.just(throwable.getMessage())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (!isViewValid()) {
                            return;
                        }
                        if (!TextUtils.isEmpty(s))
                            ToastUtils.showShortToast(s);
                    }
                });
    }

    @Override
    public void onCancel(Platform platform, int i) {
        cancelDb = Observable.just("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (!isViewValid()) {
                            return;
                        }
                        ToastUtils.showShortToast("授权已取消");
                    }
                });
    }

    private void initSystemBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup viewGroup = this.findViewById(Window.ID_ANDROID_CONTENT);
            View childView = viewGroup.getChildAt(0);
            if (null != childView) {
                ViewCompat.setFitsSystemWindows(childView, false);
            }
        }
    }

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void bindingFragments() {
        //        FragmentManager fm = getSupportFragmentManager();
        //        AYFragment f = this.fragments.get("frag_test");
        //        fm.beginTransaction().add(R.id.fragment_test, f).commit();
    }

}
