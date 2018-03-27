package com.zhumian.mapper.system;


import com.zhumian.config.mybatis.BaseMapper;
import com.zhumian.entity.system.Role;

import java.util.Map;

public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 角色解绑所有权限
     * @param roleId
     * @return
     */
    int unrelateRoleAllRightByRoleId(Long roleId);

    /**
     * 绑定权限
     * @param map
     * @return
     */
    int relateRights(Map map);
}
