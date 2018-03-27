package com.zhumian.mapper.system;


import com.zhumian.config.mybatis.BaseMapper;
import com.zhumian.entity.system.Right;
import com.zhumian.entity.system.RightVO;

import java.util.List;
import java.util.Map;
public interface RightMapper extends BaseMapper<Right> {


    List<RightVO> queryRights(Map map);

}
