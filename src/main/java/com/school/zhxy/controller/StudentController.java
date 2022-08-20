package com.school.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.zhxy.pojo.Student;
import com.school.zhxy.pojo.Teacher;
import com.school.zhxy.service.StudentService;
import com.school.zhxy.util.MD5;
import com.school.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    private StudentService studentService;

    //getStudentByOpr/1/3
    @RequestMapping("/getStudentByOpr/{pageno}/{pagesize}")
    public Result getStudent(@PathVariable("pageno") Integer pageno,
                             @PathVariable("pagesize") Integer pagesize,
                             Student student) {

        Page<Student> gradePage = new Page<Student>(pageno, pagesize);
        IPage<Student> iPage = studentService.getStudentByName(gradePage, student);
        return Result.ok(iPage);
    }

    @RequestMapping("/deleteStudent")
    public Result deleteStudent(@RequestBody List<Integer> list) {
        studentService.removeByIds(list);
        return Result.ok();
    }

    @RequestMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@RequestBody Student student){
        if(student!=null){
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return Result.ok();
    }

    @DeleteMapping("/delStudentById")
    public Result delStudentById(@RequestParam List<Integer> list){
        studentService.removeByIds(list);
        return Result.ok();
    }
}
