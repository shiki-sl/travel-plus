package com.shiki.travel.web.service;

import com.shiki.travel.pojo.Route;
import com.shiki.travel.pojo.User;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * @Author: shiki
 * @Date: 2019/04/17 20:44
 */
public interface UserService {

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    User join(User user);

    /**
     *
     * @param username
     * @param password
     * @return
     */
    Optional<User> login(@NotNull String username,  @NotNull String password);

    /**
     * 用户名校检
     * @param username
     * @return
     */
    Optional<User> check(String username);

    List<Route> query4UserFavorite(Integer uid);

    User activate(String code);
}
