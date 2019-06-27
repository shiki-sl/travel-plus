package com.shiki.travel.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author shiki
 * 收藏实体类
 */

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@IdClass(FavoriteId.class)
public class Favorite implements Serializable {
    /**
     * 旅游线路对象
     */
    @Id
    private Integer rid;
    /**
     * 收藏时间
     */
    @ApiModelProperty("用户收藏时间")
    private LocalDateTime date;
    /**
     * 所属用户
     */
    @Id
    private Integer uid;

//    @ManyToOne(targetEntity = User.class)
//    @JoinColumn(name = "uid", referencedColumnName = "uid", insertable = false, updatable = false)
//    private User user;
//
//    @ManyToOne(targetEntity = Route.class)
//    @JoinColumn(name = "rid", referencedColumnName = "rid", insertable = false, updatable = false)
//    private Route route;

}

/**
 * Favorite的复合主键
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
class FavoriteId implements Serializable {
    private Integer rid;
    private Integer uid;
}