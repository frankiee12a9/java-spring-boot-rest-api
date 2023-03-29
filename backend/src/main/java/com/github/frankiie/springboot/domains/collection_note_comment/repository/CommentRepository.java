package com.github.frankiie.springboot.domains.collection_note_comment.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.frankiie.springboot.domains.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domains.management.repository.SoftDeleteRepository;

@Repository
public interface CommentRepository extends SoftDeleteRepository<Comment> {
    @Override
    @Query("delete from Comment c where c.id = :id")
    public void deleteById(@Param("id") Long id);

    @Transactional 
    @Modifying
    @Query("delete from Comment c where c.collection.id = :collectionId")
    public void deleteByCollectionId(@Param("collectionId") Long collectionId);
}
