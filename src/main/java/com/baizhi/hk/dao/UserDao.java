package com.baizhi.hk.dao;

import com.baizhi.hk.entity.User;
import com.baizhi.hk.entity.UserDTO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface UserDao extends Mapper<User> , DeleteByIdListMapper<User,String> {
    Integer queryUserByTime(@Param("sex") String sex, @Param("day") Integer day);
    List<UserDTO> queryUserBySex(String sex);
    List<User> queryUserRand();
}
