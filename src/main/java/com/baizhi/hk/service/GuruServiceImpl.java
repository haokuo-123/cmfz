package com.baizhi.hk.service;

import com.baizhi.hk.aspect.Log;
import com.baizhi.hk.dao.GuruDao;
import com.baizhi.hk.entity.Guru;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class GuruServiceImpl implements GuruService {
    @Autowired
    private GuruDao guruDao;
    @Override
    public List<Guru> findAll() {
        return guruDao.selectAll();
    }

    @Override
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Guru guru = new Guru();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Guru> gurus = guruDao.selectByRowBounds(guru, rowBounds);
        int count = guruDao.selectCount(guru);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",gurus);
        map.put("total",count%rows == 0?count / rows : count / rows+1);
        map.put("record",count);
        return map;
    }

    @Override
    @Log(name = "上师的添加")
    public String add(Guru guru) {
        guru.setId(UUID.randomUUID().toString());
        int i = guruDao.insertSelective(guru);
        if(i==0){
            throw new RuntimeException("添加失败");
        }
        return guru.getId();
    }

    @Override
    @Log(name = "上师的修改")
    public void edit(Guru guru) {
        if ("".equals(guru.getPhoto())) {
            guru.setPhoto(null);
        }
        guruDao.updateByPrimaryKeySelective(guru);
    }

    @Override
    @Log(name = "上师的删除")
    public void delete(List<String> list) {
        int i = guruDao.deleteByIdList(list);
        if(i==0){
            throw new RuntimeException("删除失败");
        }
    }
}
