package com.fb.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 * User: zp
 * Date: 2017/3/20
 * Time: 19:43
 * Project: hnjb
 * 版权所有(C) 2017，银信天下网信息科技服务有限公司
 */
public class PojoToMapUtil {

    public static Map<String, String> objectToMap(final Object obj) throws Exception {
        if(obj == null) return null;
        Map<String, String> map = new HashMap<String, String>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter!=null ? getter.invoke(obj) : null;
            if(value != null)
                map.put(key.toUpperCase(), String.valueOf(value));
        }
        return map;
    }


}
