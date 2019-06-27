package com.shiki.travel.web.service.impl;

import com.shiki.travel.dao.RouteDao;
import com.shiki.travel.pojo.Route;
import com.shiki.travel.web.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: shiki
 * @Date: 2019/05/05 9:09
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private RouteDao routeDao;
    /**
     * 防止前段搜索界面删除搜索关键字导致后台报错
     */
    private String keywords = "*:*";

    @Override
    public Map search(Map searchMap) {
        if (StringUtils.isEmpty(searchMap.get("keywords"))) {
//            将前端传递过来的搜索关键字赋予mark
            searchMap.put("keywords", keywords);
        }

//        防止后台缓存keywords导致数据搜索不到
        Map<String, Object> map = new HashMap<>(searchList(searchMap));
        System.out.println(searchMap);
        keywords = "*:*";
        return map;
    }

    private Map<String, Object> searchList(Map searchMap) {
        Map<String, Object> map = new HashMap<>();
//        构建简单的高亮查询
        HighlightQuery query = new SimpleHighlightQuery();
//        设置高亮域
        HighlightOptions highlightOptions = new HighlightOptions()
                .addField("travel_rname")
                .setSimplePrefix("<em style='color:red'>")
                .setSimplePostfix("</em>");
//        在query中设置高亮
        query.setHighlightOptions(highlightOptions);
//        关键字搜索
        query.addCriteria(new Criteria("travel_keywords").is(searchMap.get("keywords")));
//        按cid分类
        if (!"".equals(searchMap.get("cid"))) {
            query.addFilterQuery(new SimpleQuery(new Criteria("travel_cid").is(searchMap.get("cid"))));
        }
//        价格区间过滤
        addPrice(searchMap, query);
//        按收藏逆序
        query.addSort(new Sort(Sort.Direction.DESC, "travel_count"));
//        分页查询
        try {
            addPage(searchMap, query);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        获取高亮结果集
        HighlightPage<Route> page = solrTemplate
                .queryForHighlightPage("travel", query, Route.class);
        page.forEach(System.out::println);
        page
                .getHighlighted()
                .stream()
                .filter(entry -> entry.getHighlights().size() > 0 && entry.getHighlights().get(0).getSnipplets().size() > 0)
                .forEach(entry -> entry.getEntity().setRname(entry.getHighlights().get(0).getSnipplets().get(0)));
//        每页行数
        map.put("row", page.getContent());
//        总页数
        map.put("totalPage", page.getTotalPages());
//        总条数
        map.put("total", page.getTotalElements());
        return map;
    }

    private void addPrice(Map searchMap, HighlightQuery query) {
        SimpleFacetQuery simpleFacetQuery = new SimpleFacetQuery();
        Criteria criteria = new Criteria("travel_price");
        if (!"".equals(searchMap.get("priceMin"))) {
            try {
                int priceMin = Integer.valueOf((String) searchMap.get("priceMin"));
                System.out.println(priceMin);
                simpleFacetQuery.addCriteria(criteria.greaterThanEqual(priceMin));
            } catch (NumberFormatException ignored) {
            }
        }
        if (!"".equals(searchMap.get("priceMax"))) {
            try {
                int priceMax = Integer.valueOf((String) searchMap.get("priceMax"));
                System.out.println(priceMax);
                simpleFacetQuery.addCriteria(criteria.lessThanEqual(priceMax));
            } catch (NumberFormatException ignored) {
            }
        }
        query.addFilterQuery(simpleFacetQuery);
    }

    private void addPage(Map searchMap, HighlightQuery query) {
        int pageNo = StringUtils.isEmpty(searchMap.get("pageNo")) ? 1 : Integer.valueOf((String) searchMap.get("pageNo"));
        int pageSize = StringUtils.isEmpty(searchMap.get("pageNo")) ? 10 : Integer.valueOf((String) searchMap.get("pageSize"));
        query.setOffset((long) (pageSize * (pageNo - 1)));
        query.setRows(pageSize);
    }

    @Override
    public List findAll() {
        Query query = new SimpleQuery("*:*");
        query.addFilterQuery(new SimpleFilterQuery(new Criteria("travel_keywords")));
        System.out.println(solrTemplate.query("travel", query, Route.class).getContent().size());
        List<Route> all = routeDao.findAll();
        solrTemplate.saveBeans("travel", all);
        solrTemplate.commit("travel");
        return solrTemplate.query("travel", query, Route.class).getContent();
    }
}
