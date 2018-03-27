package com.zhumian.service.base;


import com.zhumian.vo.res.BaseResponse;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends Serializable,PK extends Serializable> {

    /**
     * 根据ID获取实体
     * @param pk
     * @return
     */
    T getById(PK pk);

    /**
     * 根据ID获取实体
     * @param pk
     * @return
     */
    BaseResponse<T> getByIdR(PK pk);

    /**
     * 获取实体
     * @param t
     * @return
     */
    T getOne(T t);

    /**
     * 获取实体
     * @param t
     * @return
     */
    BaseResponse<T> getOneR(T t);


    /**
     * 查询列表
     * @return
     */
    List<T> findAll();

    /**
     * 查询列表
     * @return
     */
    BaseResponse<List<T>> findAllR();

    /**
     * 根据条件查询列表（默认模糊查询）
     * @param t
     * @return
     */
    List<T> findByParams(T t);

    /**
     * 根据条件查询列表（默认模糊查询）
     * @param t
     * @return
     */
    BaseResponse<List<T>> findByParamsR(T t);

    /**
     * 根据条件查询列表
     * @param t
     * @param isFuzzyQueryOpened
     * @return
     */
    List<T> findByParams(T t, boolean isFuzzyQueryOpened);

    /**
     * 根据条件查询列表
     * @param t
     * @param isFuzzyQueryOpened
     * @return
     */
    BaseResponse<List<T>> findByParamsR(T t,boolean isFuzzyQueryOpened);

    /**
     * 保存实体
     * @param t
     * @return
     */
    int save(T t);

    /**
     * 保存实体
     * @param t
     * @return
     */
    BaseResponse<Void> saveR(T t);

    /**
     * 批量保存实体
     * @param l
     * @return
     */
    int saveAll(List<T> l);

    /**
     * 批量保存实体
     * @param l
     * @return
     */
    BaseResponse<Void> saveAllR(List<T> l);

    /**
     * 部分更新实体
     * @param t
     * @return
     */
    int update(T t);

    /**
     * 部分更新实体
     * @param t
     * @return
     */
    BaseResponse<Void> updateR(T t);

    /**
     * 完全更新实体
     * @param t
     * @return
     */
    int updateAll(T t);

    /**
     * 完全更新实体
     * @param t
     * @return
     */
    BaseResponse<Void> updateAllR(T t);

    /**
     * 根据ID删除实体
     * @param pk
     * @return
     */
    int delete(PK pk);

    /**
     * 根据ID删除实体
     * @param pk
     * @return
     */
    BaseResponse<Void> deleteR(PK pk);

}
