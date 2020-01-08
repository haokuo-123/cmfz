package com.baizhi.hk.controller;

import com.baizhi.hk.dao.UserDao;
import com.baizhi.hk.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class LoginController {
    @Autowired
    private UserDao userDao;
    @RequestMapping("login")
    public Map login(String phone,String password){
        HashMap map = new HashMap();
        User user = new User();
        user.setPhone(phone);
        User user1 = userDao.selectOne(user);
            if(user1!=null){
                if(user1.getPassword().equals(password)){
                    map.put("status",200);
                    map.put("user",user1);
                }
            }else {
                map.put("status",-200);
            }
            return map;
    }
}
