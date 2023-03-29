package com.github.frankiie.springboot.domains.collection_note.repository;

import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domains.collection.payload.UpdateCollectionProps;
import com.github.frankiie.springboot.domains.collection_image.entity.Image;
import com.github.frankiie.springboot.domains.collection_note.entity.Note;
import com.github.frankiie.springboot.domains.pagination.model.Page;

@Repository
public interface NoteRepository {
    Optional<Note> findById(Long id);
    Optional<Note> findByIdAndFetchImages(Long id);
    Page<Note> findMany(Pageable pageable);
    @QueryHints({ @QueryHint(name = "foo", value = "bar") })
    Page<Note> findManyWithFilter(String filter, Pageable pageable);
    Page<Note> findByKeyword(Pageable pageable, String keyword);
    Page<Note> findByCollectionId(Long collectionId, Pageable pageable);
    Page<Image> findImagesById(Long id, Pageable pageable);
    // Optional<Note> updateById(Long id, UpdateCollectionProps updateProps);
    void deleteById(Long id);
    void deleteAll(Iterable<? extends Note> entities);
    void delete(Note Note);
    Note save(Note Course);
}
