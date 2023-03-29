package com.github.frankiie.springboot.domains.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domains.pagination.model.Page;
import com.github.frankiie.springboot.domains.user.entity.User;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository {
    Page<User> findAll(Pageable pageable);
    Optional<User> findById(Long id);
    Optional<User> findByIdFetchRoles(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Boolean existsByEmail(String email);
    void deleteById(Long id);
    void deleteAll(Iterable<? extends User> entities);
    void delete(User user);
    User save(User user);
    Collection<User> saveAll(Collection<User> users);
}
