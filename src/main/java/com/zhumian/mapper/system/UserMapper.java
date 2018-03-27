package com.zhumian.mapper.system;


import com.zhumian.config.mybatis.BaseMapper;
import com.zhumian.entity.system.Right;
import com.zhumian.entity.system.Role;
import com.zhumian.entity.system.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户查询角色列表
     * @param map
     * @return
     */
    List<Role> queryRolesByUser(Map map);

    /**
     * 根据用户查询权限列表
     * @param map
     * @return
     */
    List<Right> queryRightsByUser(Map map);
}
