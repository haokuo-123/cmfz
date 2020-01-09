package com.baizhi.hk.service;

import com.baizhi.hk.aspect.AddOrSelectCache;
import com.baizhi.hk.aspect.Log;
import com.baizhi.hk.dao.BannerDao;
import com.baizhi.hk.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    @AddOrSelectCache
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Banner banner = new Banner();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Banner> banners = bannerDao.selectByRowBounds(banner, rowBounds);
        int count = bannerDao.selectCount(banner);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", banners);
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);
        map.put("records", count);
        return map;
    }

    @Override
    @Log(name = "轮播图添加")
    public String add(Banner banner) {
        banner.setId(UUID.randomUUID().toString());
        banner.setCreateDate(new Date());
        int i = bannerDao.insert(banner);
        if (i == 0) {
            throw new RuntimeException("添加失败");
        }
        return banner.getId();
    }

    @Override
    @Log(name = "轮播图修改")
    public void edit(Banner banner) {
        if ("".equals(banner.getCover())) {
            banner.setCover(null);
        }
        try {
            bannerDao.updateByPrimaryKeySelective(banner);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }

    }



    @Override
    @Log(name = "轮播图删除")
    public void delete(List<String> list) {
        int i = bannerDao.deleteByIdList(list);
        if(i==0){
            throw new RuntimeException("删除失败");
        }
    }
}
