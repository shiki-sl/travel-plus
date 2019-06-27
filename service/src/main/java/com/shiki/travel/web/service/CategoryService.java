package com.shiki.travel.web.service;

import com.shiki.travel.pojo.Category;

import java.util.List;

/**
 * @Author: shiki
 * @Date: 2019/05/04 11:53
 */
public interface CategoryService {
    List<Category> findAll();

    Category findById(Integer id);
}
