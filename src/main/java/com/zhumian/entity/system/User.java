package com.zhumian.entity.system;

import com.zhumian.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Data
@Table(name = "tb_user")
public class User extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 2445387885506772057L;

    private String account;

    private String name;

    private String password;

    private Date birth;

    private String email;

    private String phone;

    private Long deptId;

    private Long posId;
}
