package com.baizhi.hk.controller;

import com.baizhi.hk.dao.*;
import com.baizhi.hk.entity.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("onePage")
@RestController
public class OnePageController {
    @Autowired
    BannerDao bannerDao;
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ArticleDao articleDao;
    @Autowired
    UgDao ugDao;
    @Autowired
    GuruDao guruDao;
    @RequestMapping("onePage")
    public Map onePage(String uid,String type,String sub_type){
        HashMap hashMap = new HashMap();
        try {
            if (type.equals("all")){
                List<Banner> banners = bannerDao.queryBannersByTime();
                List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
                List<Article> articles = articleDao.selectAll();
                hashMap.put("status",200);
                hashMap.put("head",banners);
                hashMap.put("albums",albums);
                hashMap.put("articles",articles);
            }else if (type.equals("wen")){
                List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
                hashMap.put("status",200);
                hashMap.put("albums",albums);
            }else {
                if (sub_type.equals("ssyj")){
                    Ug ug1 = new Ug();
                    ug1.setUserId(uid);
                    List<Article> articles = new ArrayList<>();
                    List<Ug> list = ugDao.select(ug1);
                    for (Ug ug2 : list) {
                        String guruId = ug2.getGuruId();
                        Article article = new Article();
                        article.setGuruId(guruId);
                        List<Article> list1 = articleDao.select(article);
                        for (Article article1 : list1) {
                            articles.add(article1);
                        }
                    }
                    hashMap.put("status",200);
                    hashMap.put("articles",articles);
                }else {
                    List<Article> articles = articleDao.selectAll();
                    hashMap.put("status",200);
                    hashMap.put("articles",articles);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }

        return hashMap;
    }


}
