package com.zhumian.service.system.impl;


import com.zhumian.config.mybatis.BaseMapper;
import com.zhumian.constant.SysMsg;
import com.zhumian.entity.system.Right;
import com.zhumian.entity.system.Role;
import com.zhumian.entity.system.User;
import com.zhumian.mapper.system.UserMapper;
import com.zhumian.service.base.BaseServiceImpl;
import com.zhumian.service.system.UserService;
import com.zhumian.util.ConvertUtil;
import com.zhumian.vo.req.system.LoginRequest;
import com.zhumian.vo.req.system.user.QueryRolesOrRightsRequest;
import com.zhumian.vo.req.system.user.UserSaveOrUpdateRequest;
import com.zhumian.vo.res.BaseResponse;
import com.zhumian.vo.res.system.RoleResponse;
import com.zhumian.vo.res.system.right.RightResponse;
import com.zhumian.vo.res.system.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService {

    @Resource
    private UserMapper mapper;


    @Autowired
    @Override
    public void setMapper(BaseMapper<User> mapper) {
        super.mapper = mapper;
    }

    @Override
    public BaseResponse login(LoginRequest request, HttpServletRequest servletRequest) {
        User userSearcher = new User();
        ConvertUtil.objectToObject(request,userSearcher,true);
        User user = getOne(userSearcher);
        if(user != null){
            HttpSession session = servletRequest.getSession();
            session.setAttribute("user",user);
            return new BaseResponse();
        }
        return new BaseResponse(false,"登录失败");
    }

    /**
     * 根据用户ID查询角色列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<List<RoleResponse>> queryRolesByUser(QueryRolesOrRightsRequest request) {
        Map map = new HashMap();
        ConvertUtil.objectToMap(request,map);
        List<Role> roles = mapper.queryRolesByUser(map);
        List<RoleResponse> responses = new ArrayList<>(roles.size());
        ConvertUtil.listObjectToListObject(roles,responses,RoleResponse.class);
        return new BaseResponse(responses);
    }

    /**
     * 根据用户查询权限列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<List<RightResponse>> queryRightsByUser(QueryRolesOrRightsRequest request) {
        Map map = new HashMap();
        ConvertUtil.objectToMap(request,map);
        List<Right> rights = mapper.queryRightsByUser(map);
        List<RightResponse> responses = new ArrayList<>(rights.size());
        ConvertUtil.listObjectToListObject(rights,responses,RightResponse.class);
        return new BaseResponse(responses);
    }

    /**
     * 保存
     *
     * @param request
     * @return
     */
    @Override
    @CachePut(value = "redis",key = "'user:' + #request.id")
    public BaseResponse save(UserSaveOrUpdateRequest request) {
        User user = new User();
        ConvertUtil.objectToObject(request,user,true,true,true);
        save(user);
        return new BaseResponse();
    }

    /**
     * 更新
     *
     * @param request
     * @return
     */
    @Override
    @CachePut(value = "redis",key = "'user:' + #request.id")
    public BaseResponse update(UserSaveOrUpdateRequest request) {
        User user = new User();
        ConvertUtil.objectToObject(request,user,true,false,true);
        update(user);
        return new BaseResponse();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    @CacheEvict(value = "redis",key = "'user:' + #id")
    public BaseResponse<Void> del(Long id) {
        return deleteR(id);
    }

    @Override
    @Cacheable(value = "redis",key = "'user:' + #id")
    public BaseResponse getResById(Long id) {
        User user = getById(id);
        if(user != null){
           UserResponse response = new UserResponse();
           ConvertUtil.objectToObject(user,response,true);
           return new BaseResponse(response);
        }
        return new BaseResponse(false, SysMsg.GET_FAIL.getMsg());
    }
}
