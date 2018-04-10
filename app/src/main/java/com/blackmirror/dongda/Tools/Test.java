package com.blackmirror.dongda.Tools;

/**
 * Created by Ruge on 2018-04-10 下午1:42
 */
public class Test {
    public static void main(String[] args){
        StringBuilder sb = new StringBuilder();
        sb.append("{\"status\":error,")
                .append("\"result\":")
                .append("zxcxc")
                .append("}");
        /*try {
            JSONObject object = new JSONObject(sb.toString());
            System.out.println(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        System.out.println(sb.toString());
    }

}
