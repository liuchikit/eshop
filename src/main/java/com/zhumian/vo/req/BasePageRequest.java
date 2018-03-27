package com.zhumian.vo.req;

import lombok.Data;

import java.io.Serializable;


@Data
public class BasePageRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = -2609061719299047120L;

    private Integer start;

    private Integer length;

    private Integer draw;

    public BasePageRequest(){
        this.start = 1;
        this.length = 10;
    }

    public void setStart(Integer start) {
        this.start =start < 0 ? 1 : start;
    }

    public Integer getStart() {
        return start < 0 ? start  : (start / length + 1);
    }



}
