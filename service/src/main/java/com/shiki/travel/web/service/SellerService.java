package com.shiki.travel.web.service;

import com.shiki.travel.pojo.Seller;

import java.util.List;

/**
 * @Author: shiki
 * @Date: 2019/05/04 16:48
 */
public interface SellerService {
    List<Seller> findAll();

    Seller findById(Integer id);
}
