package com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO;

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
        super();
        try {
            this.user_id = o.getString("user_id");
            this.auth_token = o.getString("auth_token");
            this.screen_name = o.getString("screen_name");
            this.screen_photo = o.getString("screen_photo");

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
