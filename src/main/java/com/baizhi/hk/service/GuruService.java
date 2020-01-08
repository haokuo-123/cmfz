package com.baizhi.hk.service;

import com.baizhi.hk.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    List<Guru> findAll();
    Map<String,Object> findAll(Integer page, Integer rows);
    String add(Guru guru);
    void edit(Guru guru);
    void delete(List<String> list);
}
