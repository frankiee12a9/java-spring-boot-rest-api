package com.github.frankiie.springboot.domains.user.repository.custom;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domains.pagination.model.Page;
import com.github.frankiie.springboot.domains.user.entity.User;

import java.util.Optional;

@Repository
public interface NativeQueryUserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);
}
