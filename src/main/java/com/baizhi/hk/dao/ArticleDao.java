package com.baizhi.hk.dao;

import com.baizhi.hk.entity.Article;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface ArticleDao extends Mapper<Article>, DeleteByIdListMapper<Article,String> {
}
