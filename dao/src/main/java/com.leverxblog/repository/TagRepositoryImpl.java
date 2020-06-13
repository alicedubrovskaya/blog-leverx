package com.leverxblog.repository;


import com.leverxblog.entity.ArticleEntity;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Repository
@NoArgsConstructor
public class TagRepositoryImpl implements TagRepositoryQuery{

    private static final String SELECT_FROM_ARTICLES_TAGS_WHERE_TAG_ID =
            "SELECT * FROM articles_tags WHERE tag_id = ?";
    private static final String SELECT_FROM_ARTICLES_TAGS_WHERE_TAG_ID_IN_LIST=
            "SELECT DISTINCT BIN_TO_UUID(article_id)  FROM articles_tags WHERE tag_id IN :list";

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public int amountOfArticlesByTagId(Long tagId) {
        Query query = entityManager.createNativeQuery(SELECT_FROM_ARTICLES_TAGS_WHERE_TAG_ID);
        query.setParameter(1, tagId);
        return query.getResultList().size();
    }

    @Override
    public List<ArticleEntity> findByTagsIds(List<Long> ids) {
        Query query=entityManager.createNativeQuery(SELECT_FROM_ARTICLES_TAGS_WHERE_TAG_ID_IN_LIST);
        query.setParameter("list",ids);
        return query.getResultList();
    }
}
