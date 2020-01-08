package com.baizhi.hk.controller;

import com.baizhi.hk.dao.AlbumDao;
import com.baizhi.hk.entity.Album;
import com.baizhi.hk.service.AlbumService;
import com.baizhi.hk.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private AlbumDao albumDao;

    @RequestMapping("findAll")
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Map<String, Object> map = albumService.findAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public Map<String, Object> edit(String oper, Album album,String[] id) {
        Map<String, Object> map = new HashMap<>();
        try {
            if ("add".equals(oper)) {
                String ids = albumService.add(album);
                map.put("message", ids);
            } else if (oper.equals("edit")) {
               albumService.edit(album);
            }else {
                albumDao.deleteByIdList(Arrays.asList(id));
            }
            map.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", false);
            map.put("message",e.getMessage());
        }
        return map;
    }

    @RequestMapping("upload")
    public Map<String,Object> upload(HttpSession session, MultipartFile cover, String id, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String realPath = session.getServletContext().getRealPath("/album/image/");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        // 网络路径
        String http = HttpUtil.getHttp(cover, request, "/album/image/");
        Album album = new Album();
        album.setId(id);
        album.setCover(http);
        albumService.edit(album);
        map.put("status",200);
        return map;
    }

    }



