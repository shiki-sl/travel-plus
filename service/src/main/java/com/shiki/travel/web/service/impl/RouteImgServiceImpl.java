package com.shiki.travel.web.service.impl;

import com.shiki.travel.dao.RouteImgDao;
import com.shiki.travel.pojo.RouteImg;
import com.shiki.travel.web.service.RouteImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: shiki
 * @Date: 2019/04/18 1:06
 */
@Service
@CacheConfig(cacheNames = "routeImgs")
public class RouteImgServiceImpl implements RouteImgService {
    @Autowired
    private RouteImgDao routeImgDao;

    @Cacheable(key = "'routeImg'+#id")
    @Override
    public List<RouteImg> findAllByRid(Integer id) {
        return routeImgDao.findAll((Specification<RouteImg>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaQuery.where(criteriaBuilder.equal(root.get("rid").as(Integer.class), id)).getRestriction());
    }

    @CachePut(key = "'routeImgs'")
    @Override
    public List<RouteImg> saveByRouteId(List<RouteImg> list) {
        return routeImgDao.saveAll(list);
    }

    @CacheEvict(key = "'routeImg'")
    public void clean() {
//        Log.info("class"+this.getClass()+"routeImg缓存已清空");
        System.out.println(this.getClass()+"    routeImg缓存已清空");
    }
}
