package com.github.frankiie.springboot.domain.collection_note.repository.springdata;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.frankiie.springboot.domain.collection_note.entity.Note;
import com.github.frankiie.springboot.domain.management.repository.SoftDeleteRepository;

import java.util.Optional;

@Repository
public interface SpringDataNoteRepository extends SoftDeleteRepository<Note> {
    @Override
    @Transactional
    @Modifying
    // @Query(DELETE_BY_ID)
    void deleteById(Long id);

    @Override
    @Transactional
    default void delete(Note note) {
        deleteById(note.getId());
    }

    @Override
    @Transactional
    default void deleteAll(Iterable<? extends Note> entities) {
        entities.forEach(entity -> deleteById(entity.getId()));
    }

}