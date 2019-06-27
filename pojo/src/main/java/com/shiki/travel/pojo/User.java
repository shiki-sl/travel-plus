package com.shiki.travel.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author shiki
 * 用户实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class User implements Serializable {
    /**
     * 用户id
     */
    @Id
    @ApiModelProperty("用户id")
    private int uid;
    /**
     * 用户名，账号
     **/

    @ApiModelProperty("用户昵称")
    @NotNull
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty("用户密码")
    @NotNull
    private String password;
    /**
     * 真实姓名
     */
    @ApiModelProperty("用户真实姓名")
    private String name;
    /**
     * 出生日期
     */
    @ApiModelProperty("用户生日")
    private LocalDateTime birthday;
    /**
     * 男或女
     */
    @ApiModelProperty("用户性别")
    private String sex;
    /**
     * 手机号
     */
    @ApiModelProperty("用户手机号,可验证")
    private String telephone;
    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty("用户email,需验证")
    private String email;
    /**
     * 激活状态，Y代表激活，N代表未激活
     */
    @ApiModelProperty("用户是否激活,不接受该参数")
    private String status;
    /**
     * 激活码（要求唯一）
     */
    @ApiModelProperty(hidden = true, value = "用户点击邮箱进行激活或输入短信验证码进行激活时输入")
    private String code;

//    @ApiParam(hidden = true)
//    @OneToMany(mappedBy = "uid")
//    private Set<Favorite> favorites = new HashSet<>();

}
