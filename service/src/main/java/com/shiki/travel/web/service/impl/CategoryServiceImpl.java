package com.shiki.travel.web.service.impl;

import com.shiki.travel.dao.CategoryDao;
import com.shiki.travel.pojo.Category;
import com.shiki.travel.web.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: shiki
 * @Date: 2019/05/04 11:54
 */
@CacheConfig(cacheNames = "categorys")
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @Cacheable(key = "'categorys'")
    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Cacheable(key = "'category'+#id")
    public Category findById(Integer id) {
        return categoryDao.findById(id).orElse(null);
    }

    /**
     * 清除redis中的category缓存
     */
    @CacheEvict(key = "'category::*'")
    public void clean(){

    }
}
