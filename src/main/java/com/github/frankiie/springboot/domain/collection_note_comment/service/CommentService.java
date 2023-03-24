package com.github.frankiie.springboot.domain.collection_note_comment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domain.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domain.collection_note_comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

    @Autowired private final CommentRepository commentRepository;

    public Comment create(Comment comment) {
      return commentRepository.save(comment); 
    }
}
