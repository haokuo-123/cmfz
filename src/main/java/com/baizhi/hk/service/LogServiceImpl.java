package com.baizhi.hk.service;

import com.baizhi.hk.dao.LogDao;
import com.baizhi.hk.entity.EntityLog;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;
    @Override
    public Map<String, Object> findAll(Integer page, Integer rows) {
        EntityLog log = new EntityLog();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<EntityLog> logs = logDao.selectByRowBounds(log, rowBounds);
        int count = logDao.selectCount(log);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",logs);
        map.put("total",count%rows == 0?count / rows : count / rows+1);
        map.put("record",count);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String add(EntityLog log) {
        int i = logDao.insertSelective(log);
        if(i==0){
            throw new RuntimeException("添加失败");
        }
        return log.getId();
    }

}
