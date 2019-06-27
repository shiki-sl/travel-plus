package com.shiki.travel.web.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: shiki
 * @Date: 2019/05/05 9:03
 */
public interface SearchService {

    /**
     * map集合中应包括
     * 关键字查询:
     *      keywords:搜索的关键字
     * 页面查询
     *      cid:在那个页面下进行的搜索
     * 价格区间
     *      priceMin:最低价
     *      priceMax:最高价
     * 分页查询
     *      pageNo:当前页
     *      pageSize:每页数量
     * @param searchMap
     * @return
     */
    Map search(Map searchMap);

    List findAll();
}
