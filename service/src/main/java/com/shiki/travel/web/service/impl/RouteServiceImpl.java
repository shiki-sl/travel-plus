package com.shiki.travel.web.service.impl;

import com.shiki.travel.dao.RouteDao;
import com.shiki.travel.pojo.Route;
import com.shiki.travel.web.service.CategoryService;
import com.shiki.travel.web.service.RouteImgService;
import com.shiki.travel.web.service.RouteService;
import com.shiki.travel.web.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: shiki
 * @Date: 2019/04/18 1:06
 */
@CacheConfig(cacheNames = "routes")
@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteDao routeDao;

    @Override
    public Route findById(Integer id) {
        return routeDao.findById(id).orElse(null);
    }

    @Override
    public List<Route> findAll() {
        return routeDao.findAll();
    }

    @Override
    public List<Route> saveAll(List<Route> list) {
        return routeDao.saveAll(list);
    }

    @Override
    public Page<Route> hotRankingList() {
        return routeDao.findAll((Specification<Route>) (root, criteriaQuery, criteriaBuilder) ->
                        null,
                PageRequest.of(0, 4, new Sort(Sort.Direction.DESC, "count")));
    }

    @Override
    public Page<Route> newTour() {
        return routeDao.findAll((Specification<Route>) (root, criteriaQuery, criteriaBuilder) ->
                        null,
                PageRequest.of(0, 4, new Sort(Sort.Direction.DESC, "rdate")));
    }

    @Override
    public Page<Route> theme() {
        return routeDao.findAll((Specification<Route>) (root, criteriaQuery, criteriaBuilder) ->
                        criteriaQuery.where(criteriaBuilder.equal(root.get("isThemeTour").as(String.class), 1)).getRestriction(),
                PageRequest.of(0, 4, new Sort(Sort.Direction.ASC, "rid")));
    }

    /**
     * @param searchMap min,
     *                  max,
     *                  pageNo,
     *                  pageSize,
     * @return
     */
    @Override
    public Page<Route> rankingList(Map searchMap) {
        int pageNo = StringUtils.isEmpty(searchMap.get("pageNo")) ? 0 : Integer.valueOf((String) searchMap.get("pageNo"));
        int pageSize = StringUtils.isEmpty(searchMap.get("pageSize")) ? 10 : Integer.valueOf((String) searchMap.get("pageSize"));
        double priceMin = StringUtils.isEmpty(searchMap.get("priceMin")) ? 0 : Double.valueOf((String) searchMap.get("priceMin"));
        double priceMax =
                StringUtils.isEmpty(searchMap.get("priceMax")) ? Double.MAX_VALUE : Double.valueOf((String) searchMap.get("priceMax"));
        pageNo = pageNo >= 1 ? pageNo - 1 : 0;
        System.out.println(searchMap);
        return routeDao.findAll((Specification<Route>) (root, criteriaQuery, criteriaBuilder) ->
                        criteriaQuery
                                .where(criteriaBuilder
                                        .and(criteriaBuilder.between(root.get("price").as(Double.class), priceMin, priceMax)))
                                .getRestriction(),
                PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.DESC, "count")));
    }

    /**
     * @param map cid(模块id)
     *            pageNo(当前页)
     *            totalPage(每页条数)
     * @return
     */
    @Override
    public Page<Route> findQuery(Map map) {

        int cid = StringUtils.isEmpty(map.get("cid")) ? 1 : Integer.valueOf((String) map.get("cid"));
        int pageNo = StringUtils.isEmpty(map.get("pageNo")) ? 1 : Integer.valueOf((String) map.get("pageNo"));
        int size = StringUtils.isEmpty(map.get("pageSize")) ? 10 : Integer.valueOf((String) map.get("pageSize"));
        return routeDao.findAll((Specification<Route>) (root, criteriaQuery, criteriaBuilder) ->
                        criteriaQuery.where(
                                criteriaBuilder.equal(root.get("cid").as(Integer.class), cid)
                        ).getRestriction(),
                PageRequest.of(pageNo - 1, size));
    }

    @Autowired
    private RouteImgService routeImgService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private CategoryService categoryService;

    @Cacheable(key = "'route'+#rid")
    @Override
    public Map findOneTest(Integer rid) {
        Map<String, Object> map = new HashMap<>();
        Optional<Route> route = routeDao.findById(rid);
        return route.map(r -> {
            map.put("route", r);
            map.put("routeImg", routeImgService.findAllByRid(r.getRid()));
            map.put("category", categoryService.findById(r.getCid()));
            map.put("seller", sellerService.findById(r.getSid()));
            return map;
        }).orElse(null);
    }

}
