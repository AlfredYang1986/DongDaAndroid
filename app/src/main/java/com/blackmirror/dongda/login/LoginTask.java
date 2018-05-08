package com.blackmirror.dongda.login;

import com.blackmirror.dongda.domain.Interactor.PhoneLoginUserCase;
import com.blackmirror.dongda.domain.Interactor.SendSmsUserCase;
import com.blackmirror.dongda.domain.Interactor.WeChatLoginUserCase;

import javax.inject.Inject;

public class LoginTask {
    private final SendSmsUserCase sendSmsUserCase;
    private final PhoneLoginUserCase phoneLoginUserCase;
    private final WeChatLoginUserCase weChatLoginUserCase;

    @Inject
    public LoginTask(SendSmsUserCase sendSmsUserCase, PhoneLoginUserCase phoneLoginUserCase, WeChatLoginUserCase weChatLoginUserCase) {
        this.sendSmsUserCase = sendSmsUserCase;
        this.phoneLoginUserCase = phoneLoginUserCase;
        this.weChatLoginUserCase = weChatLoginUserCase;
    }
}
