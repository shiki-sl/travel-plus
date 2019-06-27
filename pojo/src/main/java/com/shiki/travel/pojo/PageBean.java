package com.shiki.travel.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: shiki
 * @Date: 2019/1/9 14:30
 */
@NoArgsConstructor
@AllArgsConstructor
@Data

public class PageBean implements Serializable {
    /**
     * 总记录
     */
    private int totalCount;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 当前页条数
     */
    private int pageSize;

    /**
     * 保存当前页信息的list集合
     */
    private List<Route> list;

}
