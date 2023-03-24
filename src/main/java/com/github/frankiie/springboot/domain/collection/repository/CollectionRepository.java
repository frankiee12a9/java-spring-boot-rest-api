package com.github.frankiie.springboot.domain.collection.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domain.collection.entity.Collection;
import com.github.frankiie.springboot.domain.collection.payload.UpdateCollectionProps;
import com.github.frankiie.springboot.domain.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domain.pagination.model.Page;

@Repository
public interface CollectionRepository {
    Page<Collection> findMany(Pageable pageable);
    Page<Collection> findByKeyword(Pageable pageable, String keyword);
    Page<Comment> findCommentsById(Long id, Pageable pageable);
    Optional<Collection> findById(Long id);
    Optional<Collection> findByIdAndFetchComments(Long id);
    Optional<Collection> updateById(Long id, UpdateCollectionProps updateProps);
    Boolean existsById(Long id);
    void deleteById(Long id);
    void deleteAll(Iterable<? extends Collection> entities);
    void delete(Collection collection);
    Collection save(Collection Course);
}