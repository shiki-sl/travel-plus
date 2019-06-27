package com.shiki.travel.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author shiki
 * 旅游线路图片实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class RouteImg implements Serializable {
    /**
     * 商品图片id
     */
    @ApiModelProperty("商品图片id")
    @Id
    @Field("id")
    private Integer rgid;
    /**
     * 旅游商品id
     */
    @ApiModelProperty("旅游商品id")
    private Integer rid;
    /**
     * 详情商品大图
     */
    @ApiModelProperty("详情商品大图")
    private String bigPic;
    /**
     * 详情商品小图
     */
    @ApiModelProperty("商品图片id")
    private String smallPic;
//
//    @ApiModelProperty("商品实体")
//    @ManyToOne(targetEntity = Route.class)
//    @JoinColumn(name = "rid", referencedColumnName = "rid", insertable = false, updatable = false)
//    private Route route;
}
