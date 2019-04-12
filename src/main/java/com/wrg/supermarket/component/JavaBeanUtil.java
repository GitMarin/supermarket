package com.wrg.supermarket.component;

import com.wrg.supermarket.utils.enums.PlatErrorCode;
import com.wrg.supermarket.utils.exception.MkplatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JavaBeanUtil
 * @Description  java实体类转换
 * @Author ZacharyJiao
 * @Date 2019/3/26 9:55
 * @Version 1.0
 **/
public class JavaBeanUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaBeanUtil.class);

    /**
     * @Author ZacharyJiao
     * @Description 将一个map对象转化为bean
     * @Date 2019/3/26
     * @Param
     * @return
     **/
    public static void transMap2Bean(Map<String, Object> map, Object obj) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (map.containsKey(key)) {
                    if(property.getPropertyType().equals(LocalDateTime.class)){
                        Object value = map.get(key);
                        if(value!=null){
                            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime ldt = LocalDateTime.parse(value.toString(),df);
                            Method setter = property.getWriteMethod();
                            setter.invoke(obj, ldt);
                        }else{
                            Method setter = property.getWriteMethod();
                            setter.invoke(obj, value);
                        }
                    }else{
                        Object value = map.get(key);
                        // 得到property对应的setter方法
                        Method setter = property.getWriteMethod();
                        setter.invoke(obj, value);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("map转bean异常",e);
            throw new MkplatException(PlatErrorCode.SYSTEM_ERROR,"map转bean异常");
        }
    }


    /**
     * @Author ZacharyJiao
     * @Description Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
     * @Date 2019/3/26
     * @Param
     * @return
     **/
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    if(property.getPropertyType().equals(LocalDateTime.class) && value!=null){
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        value = df.format((LocalDateTime)value);
                    }
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            LOGGER.error("bean转map异常",e);
            throw new MkplatException(PlatErrorCode.SYSTEM_ERROR,"bean转map异常");
        }
        return map;
    }
}


