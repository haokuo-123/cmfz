package com.baizhi.hk.service;

import com.baizhi.hk.entity.Article;
import com.baizhi.hk.entity.Banner;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    Map<String,Object> findAll(Integer page, Integer rows);
    void add(Article article);
    void edit(Article article);
    void delete(List<String> list);
    void del(String id);
}
