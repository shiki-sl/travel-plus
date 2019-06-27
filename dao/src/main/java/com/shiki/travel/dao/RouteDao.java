package com.shiki.travel.dao;

import com.shiki.travel.pojo.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: shiki
 * @Date: 2019/04/16 15:38
 */
public interface RouteDao extends JpaRepository<Route, Integer>, JpaSpecificationExecutor<Route> {

    @Query(value = "select r.* " +
            "from route r " +
                        "inner join favorite f on r.rid = f.rid " +
                        "inner join user u on f.uid = u.uid " +
            "where u.uid = ?1",nativeQuery = true)
    List<Route> query4UserFavorite(Integer uid);

}
