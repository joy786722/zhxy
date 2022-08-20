package com.school.zhxy.service;

import com.school.zhxy.pojo.Clazz;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.school.zhxy.util.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.ResourceBundle;

public interface ClazzService extends IService<Clazz> {


    IPage getPageByname(Page<Clazz> page, Clazz clazz);
}
