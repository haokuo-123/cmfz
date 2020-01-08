package com.baizhi.hk.service;

import com.baizhi.hk.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    Map<String,Object> findAll(Integer page,Integer rows);
    String add(Banner banner);
    void edit(Banner banner);
    void delete(List<String> list);
}
