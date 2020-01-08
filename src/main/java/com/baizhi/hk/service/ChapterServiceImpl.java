package com.baizhi.hk.service;

import com.baizhi.hk.aspect.Log;
import com.baizhi.hk.dao.ChapterDao;
import com.baizhi.hk.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Override
    public Map<String, Object> findAll(Integer page, Integer rows, String albumId) {
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Chapter> list = chapterDao.selectByRowBounds(chapter, rowBounds);
        int count = chapterDao.selectCount(chapter);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    @Log(name = "章节的添加")
    public String add(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString());
        int i = chapterDao.insertSelective(chapter);
        if(i==0){
            throw new RuntimeException("添加失败");
        }
        return chapter.getId();
    }

    @Override
    @Log(name = "章节的修改")
    public void edit(Chapter chapter) {
        if ("".equals(chapter.getUrl())) {
            chapter.setUrl(null);
        }
        int i = chapterDao.updateByPrimaryKeySelective(chapter);
        if(i==0){
            throw new RuntimeException("修改失败");
        }
    }
}
