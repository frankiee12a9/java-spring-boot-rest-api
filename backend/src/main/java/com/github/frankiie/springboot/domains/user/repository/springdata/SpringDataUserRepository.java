package com.github.frankiie.springboot.domains.user.repository.springdata;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.frankiie.springboot.domains.management.repository.SoftDeleteRepository;
import com.github.frankiie.springboot.domains.user.entity.User;

import static com.github.frankiie.springboot.domains.user.repository.custom.UserQueries.DELETE_BY_ID;

import java.util.Optional;

@Repository
public interface SpringDataUserRepository extends SoftDeleteRepository<User> {

    @Override
    @Transactional
    @Modifying
    @Query(DELETE_BY_ID)
    void deleteById(Long id);

    @Override
    @Transactional
    default void delete(User user) {
        deleteById(user.getId());
    }

    @Override
    @Transactional
    default void deleteAll(Iterable<? extends User> entities) {
        entities.forEach(entity -> deleteById(entity.getId()));
    }

    Boolean existsByEmail(String email);

    @Query("""
        select user from #{#entityName} user
        left join fetch user.roles
        where user.id = ?1 
    """)
    Optional<User> findByIdFetchRoles(Long id);

}