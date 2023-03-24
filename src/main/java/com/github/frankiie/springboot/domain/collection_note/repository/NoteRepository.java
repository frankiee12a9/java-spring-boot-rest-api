package com.github.frankiie.springboot.domain.collection_note.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domain.collection.payload.UpdateCollectionProps;
import com.github.frankiie.springboot.domain.collection_note.entity.Note;
import com.github.frankiie.springboot.domain.management.repository.SoftDeleteRepository;
import com.github.frankiie.springboot.domain.pagination.model.Page;

@Repository
public interface NoteRepository {
    Page<Note> findMany(Pageable pageable);
    Page<Note> findByKeyword(Pageable pageable, String keyword);
    Optional<Note> findById(Long id);
    Optional<Note> updateById(Long id, UpdateCollectionProps updateProps);
    void deleteById(Long id);
    void deleteAll(Iterable<? extends Note> entities);
    void delete(Note Note);
    Note save(Note Course);
}
