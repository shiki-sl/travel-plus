package com.shiki.travel.web.service.impl;

import com.shiki.travel.dao.FavoriteDao;
import com.shiki.travel.dao.RouteDao;
import com.shiki.travel.pojo.Favorite;
import com.shiki.travel.pojo.Route;
import com.shiki.travel.web.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author: shiki
 * @Date: 2019/05/07 1:18
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteDao favoriteDao;

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Favorite findByUidAndRid(Integer uid, Integer rid) {
        return favoriteDao.findByUidEqualsAndRidEquals(uid, rid);
    }

    @Transactional
    @Override
    public void delFavorite(Integer uid, Integer rid) {
        Favorite byUidAndRid = findByUidAndRid(uid, rid);
        favoriteDao.delete(byUidAndRid);
        Optional<Route> byId = routeDao.findById(rid);
        if (byId.isPresent()) {
            Route route = byId.get();
            route.setCount(route.getCount() > 0 ? route.getCount() - 1 : 0);
            solrTemplate.saveBean("travel",route);
            solrTemplate.commit("travel");
            routeDao.saveAndFlush(route);
        }
    }

    @Transactional
    @Override
    public void saveFavorite(Integer uid, Integer rid) {
        Favorite favorite = new Favorite(rid, LocalDateTime.now(), uid);
        favoriteDao.saveAndFlush(favorite);
        Optional<Route> byId = routeDao.findById(rid);
        if (byId.isPresent()) {
            Route route = byId.get();
            route.setCount(route.getCount() + 1);
            solrTemplate.saveBean("travel",route);
            solrTemplate.commit("travel");
            routeDao.saveAndFlush(route);
        }
    }
}
