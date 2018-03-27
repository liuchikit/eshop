package com.zhumian.vo.req.system.role;

import com.zhumian.vo.req.BasePageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhumian
 * @Description
 * @date 2017/12/8 11:46
 */
@Data
public class RolePageRequest extends BasePageRequest implements Serializable {

    private String roleName;

    private String roleCode;
}
