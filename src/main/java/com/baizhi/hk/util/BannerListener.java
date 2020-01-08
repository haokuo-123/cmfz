package com.baizhi.hk.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.hk.entity.Banner;
import com.baizhi.hk.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class BannerListener extends AnalysisEventListener<Banner> {

    public BannerListener(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    private BannerService bannerService;
    List<Banner> banners = new ArrayList<Banner>();
    @Override
    public void invoke(Banner banner, AnalysisContext analysisContext) {
        banners.add(banner);
        bannerService.add(banner);
        System.out.println(banner);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println(banners);
    }
}
