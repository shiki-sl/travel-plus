package com.shiki.travel.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author shiki
 * @date 2019/05/02 13:26
 * ComponentScan 扫描包
 *      basePackages 中com.shiki.travel.web.*则web包下的子包会被扫描,而web包下java文件会扫不到
 * EnableJpaRepositories jpa所在包
 * EntityScan 实体包
 * EnableCaching 缓存支持
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.shiki.travel.web",
        "com.shiki.travel.api"
})
@EnableJpaRepositories(basePackages = "com.shiki.travel.dao")
@EntityScan("com.shiki.travel.pojo")
//缓存
@EnableCaching
//session
@EnableRedisHttpSession
//定时任务
@EnableScheduling
public class ControllerApplication {

    @Bean
    public BufferedImageHttpMessageConverter bufferedImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    public static void main(String[] args) {
        SpringApplication.run(ControllerApplication.class, args);
    }

}