package com.school.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.zhxy.pojo.Grade;
import com.school.zhxy.service.GradeService;
import com.school.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    @Autowired
    private GradeService gradeService;


    //"sms/gradeController/getGrades"
    @RequestMapping("/getGrades")
    public Result getGrades() {
        return Result.ok(gradeService.list());
    }

    @RequestMapping("/getGrades/{pageno}/{pagesize}")
    public Result getGrades(@PathVariable("pageno") Integer pageno,
                            @PathVariable("pagesize") Integer pagesize,
                            String gradeName) {

        Page<Grade> gradePage = new Page<Grade>(pageno, pagesize);
        IPage<Grade> iPage = gradeService.getGradeByName(gradePage, gradeName);
        return Result.ok(iPage);
    }

    @RequestMapping("/saveOrUpdateGrade")
    public Result saveOrUpdate(@RequestBody Grade grade) {
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    @RequestMapping("/deleteGrade")
    public Result deleteGrade(@RequestBody List<Integer> list) {
        gradeService.removeByIds(list);
        return Result.ok();
    }
}
