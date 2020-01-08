package com.baizhi.hk.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.baizhi.hk.dao.UserDao;
import com.baizhi.hk.entity.User;
import com.baizhi.hk.entity.UserDTO;
import com.baizhi.hk.service.UserService;
import com.baizhi.hk.util.HttpUtil;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    @RequestMapping("findAll")
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Map<String, Object> map = userService.findAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public Map<String, Object> edit(String oper, User user, String[] id) {
        Map<String, Object> map = new HashMap<>();
        try {
            if ("add".equals(oper)) {
                String ids = userService.add(user);
                map.put("message", ids);

                GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-69489dfd130b4b8195d4b1a675650c13");
                Map map1 = showUserTime();
                String s = JSONUtils.toJSONString(map1);
                goEasy.publish("cmfz", s);
               /* Map map2 = showUserByAddress();
                String s2 = map2.toString();-
                goEasy.publish("cmfz", s2);*/
            } else if (oper.equals("edit")) {
                userService.edit(user);
            }else {
                userDao.deleteByIdList(Arrays.asList(id));
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
    public Map<String,Object> upload(HttpSession session, MultipartFile photo, String id, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String realPath = session.getServletContext().getRealPath("/user/image/");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        // 网络路径
        String http = HttpUtil.getHttp(photo, request, "/user/image/");
        User user = new User();
        user.setId(id);
        user.setPhoto(http);
        userService.edit(user);
        map.put("status",200);
        return map;
    }
    @RequestMapping("showUserTime")
    public Map showUserTime(){
        HashMap map = new HashMap();
        ArrayList manList = new ArrayList();
        manList.add(userDao.queryUserByTime("男",1));
        manList.add(userDao.queryUserByTime("男",7));
        manList.add(userDao.queryUserByTime("男",30));
        manList.add(userDao.queryUserByTime("男",365));
        ArrayList womenList = new ArrayList();
        womenList.add(userDao.queryUserByTime("女",1));
        womenList.add(userDao.queryUserByTime("女",7));
        womenList.add(userDao.queryUserByTime("女",30));
        womenList.add(userDao.queryUserByTime("女",365));
        map.put("man",manList);
        map.put("women",womenList);
        return map;
    }
    @RequestMapping("showUserByAddress")
    public Map showUserByAddress(){
        HashMap hashMap = new HashMap();
        List<UserDTO> manList = userDao.queryUserBySex("男");
        List<UserDTO> womenList = userDao.queryUserBySex("女");
        hashMap.put("man",manList);
        hashMap.put("women",womenList);
        return hashMap;
    }


}
