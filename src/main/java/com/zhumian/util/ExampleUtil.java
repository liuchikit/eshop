package com.zhumian.util;

import com.zhumian.annotation.FuzzyQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * {类说明}
 *
 * @author liaozijie
 * @date 2017/11/15
 */
public class ExampleUtil {

    private final static Logger logger = LoggerFactory.getLogger(ExampleUtil.class);

    private ExampleUtil() {}

    /**
     * 获取属性名数组
     *
     * @param obj   对象
     * @param isFuzzyQueryOpened 是否模糊查询
     * @param entityClass         实体类Class
     * @return Example
     */
    public static Example createExample(Object obj, boolean isFuzzyQueryOpened, Class entityClass) {
        Example example = new Example(entityClass);
        Example.Criteria criteria = example.createCriteria();
        Field[] fields = ReflectUtil.getAllDeclaredFieldsOnlyPrivate(entityClass);
        Field field;
        for (int i = 0; i < fields.length; i++) {

            field = fields[i];

          /*  //时间范围查询，该注解和Transient、FuzzyQuery不可能同时存在
            if (rangeQuery(obj, entityClass, criteria, field)) {
                continue;
            }*/

            //不持久化的字段
            Transient annotation = field.getAnnotation(Transient.class);
            if (annotation != null) {
                continue;
            }

            // null值忽略，必须放在范围查询后面
            Object fieldValue = getFieldValueByName(field.getName(), obj, entityClass);
            if (fieldValue == null) {
                continue;
            }

            // 是否模糊查询 @FuzzyQuery
            if(isFuzzyQueryOpened){
                fuzzyQuery(criteria, field, fieldValue);
            } else {
                criteria.andEqualTo(field.getName(), fieldValue);
            }
        }
        return example;
    }

    /**
     * 模糊查询字段处理
     * @param criteria  条件对象
     * @param field     字段对象
     * @param fieldValue   字段值
     */
    private static void fuzzyQuery(Example.Criteria criteria, Field field, Object fieldValue) {
        FuzzyQuery fuzzyQuery = field.getAnnotation(FuzzyQuery.class);
        if (fuzzyQuery != null) {
                // 双向模糊查询
                criteria.andLike(field.getName(), "%" + fieldValue + "%");
            }
    }


   /* *//**
     * 时间范围查询注解处理
     * @param obj            对象
     * @param entityClass    类对象
     * @param criteria       条件对象
     * @param field         字段对象
     * @return              如果有这个注解，则返回true
     *//*
    private static boolean rangeQuery(Object obj, Class entityClass, Example.Criteria criteria, Field field) {
        boolean flag = false;
        RangeQuery rangeQuery = field.getAnnotation(RangeQuery.class);
        if(null != rangeQuery){
            String leftRangeFieldName = rangeQuery.leftRange();
            String rightRangeFiledName = rangeQuery.rightRange();
            Date leftRangeDate = (Date) getFieldValueByName(leftRangeFieldName, obj, entityClass);
            Date rightRangeDate = (Date) getFieldValueByName(rightRangeFiledName, obj, entityClass);

            if (null != leftRangeDate && null != rightRangeDate) {
                criteria.andBetween(field.getName(), leftRangeDate, rightRangeDate);
            } else if (leftRangeDate != null) {
                criteria.andGreaterThanOrEqualTo(field.getName(),leftRangeDate);
            } else if (rightRangeDate != null) {
                criteria.andLessThanOrEqualTo(field.getName(),rightRangeDate);
            }
            flag = true;
        }
        return flag;
    }*/

    /**
     * 根据属性名get属性值
     *
     * @param fieldName 属性名
     * @param obj       对象
     * @return Object
     */
    private static Object getFieldValueByName(String fieldName, Object obj, Class entityClass) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH);
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = entityClass.getMethod(getter);
            return method.invoke(obj);
        } catch (Exception e) {
            // 没有该方法，返回空值
            logger.error("fileName: {}, Object: {}", fieldName, obj, e);
            return null;
        }
    }
}
