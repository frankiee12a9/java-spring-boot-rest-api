package com.github.frankiie.springboot.domain.collection_note.repository.custom;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domain.collection.payload.UpdateCollectionProps;
import com.github.frankiie.springboot.domain.collection_note.entity.Note;
import com.github.frankiie.springboot.domain.pagination.model.Page;

import java.util.Optional;

@Repository
public interface NativeQueryNoteRepository {
    Optional<Note> findById(Long id);
    Optional<Note> findByIdAndFetchComments(Long id);
    Page<Note> findByCollectionId(Long collectionId, Pageable pageable);  // NOTE: this might me moved to `Comment` service
    Page<Note> findByKeyword(Pageable pageable, String keyword);
    Page<Note> findMany(Pageable pageable);
    Optional<Note> updateById(Long id, UpdateCollectionProps props);
}
