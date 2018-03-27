package com.zhumian.vo.res.system;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhumian
 * @Description
 * @date 2017/12/8 12:14
 */
@Data
public class RolePageResponse implements Serializable{

    private static final long serialVersionUID = -470706836293705492L;

    private Long id;

    private String roleName;

    private String roleCode;

    private String roleDesc;

    private Integer status;




}
