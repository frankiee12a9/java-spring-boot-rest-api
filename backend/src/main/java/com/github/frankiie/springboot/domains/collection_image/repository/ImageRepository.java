package com.github.frankiie.springboot.domains.collection_image.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.frankiie.springboot.domains.collection_image.entity.Image;
import com.github.frankiie.springboot.domains.management.repository.SoftDeleteRepository;

@Repository
public interface ImageRepository extends SoftDeleteRepository<Image> {
    @Override
    @Query("delete from Image i where i.id = :id")
    public void deleteById(@Param("id") Long id);

    @Transactional 
    @Modifying
    @Query("delete from Image i where i.collection.id = :collectionId")
    public void deleteByCollectionId(@Param("collectionId") Long collectionId);

    @Transactional 
    @Modifying
    @Query("delete from #{#entityName} i where i.note.id = :noteId")
    public void deleteByNoteId(@Param("noteId") Long noteId);
}
