package com.shiki.travel.dao;

import com.shiki.travel.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

/**
 * @Author: shiki
 * @Date: 2019/04/16 15:37
 */
@Component
public interface UserDao extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {

}
