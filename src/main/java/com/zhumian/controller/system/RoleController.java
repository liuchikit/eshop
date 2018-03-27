package com.zhumian.controller.system;

import com.zhumian.entity.system.Role;
import com.zhumian.service.system.RoleService;
import com.zhumian.vo.req.system.role.RolePageRequest;
import com.zhumian.vo.req.system.role.RoleRelateRightRequest;
import com.zhumian.vo.req.system.role.RoleSaveOrUpdateRequest;
import com.zhumian.vo.res.BasePageResponse;
import com.zhumian.vo.res.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/system/role")
public class RoleController {

    private static final String PATH = "/role";

    @Autowired
    private RoleService roleService;

    /**
     * 跳转到角色管理列表
     * @return
     */
    @RequestMapping(value = "/toRoleList",method = RequestMethod.GET)
    public ModelAndView toRoleList(){
        return new ModelAndView(PATH + "/roleList");
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public BasePageResponse list(RolePageRequest request){
        return roleService.list(request);
    }

    @RequestMapping(value = "/getById",method = RequestMethod.GET)
    public BaseResponse<Role> getById(Long id){
        return roleService.getByIdR(id);
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public BaseResponse save(RoleSaveOrUpdateRequest request){
        return roleService.save(request);
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    public BaseResponse update(RoleSaveOrUpdateRequest request){
        return roleService.update(request);
    }

    @RequestMapping(value = "",method = RequestMethod.DELETE)
    public BaseResponse<Void> del(Long id){
        return roleService.deleteR(id);
    }

    /**
     * 绑定权限
     * @param request
     * @return
     */
    @RequestMapping(value = "/relateRights",method = RequestMethod.POST)
    public BaseResponse relateRights(RoleRelateRightRequest request){
        return roleService.relateRights(request);
    }
}
