package com.leverxblog.dao.repository.queries;

import com.leverxblog.dao.entity.CommentEntity;
import com.leverxblog.services.filtration.Page;
import com.leverxblog.services.filtration.impl.CommentSortProvider;

import java.util.List;

public interface CommentQueryRepository {

    List<CommentEntity> findAll(Page page, CommentSortProvider commentSortProvider);
}
