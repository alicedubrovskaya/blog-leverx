package com.leverxblog.dao.repository;

import com.leverxblog.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByLogin(String login);

    UserEntity findByLogin(String login);

    UserEntity findByEmail(String email);
}
