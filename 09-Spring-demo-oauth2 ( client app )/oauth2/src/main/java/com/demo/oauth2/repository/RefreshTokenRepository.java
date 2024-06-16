package com.demo.oauth2.repository;

import com.demo.oauth2.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken , String> {


    @Query("SELECT t FROM RefreshToken t WHERE t.refreshToken = :token")
    public RefreshToken getRefreshToken(String token);

    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken t SET t.expired = true, t.revoked = true WHERE t.refreshToken = :token")
    void invalidateRefreshToken(@Param("token") String token);
}
