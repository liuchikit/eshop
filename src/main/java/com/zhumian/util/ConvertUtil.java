package com.zhumian.util;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.*;
import org.springframework.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {类说明}
 *
 * @author liaozijie
 * @date 2017/11/15
 */
public class ConvertUtil {

    private static Logger logger = LoggerFactory.getLogger(ConvertUtil.class);

    protected static final String REFLECT_ERROR = "反射获取转换对象异常";

    /** 英文逗号：, */
    private static final String COMMA = ",";

    /**
     * map转对象
     * @param map 散列表
     * @param object 对象
     */
    public static void mapToObject(Map map, Object object){
        if (object!=null && map!=null){
            BeanWrapper beanWrapper = new BeanWrapperImpl(object);
            PropertyValues propertyValues = new MutablePropertyValues(map);
            beanWrapper.setPropertyValues(propertyValues,true,true);
        }
    }

    /**
     * 反射实现对象转Map <br>
     * 	只针对实体里所有属性, 包括继承的属性 <br>
     * 	空属性不会转换 <br>
     * @param obj	需要转换的对象
     * @return		转换后的map
     * @author fuqiang
     */
    public static Map<String, Object> objectToMap(Object obj){
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        copyProperty(obj, map, true);
        return map;
    }

    /**
     * 对象转map<br/>
     * @param object 对象
     * @param map 散列表
     */
    public static void objectToMap(Object object, Map map){
        if (object!=null && map!=null){
            copyProperty(object, map, true);
        }
    }

    /**
     * 属性copy
     * @param source 源对象
     * @param target 目标map
     * @param cascade 是否级联copy（true：copy含父类属性；false：copy不含父类属性）
     */
    public static void copyProperty(Object source, Map target, boolean cascade){
        Field[] fields = source.getClass().getDeclaredFields();
        if (cascade){
            Field[] parentFields = source.getClass().getSuperclass().getDeclaredFields();
            fields = ArrayUtils.addAll(fields, parentFields);
        }
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if(field.get(source) != null){
                    target.put(field.getName(), field.get(source));
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            logger.error(REFLECT_ERROR, e);
        }
    }

    /**
     * List<Map<String,Object>>转List<T>
     * @param mapList 数据源列表
     * @param objectList 数据接收列表
     * @param clazz 接收实体类型
     */
    public static void listMapToListObject(List<Map<String,Object>> mapList, List objectList, Class clazz) {
        if (!CollectionUtils.isEmpty(mapList) && objectList!=null){
            for (Map map:mapList){
                Object object = gainInstanceByReflect(clazz);
                mapToObject(map,object);
                objectList.add(object);
            }
        }
    }

    /**
     * 对象列表转对象列表
     * @param sourceList	源列表
     * @param targetList	目标列表
     * @param targetClass	目标类型
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void listObjectToListObject(List sourceList, List targetList, Class targetClass) {
        if (!CollectionUtils.isEmpty(sourceList) && targetList != null){
            for (Object source : sourceList){
                Object target = gainInstanceByReflect(targetClass);
                BeanUtils.copyProperties(source, target);
                targetList.add(target);
            }
        }
    }

    /**
     * 列表对象转换字符串 : 以英文逗号(,)分隔, 不需要前后缀
     * @param list 列表
     * @return 字符串
     */
    public static String listToString(List<String> list) {
        return listToString(list, COMMA);
    }

    /**
     * 列表对象转换字符串 : 不需要前后缀
     * @param list 列表
     * @param separator 字符串分隔符
     * @return 字符串
     */
    public static String listToString(List<String> list, String separator) {
        return listToString(list, separator, null);
    }

    /**
     * 列表对象转换字符串
     * @param list 列表
     * @param separator 字符串分隔符
     * @param surround  字符串的前后缀，null表示不需要包裹
     * @return 字符串
     */
    public static String listToString(List<String> list, String separator, String surround){
        StringBuilder builder = new StringBuilder("");

        if(!CollectionUtils.isEmpty(list)){
            int i = 0;
            for (String str : list) {
                if (i++ > 0) {
                    builder.append(separator);
                }

                if(surround != null){
                    builder.append(surround).append(str).append(surround);
                }else {
                    builder.append(str);
                }
            }
        }
        return builder.toString();
    }

    /**
     * 反射获取对象实例
     * @param clazz 目标类
     * @return 对象实例
     */
    public static Object gainInstanceByReflect(Class clazz){
        String className = clazz.getName();
        return gainInstanceByReflect(className);
    }

    /**
     * 反射获取对象实例
     * @param className 类名
     * @return 对象实例
     */
    private static Object gainInstanceByReflect(String className){
        Object target = null;
        try {
            target = Class.forName(className).newInstance();
        } catch (InstantiationException|IllegalAccessException|ClassNotFoundException e) {
            logger.error(REFLECT_ERROR,e);
        }
        return target;
    }

