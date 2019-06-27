package com.shiki.travel.api;

import com.shiki.travel.pojo.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author: shiki
 * @Date: 2019/04/16 19:43
 */
@Api(value = "旅行信息管理接口", description = "旅行信息管理接口,提供旅行线路的查询")
public interface RouteControllerApi {
    @ApiOperation("查询一条旅行信息接口")
    @ApiImplicitParam(name = "id", value = "旅行线路ID", required = true, paramType = "path")
    ResultInfo findById(Integer id) throws Exception;

    @ApiOperation("根据旅行线路id查询一条旅行信息所有的图片接口")
    @ApiImplicitParam(name = "id", value = "旅行线路ID", required = true, paramType = "path")
    ResultInfo findRouteAndRouteImgByRid(Integer id) throws Exception;

    @ApiOperation("查询全部的旅行线路")
    ResultInfo findAll() throws Exception;

    @ApiOperation("根据旅行线路id查询一条旅行信息所有的图片接口")
    ResultInfo upload() throws Exception;

    @ApiOperation("查询前四条最多人收藏的旅行线路")
    ResultInfo hotRankingList();

    @ApiOperation("查询四条最新旅行线路")
    ResultInfo newTour();

    @ApiOperation("查询前四条主题旅行线路")
    ResultInfo theme();

    @ApiOperation("根据收藏人数倒序查询旅行线路")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "body",
                    value = "封装多个参数的查询集合,格式如下rname(旅行线路名),min(价格下限),max(价格上限)",
                    paramType = "Get"),
    })
    ResultInfo rankingList(Map searchMap);

    @ApiOperation("根据收藏人数倒序查询旅行线路")
    @ApiImplicitParam(
            name = "body",
            value = "封装多个参数的查询集合,cid(模块id),pageNo(当前页),pageSize(每页条数)",
            paramType = "Get")
    ResultInfo findQuery(@RequestParam Map map);

    @ApiOperation("根据收藏人数倒序查询旅行线路")
    @ApiImplicitParam(name = "rid", value = "旅行线路ID", required = true, paramType = "path")
    ResultInfo findOneTest(Integer rid);

    @ApiOperation("搜索")
    @ApiImplicitParam(name = "searchMap",
            value = "keywords:搜索的关键字;" +
                    "cid:在那个页面下进行的搜索;" +
                    "priceMin:最低价;" +
                    "priceMax:最高价;" +
                    "pageNo:当前页;" +
                    "pageSize:每页数量",
            required = true, paramType = "post")
    ResultInfo search(Map searchMap);

    @ApiOperation("更新solr索引库")
    ResultInfo update();
}
