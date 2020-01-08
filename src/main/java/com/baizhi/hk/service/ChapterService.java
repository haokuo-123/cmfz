package com.baizhi.hk.service;

import com.baizhi.hk.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    Map<String,Object> findAll(Integer page,Integer rows,String albumId);
    String add(Chapter chapter);
    void edit(Chapter chapter);

}
