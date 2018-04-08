package com.blackmirror.dongda.factory;

import android.content.Context;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alfredyang on 12/05/2017.
 */
public class AYFactoryManager {

    final private String TAG = "Factory Manager";

    private static AYFactoryManager instance;

    private Context context = null;
    private Document cmd_doc, facade_doc, fragment_doc, activity_doc;

    public Context getContext() {
        return context;
    }

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
        fragment_doc = loadCmdConfig(R.raw.fragments);
        activity_doc = loadCmdConfig(R.raw.controllers);

        /**
         * 必须要有文件夹
         */
        File f = new File("/mnt/sdcard/dongda/");
        f.mkdirs();
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

    protected Element queryXmlElement(Document doc,
                                      String tag_name,
                                      String base_attr,
                                      String base_var) {
        Element result = null;
        Element rt = doc.getDocumentElement();
        NodeList nst = rt.getElementsByTagName(tag_name);
        for (int index = 0; index < nst.getLength(); ++index) {
            Element elem = (Element) nst.item(index);
            String n = elem.getAttribute(base_attr);
            if (n.equals(base_var)) {
                result = elem;
                break;
            }
        }
        return result;
    }

    protected Map<String, String> queryAttrInElement(Element elem,
                                                     String ... target_attr) {

        Map<String, String> result = new HashMap<>();
        for (String iter : target_attr) {
            String tmp = elem.getAttribute(iter);
            result.put(iter, tmp);
        }
        return result;
    }

    protected List<String> querySubElement(String field, Element elem) {
        NodeList nst = elem.getElementsByTagName(field);
        List<String> result = new ArrayList<>();
        for (int index = 0; index < nst.getLength(); ++index) {
            Element tmp = (Element) nst.item(index);
            result.add(tmp.getAttribute("short"));
        }
        return result;
    }

    public AYSysObject queryInstance(String t, String short_name) {
        AYFactory f = queryFactoryInstance(t, short_name);
        return f.createInstance(t);
    }

    public AYFactory queryFactoryInstance(String t, String short_name) {
        AYFactory result;

        Document doc = null;
        if (t.equals("command")) {
            doc = cmd_doc;
        } else if (t.equals("controller")) {
            doc = activity_doc;
        } else if (t.equals("facade")) {
            doc = facade_doc;
        } else if (t.equals("fragment") || t.equals("listfragment")) {
            doc = fragment_doc;
        }

        Element elem = queryXmlElement(doc, t, "short", short_name);
        Map<String, String> tmp = queryAttrInElement(elem, "factory", "name");
        String f_name = tmp.get("factory");
        String name = tmp.get("name");

        String md5_code = AYSysHelperFunc.getInstance().md5(name);
        result = queryFactoryInManager(md5_code);

        if (result == null) {

            result = createNewFactory(md5_code, name, f_name);

            List<String> cmds = querySubElement("command", elem);
            result.putSubInstanceName("command", cmds);

            List<String> facades = querySubElement("facade", elem);
            result.putSubInstanceName("facade", facades);

            List<String> fragments = querySubElement("fragment", elem);
            result.putSubInstanceName("fragment", fragments);

            List<String> listfragments = querySubElement("listfragment", elem);
            result.putSubInstanceName("listfragment", listfragments);
        }

        return result;
    }

    protected AYFactory queryFactoryInManager(String md5_code) {
        if (manager.containsKey(md5_code)) return manager.get(md5_code);
        else return null;
    }

    protected AYFactory createNewFactory(String md5_code, String name, String f_name) {
        AYFactory f = createFactoryImpl(f_name);
        f.setInstanceName(name);
        manager.put(md5_code, f);
        return f;
    }

    protected AYFactory createFactoryImpl(String factory_name) {
        return (AYFactory) AYSysHelperFunc.getInstance().createInstanceByName(factory_name);
    }
}
