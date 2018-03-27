package com.zhumian.service.system;


import com.zhumian.entity.system.Role;
import com.zhumian.service.base.BaseService;
import com.zhumian.vo.req.system.role.RolePageRequest;
import com.zhumian.vo.req.system.role.RoleRelateRightRequest;
import com.zhumian.vo.req.system.role.RoleSaveOrUpdateRequest;
import com.zhumian.vo.res.BasePageResponse;
import com.zhumian.vo.res.BaseResponse;
import com.zhumian.vo.res.system.RoleResponse;


public interface RoleService extends BaseService<Role,Long> {

    /**
     * 保存
     * @return
     */
    BaseResponse save(RoleSaveOrUpdateRequest request);

    /**
     * 更新
     * @return
     */
    BaseResponse update(RoleSaveOrUpdateRequest request);

    /**
     * 分页查询
     * @return
     */
    BasePageResponse list(RolePageRequest rolePageRequest);

    /**
     * 逻辑删除
     * @return
     */
    BaseResponse del(Long id);

    /**
     * 绑定权限
     * @param request
     * @return
     */
    BaseResponse relateRights(RoleRelateRightRequest request);

}
