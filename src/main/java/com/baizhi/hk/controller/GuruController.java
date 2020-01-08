package com.baizhi.hk.controller;

import com.baizhi.hk.entity.Guru;
import com.baizhi.hk.service.GuruService;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("guru")
public class GuruController {
    @Autowired
    private GuruService guruService;
    @RequestMapping("findAll")
    public List<Guru> findAll(){
        List<Guru> list = guruService.findAll();
        return list;
    }
    @RequestMapping("findAllGuru")
    public Map<String,Object> findAllGuru(Integer page,Integer rows){
        Map<String, Object> map = guruService.findAll(page, rows);
        return map;
    }
    @RequestMapping("edit")
    public Map<String,Object> edit(String oper,Guru guru,String[] id){
        Map<String,Object> map = new HashMap<>();
        try {
            if("add".equals(oper)){
                String ids = guruService.add(guru);
                map.put("message",ids);
            }
            if("edit".equals(oper)){
                guruService.edit(guru);
            }
            if("del".equals(oper)){
                guruService.delete(Arrays.asList(id));
            }
            map.put("status",true);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());

        }
        return map;
    }
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile photo, String id,HttpSession session, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String realPath = session.getServletContext().getRealPath("/guru/image/");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        // 网络路径
        String http = HttpUtil.getHttp(photo, request, "/guru/image/");
        Guru guru = new Guru();
        guru.setId(id);
        guru.setPhoto(http);
        guruService.edit(guru);
        map.put("status",200);
        return map;
    }
}
