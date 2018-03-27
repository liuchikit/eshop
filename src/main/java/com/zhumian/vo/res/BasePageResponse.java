package com.zhumian.vo.res;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
public class BasePageResponse<T extends Serializable> extends BaseResponse implements Serializable{

    private static final long serialVersionUID = 3823299999905419476L;

    private Integer draw;

    private Long recordsTotal;

    private Long recordsFiltered;

    private List<T> data;

    public BasePageResponse(){
        this.data = new ArrayList<>();
    }

    public BasePageResponse(Integer draw, Long recordsFiltered, List<T> data){
        this.draw = draw;
        this.recordsTotal = recordsFiltered;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }
}
