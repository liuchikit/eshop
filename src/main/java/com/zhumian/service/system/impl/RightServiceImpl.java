package com.zhumian.service.system.impl;


import com.zhumian.config.mybatis.BaseMapper;
import com.zhumian.entity.system.Right;
import com.zhumian.entity.system.RightVO;
import com.zhumian.entity.system.User;
import com.zhumian.mapper.system.RightMapper;
import com.zhumian.service.base.BaseServiceImpl;
import com.zhumian.service.system.RightService;
import com.zhumian.util.ConvertUtil;
import com.zhumian.vo.req.system.right.RightQueryRequest;
import com.zhumian.vo.req.system.right.RightSaveOrUpdateRequest;
import com.zhumian.vo.res.BaseResponse;
import com.zhumian.vo.res.system.right.TreeViewResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author zhumian
 * @Description
 * @date 2017/12/11 22:20
 */
@Service
public class RightServiceImpl extends BaseServiceImpl<Right,Long> implements RightService {

    private final static Logger logger = LoggerFactory.getLogger(RightServiceImpl.class);

    @Autowired
    private RightMapper mapper;

    @Autowired
    @Override
    public void setMapper(BaseMapper<Right> mapper) {
        super.mapper = mapper;
    }

    /**
     * 保存
     *
     * @param request
     * @return
     */
    @Override
    @CachePut(value = "redis",key = "'right:save' + #request.getRightCode()")
    public BaseResponse save(RightSaveOrUpdateRequest request) {
        Right right = new Right();
        ConvertUtil.objectToObject(request,right,true,true,true);
        return saveR(right);
    }

    /**
     * 更新
     *
     * @param request
     * @return
     */
    @Override
    @CachePut(value = "redis",key = "'right:save' + #request.getRightCode()")
    public BaseResponse update(RightSaveOrUpdateRequest request) {
        Right right = new Right();
        ConvertUtil.objectToObject(request,right,true,false,true);
        return updateR(right);
    }

    /**
     * 查询权限
     *
     * @param request
     * @return
     */
    @Override
    @Cacheable(value = "redis",key = "'right:' + #request")
    public BaseResponse queryRights(RightQueryRequest request) {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User)session.getAttribute("user");
        Map map = new HashMap();

        //查询所有权限
        if(Objects.equals(request.getType(),1)){
            map.put("level",0);
        }
        //查询菜单树
        if(Objects.equals(request.getType(),2)){
            map.put("level",1);
            map.put("userId",user.getId());
            map.put("rightType",1);
        }
        //查询用户所有权限
        if(Objects.equals(request.getType(),3)){
            map.put("level",0);
            //若有传入用户ID，则根据用户ID查询权限，否则根据当前登录用户ID查询权限
            map.put("userId",request.getUserId() == null ? user.getId() : request.getUserId());
        }
        //查询角色所有权限
        if(Objects.equals(request.getType(),4)){
            map.put("level",0);
            map.put("roleId",request.getRoleId());
        }

        List<RightVO> rights = mapper.queryRights(map);
        List<TreeViewResponse> trees = new ArrayList<>();
        for(RightVO right : rights){
            TreeViewResponse tree = new TreeViewResponse();
            tree.setId(right.getId());
            tree.setText(right.getRightName());
            tree.setUrl(right.getRightCode());

            //查询用户权限时设置勾选状态
            if(Objects.equals(request.getType(),3)){
                if(!Objects.equals(right.getUserID(),null)){
                    tree.setChecked(1);
                }
            }
            //查询角色权限时设置勾选状态
            if(Objects.equals(request.getType(),4)){
                if(!Objects.equals(right.getRoleId(),null)){
                    tree.setChecked(1);
                }
            }
            //设置父权限ID
            request.setPid(right.getId());
            tree.setNodes(querySubRights(request));
            trees.add(tree);
        }
        return new BaseResponse(trees);
    }

    private List<TreeViewResponse> querySubRights(RightQueryRequest request){
        Map<String,Object> map = new HashMap();
        //查询菜单
        if(Objects.equals(request.getType(),2)){
            map.put("userId",request.getUserId());
            map.put("rightType",1);
        }
        //查询用户权限
        if(Objects.equals(request.getType(),3)){
            map.put("userId",request.getUserId());
        }
        //查询角色权限
        if(Objects.equals(request.getType(),4)){
            map.put("roleId",request.getRoleId());
        }

        map.put("pid",request.getPid());
        List<RightVO> rights =  mapper.queryRights(map);
        List<TreeViewResponse> trees = new ArrayList<>();
        for(RightVO right : rights){
            TreeViewResponse tree = new TreeViewResponse();
            tree.setId(right.getId());
            tree.setText(right.getRightName());
            tree.setUrl(right.getRightCode());
            //查询用户权限时设置勾选状态
            if(Objects.equals(request.getType(),3)){
                if(!Objects.equals(right.getUserID(),null)){
                    tree.setChecked(1);
                }
            }
            //查询角色权限时设置勾选状态
            if(Objects.equals(request.getType(),4)){
                if(!Objects.equals(right.getRoleId(),null)){
                    tree.setChecked(1);
                }
            }
            //设置父权限ID
            request.setPid(right.getId());
            tree.setNodes(querySubRights(request));
            trees.add(tree);
        }
        return trees;
    }

    /**
     * 根据type清空缓存
     *
     * @param type
     */
    @Override
    @CacheEvict(value="redis",key="'right:clearCache' + #type")
    public void clearCache(Integer type) {
        logger.debug("清空 type：" + type + "的缓存");
    }
}
