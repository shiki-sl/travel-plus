package com.shiki.travel.web.controller;

import com.shiki.travel.api.CategoryControllerApi;
import com.shiki.travel.pojo.ResultInfo;
import com.shiki.travel.web.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: shiki
 * @Date: 2019/05/04 11:29
 */
@RestController
@RequestMapping("/category")
public class CategoryController implements CategoryControllerApi {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/findAll")
    @Override
    public ResultInfo findAll() {
        try {
            return new ResultInfo(true,categoryService.findAll(),"查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false,null,"查询失败");
        }
    }
}
