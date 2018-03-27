package com.zhumian.vo.req.system.right;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhumian
 * @Description
 * @date 2017/12/16 16:34
 */
@Data
public class RightQueryRequest implements Serializable{

    private static final long serialVersionUID = 5853104940050025977L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;


    /**
     * 父权限ID
     */
    private Long pid;

    /**
     * 权限ID
     */
    private Long rightId;

    /**
     * 1：查询所有权限 2 ：查询菜单 3：查询用户所有权限 4：查询角色权限
     */
    private Integer type;
}
