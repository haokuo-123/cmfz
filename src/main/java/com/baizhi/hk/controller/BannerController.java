package com.baizhi.hk.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.baizhi.hk.dao.BannerDao;
import com.baizhi.hk.entity.Banner;
import com.baizhi.hk.service.BannerService;
import com.baizhi.hk.util.BannerListener;
import com.baizhi.hk.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BannerDao bannerDao;
    @RequestMapping("findAll")
    public Map<String,Object> findAll(Integer page,Integer rows){
        Map<String, Object> map = bannerService.findAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public Map<String,Object> edit(String oper, Banner banner, HttpServletRequest request,String[] id){
        Map<String, Object> map = new HashMap<>();
        try {
            if("add".equals(oper)){
                String ids = bannerService.add(banner);
                map.put("message",ids);
            }
            if("edit".equals(oper)){
                bannerService.edit(banner);
            }
            if("del".equals(oper)){
                bannerService.delete(Arrays.asList(id));
            }
            map.put("status",true);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile cover,String id,HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try {
            cover.transferTo(new File(request.getServletContext().getRealPath("/banner/image/"),cover.getOriginalFilename()));
            Banner banner = new Banner();
            banner.setId(id);
            banner.setCover(cover.getOriginalFilename());
            bannerService.edit(banner);
            map.put("status", true);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("status", false);
        }
        return map;
    }
    @RequestMapping("export")
    public void export(HttpServletResponse response) throws IOException {
        List<Banner> banners = bannerDao.selectAll();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("轮播图", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(),Banner.class).sheet("轮播图信息").doWrite(banners);
    }
  @RequestMapping("put")
  public void put(MultipartFile file,HttpServletRequest request){
          String http = HttpUtil.getHttp(file,request,"/banner/image/");
      String[] split = http.split("/");
      String name = split[split.length-1];
      String realPath = request.getServletContext().getRealPath("/banner/image/"+name);
          EasyExcel.read(realPath, Banner.class, new BannerListener(bannerService)).sheet().doRead();
  }
}
