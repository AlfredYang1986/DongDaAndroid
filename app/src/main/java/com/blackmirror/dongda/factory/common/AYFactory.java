package com.blackmirror.dongda.factory.common;

import com.blackmirror.dongda.AY.AYSysObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by alfredyang on 17/05/2017.
 */
public abstract class AYFactory implements AYSysObject {
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
        AYSysObject result = null;
        try {
            Class clazz1 = Class.forName(getInstanceName());
            Constructor c = clazz1.getConstructor(null);
            result = (AYSysObject)c.newInstance(null);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public void postCreation(AYSysObject _) {}

    protected String instance_name;

    public String getInstanceName() {
        return this.instance_name;
    }

    public void setInstanceName(String s) {
        this.instance_name = s;
    }
}
