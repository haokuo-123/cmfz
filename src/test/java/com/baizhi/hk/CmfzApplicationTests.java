package com.baizhi.hk;

import com.baizhi.hk.dao.AdminDao;
import com.baizhi.hk.dao.BannerDao;
import com.baizhi.hk.entity.Admin;
import com.baizhi.hk.entity.Banner;
import com.baizhi.hk.service.BannerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SpringBootTest
@RunWith(SpringRunner.class)
class CmfzApplicationTests {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BannerDao bannerDao;

    @Test
    void contextLoads() {
    }
}


