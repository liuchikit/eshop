package com.zhumian.vo.res.system.right;

import com.zhumian.vo.res.BaseResponse;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhumian
 * @Description
 * @date 2017/12/10 10:42
 */
@Data
public class RightResponse extends BaseResponse implements Serializable{

    private static final long serialVersionUID = -3110500040186087525L;

    private Long id;

    private Long pid;

    private String rightName;

    private String rightCode;

    private Integer rightType;

    private Integer level;

    private Integer sort;

    private Integer status;
}
