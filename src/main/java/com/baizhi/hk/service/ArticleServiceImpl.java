package com.baizhi.hk.service;

import com.baizhi.hk.aspect.Log;
import com.baizhi.hk.dao.ArticleDao;
import com.baizhi.hk.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Override
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Article article = new Article();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Article> list = articleDao.selectByRowBounds(article, rowBounds);
        int count = articleDao.selectCount(article);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("records",count);
        return map;
    }

    @Override
    @Log(name = "文章的添加")
    public void add(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateDate(new Date());
        article.setPublishDate(new Date());
       articleDao.insertSelective(article);
    }

    @Override
    @Log(name = "文章的修改")
    public void edit(Article article) {
        if ("".equals(article.getImg())) {
            article.setImg(null);
        }
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    @Log(name = "文章的删除")
    public void delete(List<String> list) {
        int i = articleDao.deleteByIdList(list);
        if(i==0){
            throw new RuntimeException("删除失败");
        }
    }

    @Override
    public void del(String id) {
        int i = articleDao.deleteByPrimaryKey(id);
        if(i == 0) {
            throw new RuntimeException("删除失败");
        }
    }
}
