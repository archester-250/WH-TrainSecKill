package com.pku.service.Impl;

import com.pku.mapper.CategoryMapper;
import com.pku.pojo.entity.Category;
import com.pku.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }
}
