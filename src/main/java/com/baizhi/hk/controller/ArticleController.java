package com.baizhi.hk.controller;

import com.baizhi.hk.entity.Article;
import com.baizhi.hk.service.ArticleService;
import com.baizhi.hk.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @RequestMapping("findAll")
    public Map<String,Object> findAll(Integer page,Integer rows){
        Map<String, Object> map = articleService.findAll(page, rows);
        return map;
    }

    @RequestMapping("uploadImg")
    public Map<String,Object> uploadImg(MultipartFile imgFile, HttpSession session, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String realPath = session.getServletContext().getRealPath("/article/articleImg/");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
            try {
                String http = HttpUtil.getHttp(imgFile,request,"/article/articleImg/");
                map.put("error",0);
                map.put("url",http);
            } catch (Exception e) {
                map.put("error",1);
                e.printStackTrace();
            }

        return map;
    }
    @RequestMapping("showAllImg")
    public Map<String,Object> showAllImg(HttpServletRequest request,HttpSession session){
        Map<String, Object> map = new HashMap<>();
        map.put("current_url",request.getContextPath()+"/article/articleImg/");
        String realPath = session.getServletContext().getRealPath("/article/articleImg/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        map.put("total_count",files.length);
        ArrayList<Object> arrayList = new ArrayList<>();
        for (File file1 : files) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("is_dir",false);
            map1.put("has_file",false);
            map1.put("filesize",file1.length());
            map1.put("is_photo",true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            map1.put("filetype",extension);
            map1.put("filename",name);
            //通过字符串拆分获取时间戳
            String time = name.split("_")[0];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            map1.put("datetime",format);
            arrayList.add(map1);
        }
        map.put("file_list",arrayList);
        return map;
    }
    @RequestMapping("insertArticle")
    public String insertArticle(Article article, MultipartFile inputfile,HttpSession session,HttpServletRequest request){
        if(article.getId()==null || "".equals(article.getId())){

            String realPath = session.getServletContext().getRealPath("/article/image/");
            File file = new File(realPath);
            if (!file.exists()){
                file.mkdirs();
            }
            // 网络路径
            String http = HttpUtil.getHttp(inputfile, request, "/article/image/");

            article.setId(article.getId());
            article.setImg(http);
            articleService.add(article);

        }else {
            articleService.edit(article);
        }
        return "ok";
    }
    @RequestMapping("del")
    public void del(Article article){
        articleService.del(article.getId());
    }
}
