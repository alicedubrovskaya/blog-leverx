package com.leverxblog.dao.repository;

import com.leverxblog.dao.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

     TagEntity findByName(String tagName);
}
