package com.school.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.zhxy.pojo.Clazz;
import com.school.zhxy.pojo.Grade;
import com.school.zhxy.pojo.Teacher;
import com.school.zhxy.service.StudentService;
import com.school.zhxy.service.TeacherService;
import com.school.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    //http://localhost:9001/sms/teacherController/getTeachers/1/3
    @RequestMapping("/getTeachers/{pageno}/{pagesize}")
    public Result getTeachers(@PathVariable("pageno") Integer pageno,
                              @PathVariable("pagesize") Integer pagesize,
                              Teacher teacher) {

        Page<Teacher> gradePage = new Page<Teacher>(pageno, pagesize);
        IPage<Teacher> iPage = teacherService.getTeacherByName(gradePage, teacher);
        return Result.ok(iPage);
    }

    @RequestMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher) {
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    @RequestMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody List<Integer> list) {
        teacherService.removeByIds(list);

        return Result.ok();
    }

}
