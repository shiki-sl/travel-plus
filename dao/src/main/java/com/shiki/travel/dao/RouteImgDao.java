package com.shiki.travel.dao;

import com.shiki.travel.pojo.RouteImg;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: shiki
 * @Date: 2019/04/16 15:38
 */
@Mapper
public interface RouteImgDao extends JpaRepository<RouteImg,Integer>, JpaSpecificationExecutor<RouteImg> {
}
