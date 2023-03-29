package com.github.frankiie.springboot.domains.collection.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domains.collection.entity.Collection;
import com.github.frankiie.springboot.domains.collection.repository.CollectionRepository;
import com.github.frankiie.springboot.domains.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domains.pagination.model.Page;
import com.github.frankiie.springboot.domains.pagination.service.Pagination;
import com.github.frankiie.springboot.utils.Responses;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FindCollectionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateCollectionService.class);

    @Autowired private final CollectionRepository collectionRepository;

    public Collection findById(Long id) {
      return collectionRepository.findById(id)
        .orElseThrow(() -> Responses.notFound("collection not found"));
    }

    public Page<Comment> findCommentsById(Long id, Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return collectionRepository.findCommentsById(id, pageable);
    }
  
    public Page<Collection> findByKeyword(String keyword, Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return collectionRepository.findByKeyword(pageable, keyword);
    }

    public Page<Collection> findMany(Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return collectionRepository.findMany(pageable);
    }
}
