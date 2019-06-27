package com.shiki.travel.pojo;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author shiki
 * 商家实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class Seller implements Serializable {
    /**
     * 商家id
     */
    @Id
    private int sid;
    /**
     * 商家名称
     */
    private String sname;
    /**
     * 商家电话
     */
    private String consphone;
    /**
     * 商家地址
     */
    private String address;

//    @OneToMany(mappedBy = "sid")
//    private Set<Route> routes;
}
