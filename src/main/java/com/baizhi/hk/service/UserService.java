package com.baizhi.hk.service;

import com.baizhi.hk.entity.User;

import java.util.Map;

public interface UserService {
    Map<String,Object> findAll(Integer page, Integer rows);
    String add(User user);
    void edit(User user);
}
