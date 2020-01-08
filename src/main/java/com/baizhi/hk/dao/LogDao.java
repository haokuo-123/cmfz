package com.baizhi.hk.dao;

import com.baizhi.hk.entity.EntityLog;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;


public interface LogDao extends Mapper<EntityLog> , DeleteByIdListMapper<EntityLog,String> {
}
