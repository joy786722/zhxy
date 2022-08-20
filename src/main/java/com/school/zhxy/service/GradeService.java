package com.school.zhxy.service;

import com.school.zhxy.pojo.Grade;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface GradeService extends IService<Grade> {


    IPage<Grade> getGradeByName(Page<Grade> page, String gradeName);
}
