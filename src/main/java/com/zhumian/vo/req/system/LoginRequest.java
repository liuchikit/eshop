package com.zhumian.vo.req.system;

import com.zhumian.vo.req.BaseRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRequest extends BaseRequest implements Serializable{

    private static final long serialVersionUID = 1123877332577671558L;

    private String account;

    private String password;
}
