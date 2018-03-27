package com.zhumian.vo.res.system.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhumian
 * @Description
 * @date 2017/12/14 11:40
 */
@Data
public class UserResponse implements Serializable {

    private static final long serialVersionUID = -2001206390868910538L;

    private Long id;

    private String account;

    private String name;

    private String password;

    private Date birth;

    private String email;

    private String phone;

    private Long deptId;

    private Long posId;
}
