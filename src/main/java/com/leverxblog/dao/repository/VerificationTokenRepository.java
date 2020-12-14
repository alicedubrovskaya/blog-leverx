package com.leverxblog.dao.repository;

import com.leverxblog.dao.entity.security.VerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, Long> {

    VerificationTokenEntity findByToken(String token);
}
