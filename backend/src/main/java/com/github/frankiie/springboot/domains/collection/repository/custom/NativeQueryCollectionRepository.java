package com.github.frankiie.springboot.domains.collection.repository.custom;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domains.collection.entity.Collection;
import com.github.frankiie.springboot.domains.collection.payload.UpdateCollectionProps;
import com.github.frankiie.springboot.domains.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domains.pagination.model.Page;

import java.util.Optional;

@Repository
public interface NativeQueryCollectionRepository {
    Optional<Collection> findById(Long id);
    Optional<Collection> findByIdAndFetchComments(Long id);
    // note: this might need to be moved to the `Comment` service
    Page<Collection> findByKeyword(Pageable pageable, String keyword);
    Page<Collection> findMany(Pageable pageable);
    Optional<Collection> updateById(Long id, UpdateCollectionProps props);
    Page<Comment> findCommentsById(Long id, Pageable pageable);  
}
