package com.leverxblog.services.filtration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public interface SortProvider<T> {
    Order[] getSortOrder(Root<T> root, CriteriaBuilder criteriaBuilder);
}
