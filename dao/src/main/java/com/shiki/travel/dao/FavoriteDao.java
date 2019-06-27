package com.shiki.travel.dao;

import com.shiki.travel.pojo.Favorite;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: shiki
 * @Date: 2019/04/16 15:08
 */
@Mapper
public interface FavoriteDao extends JpaRepository<Favorite, Integer>, JpaSpecificationExecutor<Favorite> {

    Favorite findByUidEqualsAndRidEquals(Integer uid, Integer rid);
}
