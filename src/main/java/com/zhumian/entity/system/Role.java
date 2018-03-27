package com.zhumian.entity.system;

import com.zhumian.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author zhumian
 * @Description
 * @date 2017/12/8 11:48
 */
@Table(name = "tb_role")
@Data
public class Role extends BaseEntity implements Serializable{

    private static final long serialVersionUID = -5539172945479475695L;

    private String roleName;

    private String roleCode;

    private String roleDesc;

    private Integer status;


}
