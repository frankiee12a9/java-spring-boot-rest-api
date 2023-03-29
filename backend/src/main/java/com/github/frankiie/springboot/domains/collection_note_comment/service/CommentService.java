package com.github.frankiie.springboot.domains.collection_note_comment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domains.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domains.collection_note_comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
    @Autowired private final CommentRepository commentRepository;

    public Comment create(Comment comment) {
      return commentRepository.save(comment); 
    }

    public void deleteById(Long id) {
      commentRepository.deleteById(id);
    }

    public void deleteByCollectionId(Long collectionId) {
      commentRepository.deleteByCollectionId(collectionId);
    }

}
