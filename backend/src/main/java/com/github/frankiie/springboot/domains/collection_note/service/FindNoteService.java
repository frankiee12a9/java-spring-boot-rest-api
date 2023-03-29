package com.github.frankiie.springboot.domains.collection_note.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domains.collection_image.entity.Image;
import com.github.frankiie.springboot.domains.collection_note.entity.Note;
import com.github.frankiie.springboot.domains.collection_note.repository.NoteRepository;
import com.github.frankiie.springboot.domains.pagination.model.Page;
import com.github.frankiie.springboot.domains.pagination.service.Pagination;
import com.github.frankiie.springboot.utils.Responses;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FindNoteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindNoteService.class);

    @Autowired private final NoteRepository noteRepository;

    public Note findById(Long id) {
      return noteRepository.findById(id)
              .orElseThrow(() -> Responses.notFound("note not found"));
    }

    public Note findByIdAndFetchImages(Long id) {
      return noteRepository.findByIdAndFetchImages(id)
              .orElseThrow(() -> Responses.notFound("note not found"));
    }

    public Page<Image> findImagesById(Long id, Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return noteRepository.findImagesById(id, pageable);
    }

    public Page<Note> findByKeyword(String keyword, Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return noteRepository.findByKeyword(pageable, keyword);
    }

    public Page<Note> findByCollectionId(Long collectionId, Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return noteRepository.findByCollectionId(collectionId, pageable);
    }

    public Page<Note> findMany(Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return noteRepository.findMany(pageable);
    }

    public Page<Note> findManyWithFilter(String filter, Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return noteRepository.findManyWithFilter(filter, pageable); 
    }

}
