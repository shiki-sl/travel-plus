package com.shiki.travel.web.service;

import com.shiki.travel.pojo.Route;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author: shiki
 * @Date: 2019/04/17 20:44
 */
public interface RouteService {
    /**
     * @param id
     * @return
     */
    Route findById(Integer id);

    /**
     * @return
     */
    List<Route> findAll();

    List<Route> saveAll(List<Route> list);

    Page<Route> hotRankingList();

    Page<Route> newTour();

    Page<Route> theme();

    Page<Route> rankingList(Map searchMap);

    Page<Route> findQuery(Map map);

    Map findOneTest(Integer rid);

}
