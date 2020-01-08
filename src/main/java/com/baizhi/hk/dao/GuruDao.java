package com.baizhi.hk.dao;
import com.baizhi.hk.entity.Guru;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface GuruDao extends Mapper<Guru>, DeleteByIdListMapper<Guru,String> {
}
