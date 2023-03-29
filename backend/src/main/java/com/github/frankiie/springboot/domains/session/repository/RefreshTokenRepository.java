package com.github.frankiie.springboot.domains.session.repository;

import static com.github.frankiie.springboot.domains.session.repository.Queries.DISABLE_OLD_REFRESH_TOKENS_FROM_USER;
import static com.github.frankiie.springboot.domains.session.repository.Queries.FIND_REFRESH_TOKEN_BY_CODE_FETCH_USER_AND_ROLES;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domains.session.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>, JpaSpecificationExecutor<RefreshToken> {
    @Transactional
    @Modifying
    @Query(DISABLE_OLD_REFRESH_TOKENS_FROM_USER)
    public void disableOldRefreshTokens(Long id);

    @Query(FIND_REFRESH_TOKEN_BY_CODE_FETCH_USER_AND_ROLES)
    public Optional<RefreshToken> findOptionalByCodeAndAvailableIsTrue(String code);
}
