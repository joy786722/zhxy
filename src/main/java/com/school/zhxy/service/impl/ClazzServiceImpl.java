package com.school.zhxy.service.impl;


import com.school.zhxy.pojo.Clazz;
import com.school.zhxy.service.ClazzService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.school.zhxy.mapper.ClazzMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {


    @Override
    public IPage getPageByname(Page<Clazz> page, Clazz clazz) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(!StringUtils.isEmpty(clazz.getName())){
            queryWrapper.like("name",clazz.getName());
        }
        if(!StringUtils.isEmpty(clazz.getGradeName())){
            queryWrapper.eq("grade_name",clazz.getGradeName());
        }
        return baseMapper.selectPage(page,queryWrapper);
    }
}
