package com.zhumian.controller.system;

import com.zhumian.entity.system.Right;
import com.zhumian.service.system.RightService;
import com.zhumian.vo.req.system.right.RightQueryRequest;
import com.zhumian.vo.req.system.right.RightSaveOrUpdateRequest;
import com.zhumian.vo.res.BaseResponse;
import com.zhumian.vo.res.system.right.RightResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/system/right")
public class RightController {

    private static final String PATH = "/right";

    @Autowired
    private RightService rightService;

    /**
     * 跳转到权限管理列表
     * @return
     */
    @RequestMapping(value = "/toRightList",method = RequestMethod.GET)
    public ModelAndView toRightList(){
        return new ModelAndView(PATH + "/rightList");
    }

    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById",method = RequestMethod.GET)
    public BaseResponse<Right> getById(Long id){
        return rightService.getByIdR(id);
    }

    /**
     * 保存
     * @param request
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    public BaseResponse save(RightSaveOrUpdateRequest request){
        return rightService.save(request);
    }


    /**
     * 更新
     * @param request
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public BaseResponse update(RightSaveOrUpdateRequest request){
        return rightService.update(request);
    }

    @RequestMapping(value = "clearCache",method = RequestMethod.GET)
    public void clearCache(Integer type){
        rightService.clearCache(type);
    }

    /**
     * 根据type查询权限
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryRights",method = RequestMethod.GET)
    public BaseResponse queryRights(RightQueryRequest request){
        return rightService.queryRights(request);
    }
}
