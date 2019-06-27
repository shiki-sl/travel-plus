package com.shiki.travel.web.service;

import com.shiki.travel.pojo.RouteImg;

import java.util.List;

/**
 * @Author: shiki
 * @Date: 2019/04/17 20:44
 */
public interface RouteImgService {
    /**
     * 查询对应routeId的详细图片地址
     * @param id
     * @return
     */
    List<RouteImg> findAllByRid(Integer id);

    /**
     * 根据id变更详细图片地址
     * @param list
     * @return
     */
    List<RouteImg> saveByRouteId(List<RouteImg> list);
}
