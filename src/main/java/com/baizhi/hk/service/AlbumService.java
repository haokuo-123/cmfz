package com.baizhi.hk.service;

import com.baizhi.hk.entity.Album;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface AlbumService {
    Map<String,Object> findAll(Integer page,Integer rows);
    String add(Album album);
    void edit(Album album);
    Album findOne(String id);
    void del(String id, HttpServletRequest request);
}
