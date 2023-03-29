package com.github.frankiie.springboot.domains.collection_note.repository.custom;

import static com.github.frankiie.springboot.domains.collection_note.repository.custom.NoteQueries.*;
import static com.github.frankiie.springboot.domains.session.service.SessionService.*;
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

import com.github.frankiie.springboot.domains.collection_image.entity.Image;
import com.github.frankiie.springboot.domains.collection_note.entity.Note;
import com.github.frankiie.springboot.domains.pagination.model.Page;

import static java.util.Optional.ofNullable;

@Repository
public class NativeQueryNoteRepositoryImpl implements NativeQueryNoteRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(NativeQueryNoteRepositoryImpl.class);

    @Autowired EntityManager manager;

    
    @Override
    public Optional<Note> findById(Long id) {
      var query = manager
              .createNativeQuery(FIND_BY_ID, Tuple.class)
                  .setParameter("note_id", id)
                  .setParameter("owner_id", serviceOwnerId());
      try {
          var tuple = (Tuple) query.getSingleResult();
          return of(Note.from(tuple));
      } catch (NoResultException exception) {
          return empty();
      }
    }

    @Override
    public Optional<Note> findByIdAndFetchImages(Long id) {
      var query = manager
              .createNativeQuery(FIND_BY_ID_AND_FETCH_IMAGES, Tuple.class)
                  .setParameter("note_id", id)
                  .setParameter("owner_id", serviceOwnerId());
      try {
          var tuple = (Tuple) query.getSingleResult();
          return of(Note.fromWithImages(tuple));
      } catch (NoResultException exception) {
          return empty();
      }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Page<Note> findByKeyword(Pageable pageable, String keyword) {
      var query = manager
                .createNativeQuery(FIND_BY_KEYWORD, Tuple.class)
                    .setParameter("keyword", keyword)
                    .setParameter("owner_id", serviceOwnerId());

      var count = ((BigInteger) manager
          .createNativeQuery(NoteQueries.COUNT_BY_KEYWORD)
          .setParameter("keyword", keyword)
          .setParameter("owner_id", serviceOwnerId())
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
                .createNativeQuery(FIND_BY_COLLECTION_ID, Tuple.class)
                    .setParameter("collection_id", collectionId)
                    .setParameter("owner_id", serviceOwnerId());

      var count = ((BigInteger) manager
          .createNativeQuery(NoteQueries.COUNT_BY_COLLECTION_ID) 
          .setParameter("collection_id", collectionId)
          .setParameter("owner_id", serviceOwnerId())
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
              .createNativeQuery(FIND_MANY, Tuple.class)
              .setParameter("owner_id", serviceOwnerId());
      
      var count = ((BigInteger) manager
          .createNativeQuery(NoteQueries.COUNT_MANY)
          .setParameter("owner_id", serviceOwnerId())
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
              .createNativeQuery(FIND_MANY_WITH_FILTER, Tuple.class)
              .setParameter("filter", filter)
              .setParameter("owner_id", serviceOwnerId());
      
      var count = ((BigInteger) manager
          .createNativeQuery(NoteQueries.COUNT_MANY_WITH_FILTER)
          .setParameter("filter", filter)
          .setParameter("owner_id", serviceOwnerId())
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
                .createNativeQuery(FIND_IMAGES_BY_ID, Tuple.class)
                .setParameter("note_id", id)
                .setParameter("owner_id", serviceOwnerId());

      var count = ((BigInteger) manager
          .createNativeQuery(NoteQueries.COUNT_IMAGES_BY_ID)
          .setParameter("note_id", id)
          .setParameter("owner_id", serviceOwnerId())
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
