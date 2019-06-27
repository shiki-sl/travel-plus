package com.shiki.travel.web.service.impl;

import com.shiki.travel.dao.SellerDao;
import com.shiki.travel.pojo.Seller;
import com.shiki.travel.web.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: shiki
 * @Date: 2019/05/04 16:48
 */
@CacheConfig(cacheNames = "sellers")
@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerDao sellerDao;

    @Cacheable(key = "'sellers'")
    @Override
    public List<Seller> findAll() {
        return sellerDao.findAll();
    }

//    @Cacheable(key = "'seller'+#id")
    @Cacheable(key = "'seller'+#id")
    @Override
    public Seller findById(Integer id) {
        return sellerDao.findById(id).orElse(null);
    }
}
