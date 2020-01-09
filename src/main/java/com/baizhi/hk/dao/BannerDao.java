package com.baizhi.hk.dao;

import com.baizhi.hk.cache.MyBatisCache;
import com.baizhi.hk.entity.Banner;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
//@CacheNamespace(implementation = MyBatisCache.class)
public interface BannerDao extends Mapper<Banner>, DeleteByIdListMapper<Banner,String> {
    List<Banner> queryBannersByTime();
}
