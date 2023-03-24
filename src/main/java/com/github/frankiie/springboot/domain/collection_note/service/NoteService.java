package com.github.frankiie.springboot.domain.collection_note.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domain.collection_note.entity.Note;
import com.github.frankiie.springboot.domain.collection_note.payload.CreateNoteProps;
import com.github.frankiie.springboot.domain.collection_note.repository.NoteRepository;
import com.github.frankiie.springboot.domain.pagination.model.Page;
import com.github.frankiie.springboot.domain.pagination.service.Pagination;
import com.github.frankiie.springboot.utils.Responses;

import lombok.RequiredArgsConstructor;

import static com.github.frankiie.springboot.utils.Messages.*;


@RequiredArgsConstructor
@Service
public class NoteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteService.class);

    @Autowired private final NoteRepository noteRepository;
  
    public Note create(CreateNoteProps createProps) {
      return save(createProps);
    }

    public Note findById(Long id) {
      return noteRepository.findById(id)
        .orElseThrow(() -> Responses.notFound("note not found"));
    }

    public Page<Note> findByKeyword(String keyword, Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return noteRepository.findByKeyword(pageable, keyword);
    }

    public Page<Note> findMany(Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return noteRepository.findMany(pageable);
    }

    private Note save(CreateNoteProps createProps) {
      var note = new Note(createProps);
      noteRepository.save(note);
      return note;
    }
}
