package com.blackmirror.dongda.factory.common;

import com.blackmirror.dongda.AY.AYSysHelperFunc;
import com.blackmirror.dongda.AY.AYSysObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alfredyang on 17/05/2017.
 */
public abstract class AYFactory implements AYSysObject {

    protected String instance_name;//activity传入的是activity全名 com.xcx.xcx.testactivity
    protected Map<String, List<String> > sub_instance_name = new HashMap<>();


    /**
     * @param t type name : [command, facade, controller, fregment]
     * @return reVal : instance
     */
    final public AYSysObject createInstance(String t) {
        preCreation(t);
        AYSysObject reVal = creation();
        postCreation(reVal);
        return reVal;
    }

    public void preCreation(String t) {}

    public AYSysObject creation() {
        return AYSysHelperFunc.getInstance().createInstanceByName(getInstanceName());
    }

    public void postCreation(AYSysObject defaultArgs) {}

    public String getInstanceName() {
        return this.instance_name;
    }

    public void setInstanceName(String s) {
        this.instance_name = s;
    }

    public List<String> getSubInstanceName(String field) {
        return sub_instance_name.get(field);
    }

    public void putSubInstanceName(String field, List<String> lst) {
        sub_instance_name.put(field, lst);
    }
}
