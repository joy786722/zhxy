package com.school.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.zhxy.pojo.Clazz;
import com.school.zhxy.service.ClazzService;
import com.school.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    //clazzController/getClazzs
    @RequestMapping("/getClazzs")
    public Result getClazzs() {
        return Result.ok(clazzService.list());

    }

    ///sms/clazzController/getClazzsByOpr/1/3
    @RequestMapping("/getClazzsByOpr/{pageno}/{pagesize}")
    public Result getClazzsByOpr(@PathVariable Integer pageno,
                                 @PathVariable Integer pagesize,
                                 Clazz clazz) {
        Page<Clazz> page = new Page<Clazz>(pageno, pagesize);
        IPage iPage = clazzService.getPageByname(page, clazz);
        return Result.ok(iPage);
    }

    //sms/clazzController/saveOrUpdateClazz
    @RequestMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@RequestBody Clazz clazz) {
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }
    //http://localhost:9001/sms/clazzController/deleteClazz

    @RequestMapping("deleteClazz")
    public Result deleteClazz(@RequestBody List<Integer> list) {
        clazzService.removeByIds(list);
        return Result.ok();
    }
}
