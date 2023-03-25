package com.github.frankiie.springboot.domain.collection_note.repository.custom;

import static com.github.frankiie.springboot.domain.collection_note.repository.custom.NoteQueries.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.List;
import java.util.Optional;
import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Tuple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domain.collection_image.entity.Image;
import com.github.frankiie.springboot.domain.collection_note.entity.Note;
import com.github.frankiie.springboot.domain.pagination.model.Page;

@Repository
public class NativeQueryNoteRepositoryImpl implements NativeQueryNoteRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(NativeQueryNoteRepositoryImpl.class);

    @Autowired EntityManager manager;

    @Override
    public Optional<Note> findById(Long id) {
      var query = manager
              .createNativeQuery(NoteQueries.FIND_BY_ID, Tuple.class)
                  .setParameter("note_id", id);
      try {
          var tuple = (Tuple) query.getSingleResult();
          return of(Note.from(tuple));
      } catch (NoResultException exception) {
          return empty();
      }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Page<Note> findByKeyword(Pageable pageable, String keyword) {
      var query = manager
                .createNativeQuery(NoteQueries.FIND_BY_KEYWORD, Tuple.class)
                    .setParameter("keyword", keyword);

      var count = ((BigInteger) manager
          .createNativeQuery(NoteQueries.COUNT_BY_KEYWORD)
          .setParameter("keyword", keyword)
          .getSingleResult())
            .longValue();
      
      var pageNumber = pageable.getPageNumber();
      var pageSize = pageable.getPageSize();

      query.setFirstResult(pageNumber * pageSize);
      query.setMaxResults(pageSize);

      List<Tuple> content = query.getResultList();
      var notes = content.stream().map(Note::from).toList();
      return Page.of(notes, pageNumber, pageSize, count);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Note> findByCollectionId(Long collectionId, Pageable pageable) {
      var query = manager
                .createNativeQuery(NoteQueries.FIND_BY_COLLECTION_ID, Tuple.class)
                    .setParameter("collection_id", collectionId);

      var count = ((BigInteger) manager
          .createNativeQuery(NoteQueries.COUNT_BY_COLLECTION_ID) 
          .setParameter("collection_id", collectionId)
          .getSingleResult())
            .longValue();
      
      var pageNumber = pageable.getPageNumber();
      var pageSize = pageable.getPageSize();

      query.setFirstResult(pageNumber * pageSize);
      query.setMaxResults(pageSize);

      List<Tuple> content = query.getResultList();
      var notes = content.stream().map(Note::from).toList();
      return Page.of(notes, pageNumber, pageSize, count);
    } 

    @Override
    @SuppressWarnings("unchecked")
    public Page<Note> findMany(Pageable pageable) {
      var query = manager
              .createNativeQuery(NoteQueries.FIND_MANY, Tuple.class);
      
      var count = ((BigInteger) manager
          .createNativeQuery(NoteQueries.COUNT_MANY)
          .getSingleResult())
            .longValue();

      var pageNumber = pageable.getPageNumber();
      var pageSize = pageable.getPageSize();

      query.setFirstResult(pageNumber * pageSize);
      query.setMaxResults(pageSize);

      List<Tuple> content = query.getResultList();
      var notes = content.stream().map(Note::from).toList();
      return Page.of(notes, pageNumber, pageSize, count);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Page<Note> findManyWithFilter(String filter, Pageable pageable) {
      var query = manager
              .createNativeQuery(NoteQueries.FIND_MANY_WITH_FILTER, Tuple.class)
              .setParameter("filter", filter);
      
      var count = ((BigInteger) manager
          .createNativeQuery(NoteQueries.COUNT_MANY_WITH_FILTER)
          .setParameter("filter", filter)
          .getSingleResult())
            .longValue();

      var pageNumber = pageable.getPageNumber();
      var pageSize = pageable.getPageSize();

      query.setFirstResult(pageNumber * pageSize);
      query.setMaxResults(pageSize);

      List<Tuple> content = query.getResultList();
      var notes = content.stream().map(Note::from).toList();
      return Page.of(notes, pageNumber, pageSize, count);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Image> findImagesById(Long id, Pageable pageable) {
      var query = manager
                .createNativeQuery(NoteQueries.FIND_IMAGES_BY_ID, Tuple.class)
                    .setParameter("note_id", id);

      var count = ((BigInteger) manager
          .createNativeQuery(NoteQueries.COUNT_IMAGES_BY_ID)
          .setParameter("note_id", id)
          .getSingleResult())
            .longValue();
      
      var pageNumber = pageable.getPageNumber();
      var pageSize = pageable.getPageSize();

      query.setFirstResult(pageNumber * pageSize);
      query.setMaxResults(pageSize);

      List<Tuple> content = query.getResultList();
      var images = content.stream().map(Image::from).toList();
      return Page.of(images, pageNumber, pageSize, count);
    }

}
