package com.baizhi.hk.service;

import com.baizhi.hk.dao.AdminDao;
import com.baizhi.hk.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    public Admin login(Admin admin, String inputCode, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String securityCode = (String) session.getAttribute("securityCode");
        Admin admin2 = new Admin();
        admin2.setUsername(admin.getUsername());
        Admin admin1 = adminDao.selectOne(admin2);
        if (securityCode.equalsIgnoreCase(inputCode)){
            if(admin1 != null){
                if(admin1.getPassword().equals(admin.getPassword())){
                    session.setAttribute("admin",admin1.getUsername());
                    return admin;
                }
                throw new RuntimeException("密码为空或密码错误");
            }else {
                throw new RuntimeException("用户名为空或用户名错误");
            }
        }else {
            throw new RuntimeException("验证码错误");
        }
    }
}
