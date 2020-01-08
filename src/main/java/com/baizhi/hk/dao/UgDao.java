package com.baizhi.hk.dao;
import com.baizhi.hk.entity.Ug;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface UgDao extends Mapper<Ug>, DeleteByIdListMapper<Ug,String> {
}
