package com.github.frankiie.springboot.domains.collection.repository.springdata;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.frankiie.springboot.domains.collection.entity.Collection;
import com.github.frankiie.springboot.domains.management.repository.SoftDeleteRepository;

import java.util.Optional;

@Repository
public interface SpringDataCollectionRepository extends SoftDeleteRepository<Collection> {

    @Override
    @Transactional
    @Modifying
    // @Query(DELETE_BY_ID)
    void deleteById(Long id);

    @Override
    @Transactional
    default void delete(Collection collection) {
        deleteById(collection.getId());
    }

    @Override
    @Transactional
    default void deleteAll(Iterable<? extends Collection> entities) {
        entities.forEach(entity -> deleteById(entity.getId()));
    }

}