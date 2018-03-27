package com.zhumian.service.system;


import com.zhumian.entity.system.User;
import com.zhumian.service.base.BaseService;
import com.zhumian.vo.req.system.LoginRequest;
import com.zhumian.vo.req.system.user.QueryRolesOrRightsRequest;
import com.zhumian.vo.req.system.user.UserSaveOrUpdateRequest;
import com.zhumian.vo.res.BaseResponse;
import com.zhumian.vo.res.system.RoleResponse;
import com.zhumian.vo.res.system.right.RightResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface UserService extends BaseService<User,Long> {

    /**
     * 登录
     * @param request
     * @return
     */
    BaseResponse login(LoginRequest request, HttpServletRequest servletRequest);

    /**
     * 根据用户查询角色列表
     * @param request
     * @return
     */
    BaseResponse<List<RoleResponse>> queryRolesByUser(QueryRolesOrRightsRequest request);

    /**
     * 根据用户查询权限列表
     * @param request
     * @return
     */
    BaseResponse<List<RightResponse>> queryRightsByUser(QueryRolesOrRightsRequest request);

    /**
     * 保存
     * @param request
     * @return
     */
    BaseResponse save(UserSaveOrUpdateRequest request);

    /**
     * 更新
     * @param request
     * @return
     */
    BaseResponse update(UserSaveOrUpdateRequest request);

    /**
     * 删除
     * @param id
     * @return
     */
    BaseResponse<Void> del(Long id);

    BaseResponse getResById(Long id);
}
