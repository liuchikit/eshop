package com.zhumian.service.system;


import com.zhumian.entity.system.Right;
import com.zhumian.service.base.BaseService;
import com.zhumian.vo.req.system.right.RightQueryRequest;
import com.zhumian.vo.req.system.right.RightSaveOrUpdateRequest;
import com.zhumian.vo.res.BaseResponse;

/**
 * @author zhumian
 * @Description
 * @date 2017/12/11 21:40
 */
public interface RightService extends BaseService<Right,Long> {

    /**
     * 保存
     * @param request
     * @return
     */
    BaseResponse save(RightSaveOrUpdateRequest request);

    /**
     * 更新
     * @param request
     * @return
     */
    BaseResponse update(RightSaveOrUpdateRequest request);



    /**
     * 查询权限
     * @param request
     * @return
     */
    BaseResponse queryRights(RightQueryRequest request);

    /**
     * 根据type清空缓存
     * @param type
     */
    void clearCache(Integer type);


}
