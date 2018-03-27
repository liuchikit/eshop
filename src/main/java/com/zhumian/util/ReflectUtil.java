package com.zhumian.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {类说明}
 *
 * @author liaozijie
 * @date 2017/11/15
 */
public class ReflectUtil {

    private ReflectUtil(){}

    /**
     * 获取指定类所有声明字段（含继承树上所有父类的申明字段）
     * @param clazz 指定类
     * @return 指定类所有声明字段
     */
    public static Field[] getAllDeclaredFields(Class clazz){
        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        Class superClass = clazz.getSuperclass();
        while (superClass != Object.class){
            fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        }
        return fields.toArray(new Field[fields.size()]);
    }

    /**
     * 获取指定类所有声明字段（含继承树上所有父类的申明字段）
     * 仅返回 private 成员变量
     * @param clazz 指定类
     * @return 指定类所有声明字段 仅返回 private 成员变量
     */
    public static Field[] getAllDeclaredFieldsOnlyPrivate(Class clazz){
        Field[] fields = getAllDeclaredFields(clazz);
        List<Field> newList = new ArrayList<>();
        for (Field field: fields) {
            if (Modifier.isStatic(field.getModifiers())
                    || Modifier.isFinal(field.getModifiers())
                    || Modifier.isProtected(field.getModifiers())
                    || Modifier.isPublic(field.getModifiers())) {
                continue;
            }
            newList.add(field);
        }
        return newList.toArray(new Field[newList.size()]);
    }
}
