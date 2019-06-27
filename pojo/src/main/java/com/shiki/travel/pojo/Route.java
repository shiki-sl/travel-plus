package com.shiki.travel.pojo;

import lombok.*;
import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author shiki
 * 旅游线路商品实体类
 */

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class Route implements Serializable {
    /**
     * 线路id，必输
     */
    @Id
    @org.springframework.data.annotation.Id
    @Field("id")
    private Integer rid;
    /**
     * 线路名称，必输
     */
    @Field("travel_rname")
    private String rname;
    /**
     * 价格，必输
     */
    @Field("travel_price")
    private Double price;
    /**
     * 线路介绍
     */
    @Field("travel_routeIntroduce")
    private String routeIntroduce;
    /**
     * 是否上架，必输，0代表没有上架，1代表是上架
     */
    @Field("travel_rflag")
    private String rflag;
    /**
     * 上架时间
     */
    private String rdate;
    /**
     * 是否主题旅游，必输，0代表不是，1代表是
     */
    private String isThemeTour;
    /**
     * 收藏数量
     */
    @Field("travel_count")
    private Integer count;
    /**
     * 所属分类，必输
     */
    @Field("travel_cid")
    private Integer cid;
    /**
     * 缩略图
     */
    @Field("travel_rimage")
    private String rimage;
    /**
     * 所属商家
     */
    @Field("travel_sid")
    private Integer sid;
    /**
     * 抓取数据的来源id
     */
    private String sourceId;

//    @ManyToOne(targetEntity = Category.class, optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "cid", referencedColumnName = "cid", insertable = false, updatable = false)
//    private Category category;
//
//    @ManyToOne(targetEntity = Seller.class, optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "sid", referencedColumnName = "sid", insertable = false, updatable = false)
//    private Seller seller;
//
//    @OneToMany(mappedBy = "rid")
//    private Set<RouteImg> routeImgs = new HashSet<>();
//
//    @OneToMany(mappedBy = "rid")
//    private Set<Favorite> favorites = new HashSet<>();
}
