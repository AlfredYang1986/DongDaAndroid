package com.blackmirror.dongda.factory;

import android.content.Context;
import android.util.Log;
import com.blackmirror.dongda.AY.AYSysHelperFunc;
import com.blackmirror.dongda.AY.AYSysObject;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.factory.common.AYFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alfredyang on 12/05/2017.
 */
public class AYFactoryManager {

    final private String TAG = "Factory Manager";

    private static AYFactoryManager instance;

    private Context context = null;
    private Document cmd_doc, facade_doc, activity_doc;

    public static AYFactoryManager getInstance(Context c) {
        //TODO: 修改成线程安全的
        if (instance == null) {
            instance = new AYFactoryManager(c);
        }
        return instance;
    }

    private AYFactoryManager(Context c) {
        if (context == null) {
            context = c.getApplicationContext();
        }

        cmd_doc = loadCmdConfig(R.raw.commands);
        facade_doc = loadCmdConfig(R.raw.facade);
        activity_doc = loadCmdConfig(R.raw.controllers);
    }

    private Map<String, AYFactory> manager = new HashMap<>();

    private Document loadCmdConfig(int resourceId) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = null;

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream in = context.getResources().openRawResource(resourceId);
            doc = db.parse(in);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return doc;
        }
    }

    //    public AYSysObject queryInstance(String t, String name) {
//        AYSysObject result;
//        if (t.equalsIgnoreCase("command")) {
//            result = queryCmdInstance(name);
//        } else {
//            result = null;
//        }
//
//        return result;
//    }

//    protected interface result_func<T> {
//        T queryResult(Element elem, String ... args);
//    }
//    protected class string_result_func implements result_func<String> {
//        @Override
//        public String queryResult(Element elem, String... args) {
//            String result = null;
//            for (String iter : args) {
//                result = elem.getAttribute(iter);
//                if (result != null && !result.isEmpty())
//                    break;
//            }
//            return result;
//        }
//    }
//    protected class map_result_func implements result_func<Map<String, String> > {
//        @Override
//        public Map<String, String> queryResult(Element elem, String... args) {
//            Map<String, String> result = null;
//
//            return result;
//        }
//    }
//
//    protected result_func createResultFunc(String ... attr) {
//        result_func result;
//        if (attr.length == 1) result = new string_result_func();
//        else result = new map_result_func();
//        return result;
//    }

    protected Map<String, String> queryXmlImpl(Document doc,
                                                String tag_name,
                                                String base_attr,
                                                String base_var,
                                                String ... target_attr) {

        Map<String, String> result = new HashMap<>();
        Element rt = doc.getDocumentElement();
        NodeList nst = rt.getElementsByTagName(tag_name);
        for (int index = 0; index < nst.getLength(); ++index) {
            Element elem = (Element) nst.item(index);
            String n = elem.getAttribute(base_attr);
            if (n.equals(base_var)) {
                for (String iter : target_attr) {
                    String tmp = elem.getAttribute(iter);
                    result.put(iter, tmp);
                }
                break;
            }
        }
        return result;
    }

    public AYSysObject queryInstance(String t, String short_name) {
        AYSysObject result = null;
        if (t.equals("command")) {
            Map<String, String> tmp = queryXmlImpl(cmd_doc, "command", "short", short_name, "factory", "name");
            String f_name = tmp.get("factory");
            String name = tmp.get("name");
            result = queryCmdInstance(name, f_name);
        }
        return result;
    }

    protected String queryFactoryNameByName(String t, String name) {
        String result = null;
        if (t.equals("command")) {
            result = queryXmlImpl(cmd_doc, "command", "name", name, "factory").get("factory");
        }
        return result;
    }

    protected AYSysObject queryCmdInstance(String name, String f_name) {
        AYFactory f = queryFactoryByName("command", name, f_name);
        return f.createInstance("command");
    }

    protected AYSysObject queryCmdInstance(String name) {
        String f_name = queryFactoryNameByName("command", name);
        AYFactory f = queryFactoryByName("command", name, f_name);
        return f.createInstance("command");
    }

    protected AYFactory queryFactoryByName(String t, String i, String name) {
        String n = AYSysHelperFunc.getInstance().md5(name);
        AYFactory result = null;
        if (manager.containsKey(n)) {
            result = manager.get(n);
        } else {
            AYFactory f = createFactory(name);
            f.setInstanceName(i);
            manager.put(n, f);
            result = f;
        }

        return result;
    }

    protected AYFactory createFactory(String factory_name) {
        AYFactory result = null;
        try {
            Class clazz1 = Class.forName(factory_name);
            Constructor c = clazz1.getConstructor(null);
            result = (AYFactory) c.newInstance(null);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return result;
    }
}
