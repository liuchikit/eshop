package com.zhumian.vo.res.system.right;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhumian
 * @Description
 * @date 2017/12/12 19:57
 */
@Data
public class TreeViewResponse implements Serializable{

    private static final long serialVersionUID = 4207445727672332174L;

    private Long id;
    private String text;
    private String url;
    private Integer checked;
    private TreeViewResponseState state;

    private List<TreeViewResponse> nodes;

    public TreeViewResponse(){
        this.checked = 0;
        TreeViewResponseState state = new TreeViewResponseState();
        state.setChecked(false);
        this.state = state;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
        if(this.checked == 1){
           this.state.setChecked(true);
        }

    }

}

@Data
class TreeViewResponseState implements Serializable{
    private static final long serialVersionUID = -3703262346562186236L;
    private boolean checked;
}
