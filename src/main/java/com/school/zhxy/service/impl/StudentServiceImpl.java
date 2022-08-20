package com.school.zhxy.service.impl;

import com.school.zhxy.mapper.StudentMapper;
import com.school.zhxy.pojo.Admin;
import com.school.zhxy.pojo.LoginForm;
import com.school.zhxy.pojo.Student;
import com.school.zhxy.pojo.Teacher;
import com.school.zhxy.service.StudentService;
import com.school.zhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**

 */
@Service("stuService")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {


    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper = new  QueryWrapper<Student>();
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        Student student = baseMapper.selectOne(queryWrapper);
        return student;
    }

    @Override
    public Student selectByid(Long userId) {
        QueryWrapper<Student> queryWrapper= new QueryWrapper();
        queryWrapper.eq("id",userId);
       return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Student> getStudentByName(Page<Student> gradePage, Student student) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(student.getClazzName())){
            queryWrapper.like("clazz_name",student.getClazzName());
        }
        if(!StringUtils.isEmpty(student.getName())){
            queryWrapper.like("name",student.getName());
        }
        queryWrapper.orderByAsc("id");
        queryWrapper.orderByDesc("name");
        return baseMapper.selectPage(gradePage,queryWrapper);
    }
}
