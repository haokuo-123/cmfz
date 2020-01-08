package com.baizhi.hk.service;

import com.baizhi.hk.entity.Admin;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {
    Admin login(Admin admin, String inputCode, HttpServletRequest request);
}
