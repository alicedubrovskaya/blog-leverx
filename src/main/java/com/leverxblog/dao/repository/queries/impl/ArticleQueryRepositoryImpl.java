package com.leverxblog.dao.repository.queries.impl;

import com.leverxblog.dao.entity.ArticleEntity;
import com.leverxblog.services.filtration.impl.ArticleSortProvider;
import com.leverxblog.services.filtration.Page;
import com.leverxblog.dao.repository.queries.ArticleQueryRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@NoArgsConstructor
public class ArticleQueryRepositoryImpl implements ArticleQueryRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<ArticleEntity> findAll(Page page, ArticleSortProvider articleSortProvider) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ArticleEntity> criteriaQuery = criteriaBuilder.createQuery(ArticleEntity.class);
        Root<ArticleEntity> root = criteriaQuery.from(ArticleEntity.class);
        criteriaQuery.orderBy(articleSortProvider.getSortOrder(root, criteriaBuilder));
        Integer skip = page.getSkip();
        Integer limit = page.getLimit();
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(skip * limit - limit)
                .setMaxResults(limit)
                .getResultList();
    }
}
