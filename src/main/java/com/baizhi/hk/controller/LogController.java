package com.baizhi.hk.controller;

import com.baizhi.hk.dao.LogDao;
import com.baizhi.hk.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("log")
public class LogController {
    @Autowired
    private LogService logService;
    @Autowired
    private LogDao logDao;
    @RequestMapping("findAll")
    public Map<String,Object> findAll(Integer page,Integer rows){
        Map<String, Object> map = logService.findAll(page, rows);
        return map;
    }
    @RequestMapping("edit")
    public void edit(String oper,String[] id){
       if("del".equals(oper)) {
           logDao.deleteByIdList(Arrays.asList(id));
       }
    }
}
