package com.shiki.travel.api;

import com.shiki.travel.pojo.ResultInfo;
import com.shiki.travel.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpSession;

/**
 * @Author: shiki
 * @Date: 2019/04/16 19:43
 */
@Api(value = "用户界面管理接口", description = "用户管理界面接口,提供用户注册及登录功能")
public interface UserControllerApi {
    @ApiOperation("用户注册")
    ResultInfo join(User user,String checkCode, HttpSession session) throws Exception;

    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    ResultInfo login(String username, String password, String checkCode, HttpSession sessione) throws Exception;

    @ApiOperation("用户名校检")
    @ApiImplicitParam(name = "username", value = "用户名", required = true)
    boolean check(String username) throws Exception;

    @ApiOperation("用户退出")
    ResultInfo logout(HttpSession session) throws Exception;

    @ApiOperation("查询当前登录的用户名")
    ResultInfo getUsername(HttpSession session);

    @ApiOperation("查询当前登录用户的收藏列表")
    ResultInfo query4UserFavorite(HttpSession session);

    @ApiOperation("用户激活")
    ResultInfo activate(String code);

    @ApiOperation("查看用户是否收藏")
    ResultInfo getFavorite(Integer rid,HttpSession session);

    @ApiOperation("删除用户收藏状态")
    ResultInfo delFavorite(Integer rid,HttpSession session);

    @ApiOperation("保存用户收藏状态")
    ResultInfo saveFavorite(Integer rid,HttpSession session);
}
