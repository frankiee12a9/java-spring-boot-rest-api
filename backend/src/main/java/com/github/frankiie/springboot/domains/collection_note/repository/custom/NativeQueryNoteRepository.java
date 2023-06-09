package com.github.frankiie.springboot.domains.collection_note.repository.custom;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domains.collection_image.entity.Image;
import com.github.frankiie.springboot.domains.collection_note.entity.Note;
import com.github.frankiie.springboot.domains.pagination.model.Page;

import java.util.Optional;

@Repository
public interface NativeQueryNoteRepository {
    Optional<Note> findById(Long id);
    Optional<Note> findByIdAndFetchImages(Long id);
    // note: this might me moved to `Comment` service
    Page<Note> findByCollectionId(Long collectionId, Pageable pageable);  
    // note: this might need to be moved to `Comment` service
    Page<Image> findImagesById(Long id, Pageable pageable);  
    Page<Note> findByKeyword(Pageable pageable, String keyword);
    Page<Note> findMany(Pageable pageable);
    Page<Note> findManyWithFilter(String filter, Pageable pageable);
}
