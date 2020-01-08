package com.baizhi.hk.service;

import com.baizhi.hk.entity.EntityLog;
import java.util.Map;

public interface LogService {
    Map<String,Object> findAll(Integer page, Integer rows);
    String add(EntityLog log);
}
