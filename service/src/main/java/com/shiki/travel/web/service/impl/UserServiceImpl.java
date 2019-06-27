package com.shiki.travel.web.service.impl;

import com.shiki.travel.dao.RouteDao;
import com.shiki.travel.dao.UserDao;
import com.shiki.travel.pojo.Route;
import com.shiki.travel.pojo.User;
import com.shiki.travel.utils.MailUtils;
import com.shiki.travel.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.Duration;
import java.util.*;

/**
 * @Author: shiki
 * @Date: 2019/04/18 1:06
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public User join(User user) {
        user.setCode(UUID.randomUUID().toString());
        user.setStatus("N");
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user = userDao.saveAndFlush(user);
        jmsMessagingTemplate.convertAndSend("email", new HashMap<>(Map.of("email", user.getEmail(), "code",
                user.getCode())));
        redisTemplate.opsForValue().set(user.getUid() + "code", user.getCode(), Duration.ofDays(7L));
        return user;
    }

    @JmsListener(destination = "email")
    public void email(Map map) {
        String email = (String) map.get("email");
        String code = (String) map.get("code");
        MailUtils.sendMail(email,
                "<br><a href=http://www.travel.com:16980/user/activate/" + code +
                        ">点击链接进行激活账号3日内激活有效</a>", "激活邮件");
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Optional<User> login(String username, String password) {
        System.out.println(username + ":" + password);
        return userDao.findOne((Specification<User>) (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("username").as(String.class), username),
                        criteriaBuilder.equal(root.get("password").as(String.class), DigestUtils.md5DigestAsHex(password.getBytes()))
                )).getRestriction());
    }

    /**
     * 用户名校检
     *
     * @param username
     * @return
     */
    @Override
    public Optional<User> check(String username) {
        return userDao.findOne((Specification<User>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaQuery
                        .where(criteriaBuilder.equal(root.get("username").as(String.class), username))
                        .getRestriction());
    }

    @Autowired
    private RouteDao routeDao;

    @Override
    public List<Route> query4UserFavorite(Integer uid) {
        return routeDao.query4UserFavorite(uid);
    }

    @Override
    public User activate(String code) {
        Optional<User> one =
                userDao.findOne((Specification<User>) (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.where(criteriaBuilder.equal(root.get("code").as(String.class), code)).getRestriction());
        return one.map(u -> {
            u.setStatus("Y");
            userDao.saveAndFlush(u);
            jmsMessagingTemplate.convertAndSend("success", new HashMap<>(Map.of("email", u.getEmail(), "username",
                    u.getName())));
            return u;
        }).orElse(null);
    }

    @JmsListener(destination = "success")
    public void success(Map map) {
        String email = (String) map.get("email");
        String username = (String) map.get("username");
        MailUtils.sendMail(email,
                "<br>"+username+"您已激活成功<a href=http://www.travel.com:/login.html>请登录</a>", "欢迎登录");
    }
}
