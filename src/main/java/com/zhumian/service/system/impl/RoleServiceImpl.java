package com.zhumian.service.system.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhumian.config.mybatis.BaseMapper;
import com.zhumian.entity.system.Role;
import com.zhumian.mapper.system.RoleMapper;
import com.zhumian.service.base.BaseServiceImpl;
import com.zhumian.service.system.RoleService;
import com.zhumian.util.ConvertUtil;
import com.zhumian.vo.req.system.role.RolePageRequest;
import com.zhumian.vo.req.system.role.RoleRelateRightRequest;
import com.zhumian.vo.req.system.role.RoleSaveOrUpdateRequest;
import com.zhumian.vo.res.BasePageResponse;
import com.zhumian.vo.res.BaseResponse;
import com.zhumian.vo.res.system.RolePageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class RoleServiceImpl extends BaseServiceImpl<Role,Long> implements RoleService {

    @Autowired
    private RoleMapper mapper;

    @Autowired
    @Override
    public void setMapper(BaseMapper<Role> mapper) {
        super.mapper = mapper;
    }


    @Override
    public BaseResponse save(RoleSaveOrUpdateRequest request) {
        Role role = new Role();
        ConvertUtil.objectToObject(request,role,true,true,true);
        BaseResponse validate = validate(role);
        if(validate.getSuccess()){
            save(role);
            return new BaseResponse();
        }
        return validate;
    }

    @Override
    public BaseResponse update(RoleSaveOrUpdateRequest request) {
        Role role = new Role();
        ConvertUtil.objectToObject(request,role,true,true,true);
        BaseResponse validate = validate(role);
        if(validate.getSuccess()){
            return updateR(role);
        }
        return validate;

    }

    /**
     * 校验角色名称编码唯一
     * @param role
     * @return
     */
    private BaseResponse validate(Role role){
        String msg = "";
        boolean flag = true;

        Role name = new Role();
        name.setRoleName(role.getRoleName());
        List<Role> names = findByParams(name,false);
        if(!names.isEmpty()){
            flag = false;
            msg = "角色名称已被占用";
        }

        Role code = new Role();
        name.setRoleCode(role.getRoleCode());
        List<Role> codes = findByParams(code,false);
        if(!codes.isEmpty()){
            flag = false;
            msg = "角色编码已被占用";
        }

       return new BaseResponse(flag,msg);

    }

    @Override
    public BasePageResponse list(RolePageRequest request) {
        Page<Object> page = PageHelper.startPage(request.getStart(),
                request.getLength());
        Role searcher = new Role();
        ConvertUtil.objectToObject(request,searcher,true);
        List<Role> roles = findByParams(searcher);
        List<RolePageResponse> responses = new ArrayList<>(roles.size());
        ConvertUtil.listObjectToListObject(roles,responses,RolePageResponse.class);
        return new BasePageResponse(request.getDraw(),page.getTotal(),responses);
    }



    /**
     * 绑定权限
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse relateRights(RoleRelateRightRequest request) {
        //先解绑所有权限
        int result = mapper.unrelateRoleAllRightByRoleId(request.getRoleId());
        List<Long> rightIds = Arrays.asList(request.getRightIds());

        //绑定权限
        Map map = new HashMap();
        map.put("roleId",request.getRoleId());
        map.put("list",rightIds);
        mapper.relateRights(map);
        return new BaseResponse();
    }

    @Override
    public BaseResponse del(Long id) {
        return null;
    }
}