    /**
     * 对象属性copy
     * @param source 源对象
     * @param target 目标对象
     */
    public static void objectToObject(Object source, Object target){
        objectToObject(source,target,false, false, false);
    }


    /**
     * 对象属性copy
     * @param source 源对象
     * @param target 目标对象
     * @param ignoreNull 忽略NULL属性
     */
    public static void objectToObject(Object source, Object target, boolean ignoreNull){
        objectToObject(source, target, ignoreNull, false, false);
    }

    /**
     * 对象属性copy
     * @param source 源对象
     * @param target 目标对象
     */
    @Deprecated
    public static void objectToObjectWithNoAuditInfo(Object source, Object target){
        objectToObject(source, target, false, false, false);
    }

    /**
     * 对象属性copy
     * @param source 源对象
     * @param target 目标对象
     */
    @Deprecated
    public static void objectToObjectWithCreateInfo(Object source, Object target){
        objectToObject(source, target, false, true, false);
    }

    /**
     * 对象属性copy
     * @param source 源对象
     * @param target 目标对象
     */
    @Deprecated
    public static void objectToObjectWithUpdateInfo(Object source, Object target){
        objectToObject(source, target, false, true, true);
    }


    /**
     * 对象属性copy
     * @param source 源对象
     * @param target 目标对象
     * @param ignoreNull 忽略NULL属性
     * @param withCreateInfo copy包括创建操作信息
     * @param withUpdateInfo copy包括更新操作信息
     */
    public static void objectToObject(Object source, Object target, boolean ignoreNull,
                                      boolean withCreateInfo, boolean withUpdateInfo){
        if (source == null || target == null) {
            return;
        }

        List<String> ignorePropertiesList = new ArrayList<>();
        // 处理旧债务
        // 说明：
        // 1、对于更新/新增操作，所有的VoRequest都应该继承BaseRequest，让以下四个参数在线程中传递
        // 2、对于查询操作，应该另起一个FormVo，比如xxxRequestQueryVo，不应该继承BaseRequest，否则会造成以下四个参数被作为查询参数
        if (!withCreateInfo) {
            ignorePropertiesList.add("creator");
            ignorePropertiesList.add("creatorId");
            ignorePropertiesList.add("createTime");

        }
        if (!withUpdateInfo) {
            ignorePropertiesList.add("updater");
            ignorePropertiesList.add("updaterId");
            ignorePropertiesList.add("updateTime");

        }
        if (ignoreNull){
            ignoreNull(source, ignorePropertiesList);
        }
        String[] ignorePropertiesArray = new String[ignorePropertiesList.size()];
        BeanUtils.copyProperties(source, target, ignorePropertiesList.toArray(ignorePropertiesArray));
    }

    /**
     * 忽略null属性
     * @param source 源
     * @param ignorePropertiesList 忽略的属性队列
     */
    private static void ignoreNull(Object source, List<String> ignorePropertiesList) {
        Field[] fields = source.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(source) == null) {
                    ignorePropertiesList.add(field.getName());
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            logger.error(REFLECT_ERROR, e);
        }
    }


    /**
     * 支持更新操作的对象拷贝
     * @param source 源对象
     * @param target 目标对象
     * @param ignoreNull 忽略NULL属性
     * @param isUpdate 是否更新操作
     */
    public static void objectToObject(Object source, Object target, boolean ignoreNull, boolean isUpdate){
        objectToObject(source, target, ignoreNull, !isUpdate, true);
    }

    /**
     * Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
     * @param map Map集合
     * @param obj 结果对象
     */
    public static void transMap2Bean(Map<String, Object> map, Object obj) {

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }

            }

        } catch (Exception e) {
            logger.error("Map --> Bean Error", e);
        }
    }

    /**
     * 保存操作时属性拷贝， 不忽略null，拷贝creator和editor信息
     * @param source 源对象
     * @param target 目标对象
     */
    public static void ObjectToObjectWithInsert(Object source, Object target) {
        objectToObject(source, target, false, true, true);
    }

    /**
     * 更新操作时属性拷贝， 忽略null属性，不拷贝creator信息
     * @param source 源对象
     * @param target 目标对象
     */
    public static void ObjectToObjectWithUpdate(Object source, Object target) {
        objectToObject(source, target, true, false, true);
    }

    /**
     * 查询操作时属性拷贝，不拷贝creator和Editor信息
     * @param source 源对象
     * @param target 目标对象
     */
    public static void ObjectToObjectWithSelect(Object source, Object target) {
        objectToObject(source, target, true, false, false);
    }
}
