package com.leverxblog.repository.queries.impl;

import com.leverxblog.entity.CommentEntity;
import com.leverxblog.filtration.impl.ArticleSortProvider;
import com.leverxblog.filtration.Page;
import com.leverxblog.filtration.impl.CommentSortProvider;
import com.leverxblog.repository.queries.CommentQueryRepository;
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
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<CommentEntity> findAll(Page page, CommentSortProvider commentSortProvider) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CommentEntity> criteriaQuery = criteriaBuilder.createQuery(CommentEntity.class);
        Root<CommentEntity> root = criteriaQuery.from(CommentEntity.class);
        criteriaQuery.orderBy(commentSortProvider.getSortOrder(root, criteriaBuilder));
        Integer skip = page.getSkip();
        Integer limit = page.getLimit();
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(skip * limit - limit)
                .setMaxResults(limit)
                .getResultList();
    }
}
