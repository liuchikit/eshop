package com.zhumian.service.base;

import com.zhumian.config.mybatis.BaseMapper;
import com.zhumian.constant.SysMsg;
import com.zhumian.vo.res.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


public abstract class BaseServiceImpl<T extends Serializable,PK extends Serializable> implements BaseService<T,PK>{

    private static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    protected BaseMapper<T> mapper;

    protected Class entityClass;

    public abstract void setMapper(BaseMapper<T> mapper);

    public BaseServiceImpl(){
        //当前对象的直接超类Type
        Type type = getClass().getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) { // 如果Service类被包装代理类的话
            type = getClass().getSuperclass().getGenericSuperclass();
        }
        //参数化类型
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
        this.entityClass = (Class<T>) trueType;
    }

    @Override
    public T getById(PK pk) {
        return mapper.selectByPrimaryKey(pk);
    }

    @Override
    public T getOne(T t) {
        return mapper.selectOne(t);
    }

    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }

    @Override
    public List<T> findByParams(T t) {
        return mapper.selectByExample(t);
    }

    @Override
    public List<T> findByParams(T filter, boolean isFuzzyQueryOpened) {
        return null;
    }

    @Override
    public int save(T t) {
        return mapper.insert(t);
    }

    @Override
    public int saveAll(List<T> l) {
        return 0;
    }

    @Override
    public int update(T t) {
        return mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public int updateAll(T t) {
        return mapper.updateByPrimaryKey(t);
    }

    @Override
    public BaseResponse<T> getByIdR(PK pk) {
        T t = getById(pk);
        if(t == null){
            return new BaseResponse<>(false, SysMsg.GET_FAIL.getMsg());
        }else {
            return new BaseResponse<>(t,SysMsg.GET_SUCCESS.getMsg());
        }
    }

    @Override
    public BaseResponse<T> getOneR(T t) {
        T one = getOne(t);
        if(t == null){
            return new BaseResponse<>(false, SysMsg.GET_FAIL.getMsg());
        }else {
            return new BaseResponse<>(one,SysMsg.GET_SUCCESS.getMsg());
        }
    }

    @Override
    public BaseResponse<List<T>> findAllR() {
        return new BaseResponse<>(findAll());
    }

    @Override
    public BaseResponse<List<T>> findByParamsR(T t) {
        return new BaseResponse<>(findByParams(t));
    }

    @Override
    public BaseResponse<List<T>> findByParamsR(T t, boolean isFuzzyQueryOpened) {
        return new BaseResponse<>(findByParams(t,isFuzzyQueryOpened));
    }

    @Override
    public BaseResponse<Void> saveR(T t) {
        int num = save(t);
        if(num > 0){
            return new BaseResponse<>(true,SysMsg.SAVE_SUCCESS.getMsg());
        }
        return new BaseResponse<>(false,SysMsg.SAVE_FAIL.getMsg());
    }

    @Override
    public BaseResponse<Void> saveAllR(List<T> l) {
        return null;
    }

    @Override
    public BaseResponse<Void> updateR(T t) {
        int num = update(t);
        if(num > 0){
            return new BaseResponse<>(true,SysMsg.UPDATE_SUCCESS.getMsg());
        }
        return new BaseResponse<>(false,SysMsg.UPDATE_FAIL.getMsg());
    }

    @Override
    public BaseResponse<Void> updateAllR(T t) {
        return null;
    }

    @Override
    public int delete(PK pk) {
        return mapper.deleteByPrimaryKey(pk);
    }

    @Override
    public BaseResponse<Void> deleteR(PK pk) {
        int num = delete(pk);
        if(num > 0){
            return new BaseResponse<>(true,SysMsg.DEL_SUCCESS.getMsg());
        }
        return new BaseResponse<>(false,SysMsg.DEL_FAIL.getMsg());
    }
}
