package com.github.frankiie.springboot.domain.collection_note.repository.custom;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domain.collection_image.entity.Image;
import com.github.frankiie.springboot.domain.collection_note.entity.Note;
import com.github.frankiie.springboot.domain.pagination.model.Page;

import java.util.Optional;

@Repository
public interface NativeQueryNoteRepository {
    Optional<Note> findById(Long id);
    // NOTE: this might me moved to `Comment` service
    Page<Note> findByCollectionId(Long collectionId, Pageable pageable);  
    // NOTE: this might need to be moved to `Comment` service
    Page<Image> findImagesById(Long id, Pageable pageable);  
    Page<Note> findByKeyword(Pageable pageable, String keyword);
    Page<Note> findMany(Pageable pageable);
    Page<Note> findManyWithFilter(String filter, Pageable pageable);
}
