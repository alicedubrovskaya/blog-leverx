package com.leverxblog.repository;

import com.leverxblog.entity.UserEntity;
import com.leverxblog.entity.VerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, Long> {

    VerificationTokenEntity findByToken(String token);
}
