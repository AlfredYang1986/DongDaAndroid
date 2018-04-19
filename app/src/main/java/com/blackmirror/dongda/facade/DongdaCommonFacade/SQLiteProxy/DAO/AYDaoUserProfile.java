package com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO;

import com.blackmirror.dongda.model.uibean.PhoneLoginUiBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by alfredyang on 27/05/2017.
 */
public class AYDaoUserProfile implements Serializable {
    private String user_id;
    private String auth_token;
    private int is_current = 0;
    private String screen_name;
    private String screen_photo;

    public AYDaoUserProfile() {
        super();
    }

    public AYDaoUserProfile(JSONObject o) {
        /*{
            "status": "ok",
                "result": {
            "user": {
                "screen_name": "xcx",
                        "has_auth_phone": 1,
                        "current_device_type": "",
                        "is_service_provider": 0,
                        "user_id": "2737d748bce21504447cf27d7b1f4f99",
                        "screen_photo": "",
                        "current_device_id": ""
            },
            "auth_token": "bearer2737d748bce21504447cf27d7b1f4f99"
        }
        }*/
        super();
        try {
            JSONObject result = o.getJSONObject("result");
            JSONObject user = o.getJSONObject("result").getJSONObject("user");
            this.auth_token = result.getString("auth_token");
            this.user_id = user.getString("user_id");
            this.screen_name = user.getString("screen_name");
            this.screen_photo = user.getString("screen_photo");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public AYDaoUserProfile(PhoneLoginUiBean bean) {
        this.user_id = bean.user_id;
        this.auth_token = bean.auth_token;
        this.screen_name = bean.screen_name;
        this.screen_photo = bean.screen_photo;

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public int getIs_current() {
        return is_current;
    }

    public void setIs_current(int is_current) {
        this.is_current = is_current;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getScreen_photo() {
        return screen_photo;
    }

    public void setScreen_photo(String screen_photo) {
        this.screen_photo = screen_photo;
    }
}
