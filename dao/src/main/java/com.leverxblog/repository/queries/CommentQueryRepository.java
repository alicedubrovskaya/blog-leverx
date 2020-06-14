package com.leverxblog.repository.queries;

import com.leverxblog.entity.CommentEntity;
import com.leverxblog.filtration.Page;
import com.leverxblog.filtration.impl.CommentSortProvider;

import java.util.List;

public interface CommentQueryRepository {

    List<CommentEntity> findAll(Page page, CommentSortProvider commentSortProvider);
}
