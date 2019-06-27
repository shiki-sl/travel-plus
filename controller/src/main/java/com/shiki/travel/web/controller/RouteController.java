package com.shiki.travel.web.controller;

import com.shiki.travel.api.RouteControllerApi;
import com.shiki.travel.pojo.ResultInfo;
import com.shiki.travel.pojo.Route;
import com.shiki.travel.utils.FastDFSClientOne;
import com.shiki.travel.web.service.RouteImgService;
import com.shiki.travel.web.service.RouteService;
import com.shiki.travel.web.service.SearchService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: shiki
 * @Date: 2019/05/02 14:34
 */
@RestController
@RequestMapping("/route")
public class RouteController implements RouteControllerApi {

    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteImgService routeImgService;

    @Override
    @GetMapping("/findById/{id}")
    @ApiImplicitParam(name = "id", value = "旅行线路ID", paramType = "path")
    public ResultInfo findById(@PathVariable @NotNull Integer id) throws Exception {
        return new ResultInfo(true, routeService.findById(id), "成功");
    }

    @Override
    @GetMapping("/findRouteAndRouteImgByRid/{id}")
    public ResultInfo findRouteAndRouteImgByRid(@PathVariable @NotNull Integer id) throws Exception {
        HashMap<String, Object> map = new HashMap<>(Map.of(
                "route", routeService.findById(id),
                "routeImg", routeImgService.findAllByRid(id)));
        return new ResultInfo(true, map, "成功");
    }

    @Override
    @GetMapping("/findAll")
    public ResultInfo findAll() {
        return new ResultInfo(true, routeService.findAll(), "成功");
    }

    @Override
    @PostMapping("/upload")
    public ResultInfo upload() throws Exception {
        FastDFSClientOne fastDFSClientOne = new FastDFSClientOne("classpath:fdfs_client.conf");
        List<Route> routeImgs = routeService.findAll();
        routeImgs.forEach(routeImg -> {
            try {
                routeImg.setRimage(fastDFSClientOne.uploadFile("F:\\travel\\src\\main\\webapp\\" + routeImg.getRimage()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        List<Route> save = routeService.saveAll(routeImgs);
        return new ResultInfo(true, save.size(), "更新成功");
    }

    @Override
    @GetMapping("/hotRankingList")
    public ResultInfo hotRankingList() {
        try {
            return new ResultInfo(true, routeService.hotRankingList().getContent(), "查询最热旅行成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false, null, "查询最热旅行失败");
        }
    }

    @Override
    @GetMapping("/newTour")
    public ResultInfo newTour() {
        try {
            return new ResultInfo(true, routeService.newTour().getContent(), "查询最新旅行成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false, null, "查询最新旅行失败");
        }
    }

    @Override
    @GetMapping("/theme")
    public ResultInfo theme() {
        try {
            return new ResultInfo(true, routeService.theme().getContent(), "查询主题旅行成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false, null, "查询主题旅行失败");
        }
    }

    @GetMapping("/rankingList")
    @Override
    public ResultInfo rankingList(@RequestParam Map searchMap) {
        try {
            return new ResultInfo(true, routeService.rankingList(searchMap), "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false, searchMap, "查询出错");
        }
    }

    @GetMapping("/findQuery")
    @Override
    public ResultInfo findQuery(@RequestParam Map map) {
        try {
            return new ResultInfo(true, routeService.findQuery(map), "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false, map, "查询出错");
        }
    }

    @GetMapping("/findOneTest/{rid}")
    @Override
    public ResultInfo findOneTest(@PathVariable Integer rid) {
        try {
            return new ResultInfo(true, routeService.findOneTest(rid), "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false, null, "查询出错");
        }
    }

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    @Override
    public ResultInfo search(@RequestParam Map searchMap) {
        System.out.println(searchMap);
        return new ResultInfo(true, searchService.search(searchMap), "搜索结果");
    }

    @Override
    public ResultInfo update() {
        searchService.findAll();
        return new ResultInfo(true, null,"");
    }
}
