package com.shiki.travel.dao;

import com.shiki.travel.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author shiki
 * 分类实体类
 */

public interface CategoryDao extends JpaRepository<Category,Integer>, JpaSpecificationExecutor<Category> {
}
