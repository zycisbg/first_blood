package com.fb.interceptor;

import com.jfinal.kit.PropKit;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

public class XssRequestWrapper extends HttpServletRequestWrapper {





    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public String getParameter(String name) {
        if(name==null){
            return null;
        }
        String value = super.getParameter(name);

        if (value == null) {
            return null;
        }
        return StringEscapeUtils.escapeHtml4(value);
    }

    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if(name==null){
            return null;
        }

        if (values == null) {
            return null;
        }
        String[] newValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            newValues[i] = StringEscapeUtils.escapeHtml4(values[i]);
        }
        return newValues;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        HashMap<String, String[]> newMap = new HashMap();
        Map<String, String[]> oldMap = super.getParameterMap();
        for(String key : oldMap.keySet()){
                newMap.put(key,this.getNewValues(key,oldMap));
        }
        return newMap;
    }

    private String[] getNewValues(String key,Map<String, String[]> map){
        if(map==null){
            return null;
        }
        String[] strings = map.get(key);
        String[] newValues = new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            newValues[i] = StringEscapeUtils.escapeHtml4(strings[i]);
        }
        return newValues;
    }
}
