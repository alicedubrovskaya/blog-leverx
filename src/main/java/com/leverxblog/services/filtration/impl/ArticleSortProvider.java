package com.leverxblog.services.filtration.impl;

import com.leverxblog.dao.entity.ArticleEntity;
import com.leverxblog.services.filtration.SortOrder;
import com.leverxblog.services.filtration.SortProvider;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public class ArticleSortProvider implements SortProvider<ArticleEntity> {

    private String sortBy;
    private SortOrder sortOrder;

    public ArticleSortProvider(String sortBy, String sortOrder) {
        this.sortBy = sortBy;
        this.sortOrder = sortOrder == null ? null : SortOrder.valueOf(sortOrder.toUpperCase());
    }

    @Override
    public Order[] getSortOrder(Root<ArticleEntity> root, CriteriaBuilder criteriaBuilder) {
        if (StringUtils.isEmpty(sortBy) && StringUtils.isEmpty(sortOrder)) {
            return new Order[]{};
        }
        if (sortOrder == SortOrder.DESC) {
            return new Order[]{criteriaBuilder.desc(root.get(sortBy))};
        }
        return new Order[]{criteriaBuilder.asc(root.get(sortBy))};
    }

}
