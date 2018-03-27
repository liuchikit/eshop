package com.zhumian.vo.req.system.user;

import com.zhumian.vo.req.BaseRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhumian
 * @Description
 * @date 2017/12/10 10:30
 */
@Data
public class QueryRolesOrRightsRequest extends BaseRequest implements Serializable{

    private static final long serialVersionUID = 6647998668231551099L;

    private Long id;

    private String account;
}
