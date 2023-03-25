package com.github.frankiie.springboot.domain.collection_note_comment.repository;

import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domain.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domain.management.repository.SoftDeleteRepository;

@Repository
public interface CommentRepository extends SoftDeleteRepository<Comment> {
}
