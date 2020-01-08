package com.baizhi.hk.service;

import com.baizhi.hk.aspect.Log;
import com.baizhi.hk.dao.UserDao;
import com.baizhi.hk.entity.Album;
import com.baizhi.hk.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public Map<String, Object> findAll(Integer page, Integer rows) {
        User user = new User();
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<User> users = userDao.selectByRowBounds(user, rowBounds);
        int count = userDao.selectCount(user);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",users);
        map.put("total",count%rows == 0?count / rows : count / rows+1);
        map.put("record",count);
        return map;
    }

    @Override
    @Log(name = "用户的添加")
    public String add(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setLastLogin(new Date());
        int i = userDao.insertSelective(user);

        if(i==0){
            throw new RuntimeException("添加失败");
        }
        return user.getId();

    }

    @Override
    @Log(name = "用户的修改")
    public void edit(User user) {
        if ("".equals(user.getPhoto())) {
            user.setPhoto(null);
        }
        try {
            userDao.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("修改失败");
        }
    }
}
