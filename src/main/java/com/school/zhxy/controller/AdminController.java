package com.school.zhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.zhxy.pojo.Admin;
import com.school.zhxy.pojo.Student;
import com.school.zhxy.pojo.Teacher;
import com.school.zhxy.service.AdminSercice;
import com.school.zhxy.service.StudentService;
import com.school.zhxy.service.TeacherService;
import com.school.zhxy.util.JwtHelper;
import com.school.zhxy.util.MD5;
import com.school.zhxy.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    private AdminSercice adminSercice;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @RequestMapping("/getAllAdmin/{pageno}/{pagesize}")
    public Result getAllAdmin(@PathVariable("pageno")Integer pageno,
                              @PathVariable("pagesize")Integer pagesize,
                                String adminName){
        Page<Admin> page = new Page<>(pageno,pagesize);
        IPage<Admin> iPage=adminSercice.getAdminByName(page,adminName);
        return Result.ok(iPage);
    }

    @RequestMapping("/savaOrUpdateAdmin")
    public  Result savaOrUpdateAdmin(@RequestBody Admin admin){
        if (!StringUtils.isEmpty(admin.getPassword())) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminSercice.saveOrUpdate(admin);
        return Result.ok();

    }

    @RequestMapping("/deleteAdmin")
    public  Result deleteAdmin(@RequestBody List<Integer> list){
        adminSercice.removeByIds(list);
        return Result.ok();
    }

    @ApiOperation("修改密码")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(@RequestHeader("token") String token,
                            @PathVariable("oldPwd") String oldPwd,
                            @PathVariable("newPwd") String newPwd){
        boolean yOn = JwtHelper.isExpiration(token);
        if(yOn){
            //token过期
            return Result.fail().message("token失效!");
        }
        //通过token获取当前登录的用户id
        Long userId = JwtHelper.getUserId(token);
        //通过token获取当前登录的用户类型
        Integer userType = JwtHelper.getUserType(token);
        // 将明文密码转换为暗文
        oldPwd=MD5.encrypt(oldPwd);
        newPwd= MD5.encrypt(newPwd);
        if(userType == 1){
            QueryWrapper<Admin> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
            Admin admin = adminSercice.getOne(queryWrapper);
            if (null!=admin) {
                admin.setPassword(newPwd);
                adminSercice.saveOrUpdate(admin);
            }else{
                return Result.fail().message("原密码输入有误！");
            }
        }else if(userType == 2){
            QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
            Student student = studentService.getOne(queryWrapper);
            if (null!=student) {
                student.setPassword(newPwd);
                studentService.saveOrUpdate(student);
            }else{
                return Result.fail().message("原密码输入有误！");
            }
        }
        else if(userType == 3){
            QueryWrapper<Teacher> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
            Teacher teacher = teacherService.getOne(queryWrapper);
            if (null!=teacher) {
                teacher.setPassword(newPwd);
                teacherService.saveOrUpdate(teacher);
            }else{
                return Result.fail().message("原密码输入有误！");
            }
        }
        return Result.ok();
    }

}
