package com.school.zhxy.service.impl;

import com.school.zhxy.mapper.TeacherMapper;
import com.school.zhxy.pojo.*;
import com.school.zhxy.service.TeacherService;
import com.school.zhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("teaService")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {


    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> queryWrapper = new  QueryWrapper<Teacher>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }

    @Override
    public Teacher selectByid(Long userId) {
        return  baseMapper.selectById(userId);
    }

    @Override
    public IPage<Teacher> getTeacherByName(Page<Teacher> gradePage, Teacher teacher) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(teacher.getClazzName())){
            queryWrapper.like("clazz_name",teacher.getClazzName());
        }
        if(!StringUtils.isEmpty(teacher.getName())){
            queryWrapper.like("name",teacher.getName());
        }
        queryWrapper.orderByAsc("id");
        queryWrapper.orderByDesc("name");
        return baseMapper.selectPage(gradePage,queryWrapper);
    }



}
