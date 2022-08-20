package com.school.zhxy.service;

import com.school.zhxy.pojo.LoginForm;
import com.school.zhxy.pojo.Student;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


public interface StudentService extends IService<Student> {


    Student login(LoginForm loginForm);

    Student selectByid(Long userId);

    IPage<Student> getStudentByName(Page<Student> gradePage, Student student);
}
