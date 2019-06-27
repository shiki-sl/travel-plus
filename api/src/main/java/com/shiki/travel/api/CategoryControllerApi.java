package com.shiki.travel.api;

import com.shiki.travel.pojo.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: shiki
 * @Date: 2019/05/04 11:30
 */
@Api("标头接口")
public interface CategoryControllerApi {
    @ApiOperation("查询全部分类")
    ResultInfo findAll();
}
