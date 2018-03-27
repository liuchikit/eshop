package com.zhumian.vo.res;

import com.zhumian.constant.SysMsg;
import lombok.Data;

import java.io.Serializable;
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 7755259851405547419L;

    private Boolean success;

    private Integer code;

    private String msg;

    private T data;

    public BaseResponse(){
        this.success = true;
        this.msg = SysMsg.DO_SUCCESS.getMsg();
    }

    public BaseResponse(boolean success,String msg){
        this.success = success;
        this.msg = msg;
    }

    public BaseResponse(T data){
        this.success = true;
        this.data = data;
    }

    public BaseResponse(T data,String msg){
        this.data = data;
        this.msg = msg;
        this.success = true;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }
}
