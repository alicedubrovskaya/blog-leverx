package com.leverxblog.repository;

import com.leverxblog.entity.UserEntity;
import com.leverxblog.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUserEntity(UserEntity userEntity);
}
