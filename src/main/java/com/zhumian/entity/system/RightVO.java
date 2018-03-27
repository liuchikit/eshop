package com.zhumian.entity.system;

import lombok.Data;

import java.io.Serializable;

@Data
public class RightVO implements Serializable{

    private static final long serialVersionUID = -2695367194324539436L;

    private Long id;

    private Long pid;

    private String rightName;

    private String rightCode;

    private Integer rightType;

    private Integer level;

    private Integer sort;

    private Integer status;

    private Long roleId;

    private Long userID;
}
