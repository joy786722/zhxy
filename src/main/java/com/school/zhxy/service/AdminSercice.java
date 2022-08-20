package com.school.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.school.zhxy.pojo.Admin;
import com.school.zhxy.pojo.LoginForm;
import org.springframework.stereotype.Service;

@Service
public interface AdminSercice extends IService<Admin> {
     Admin login(LoginForm loginForm);

    Admin selectByid(Long userId);

    IPage<Admin> getAdminByName(Page<Admin> page, String adminName);
}
