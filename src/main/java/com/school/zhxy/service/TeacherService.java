package com.school.zhxy.service;

import com.school.zhxy.pojo.Grade;
import com.school.zhxy.pojo.LoginForm;
import com.school.zhxy.pojo.Teacher;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TeacherService extends IService<Teacher> {


    Teacher login(LoginForm loginForm);

    Teacher selectByid(Long userId);


    IPage<Teacher> getTeacherByName(Page<Teacher> gradePage, Teacher teacher);
}
