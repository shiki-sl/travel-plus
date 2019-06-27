package com.shiki.travel.pojo;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author shiki
 * 分类实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class Category implements Serializable {
    /**
     * 分类id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;
    /**
     * 分类名称
     */
    private String cname;

//    /**
//     * 注解配置分类id和分类列表之间的关系
//     * 使用注解配置多表关系
//     * 1.声明关系
//     * 2.配置外检(中间表)
//     *
//     * @joinColumn:配置外键
//     */
//    @OneToMany(mappedBy = "cid")
//    private Set<Route> routes = new HashSet<>();
}
