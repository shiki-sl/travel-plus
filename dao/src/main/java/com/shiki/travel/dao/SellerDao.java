package com.shiki.travel.dao;

import com.shiki.travel.pojo.Seller;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: shiki
 * @Date: 2019/04/16 15:37
 */
@Mapper
public interface SellerDao extends JpaRepository<Seller,Integer>, JpaSpecificationExecutor<Seller> {
}
