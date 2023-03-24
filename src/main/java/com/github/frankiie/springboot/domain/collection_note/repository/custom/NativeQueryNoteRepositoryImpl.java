package com.github.frankiie.springboot.domain.collection_note.repository.custom;

import static com.github.frankiie.springboot.domain.collection_note.repository.custom.NoteQueries.*;
// import static com.github.frankiie.springboot.domain.Note.repository.custom.Queries.FIND_BY_ID_AND_FETCH_COMMENTS;
// import static com.github.frankiie.springboot.domain.Note.repository.custom.CollectionQueries.FIND_BY_KEYWORD;
// import static com.github.frankiie.springboot.domain.Note.repository.custom.CollectionQueries.COUNT_ACTIVE_COLLECTION_COMMENTS;
// import static com.github.frankiie.springboot.domain.Note.repository.custom.CollectionQueries.FIND_MANY;
// import static com.github.frankiie.springboot.domain.Note.repository.custom.CollectionQueries.COUNT_ACTIVE_COLLECTIONS;
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

import com.github.frankiie.springboot.domain.collection.payload.UpdateCollectionProps;
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
          .createNativeQuery(NoteQueries.COUNT_ACTIVE_NOTES)
          .getSingleResult())
            .longValue();
      
      var pageNumber = pageable.getPageNumber();
      var pageSize = pageable.getPageSize();

      query.setFirstResult(pageNumber * pageSize);
      query.setMaxResults(pageSize);

      List<Tuple> content = query.getResultList();

      var courses = content.stream().map(Note::from).toList();

      return Page.of(courses, pageNumber, pageSize, count);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Note> findByCollectionId(Long collectionId, Pageable pageable) {
      var query = manager
                .createNativeQuery(NoteQueries.FIND_BY_COLLECTION_ID, Tuple.class)
                    .setParameter("collection_id", collectionId);

      var count = ((BigInteger) manager
          .createNativeQuery(NoteQueries.COUNT_ACTIVE_NOTES)
          .getSingleResult())
            .longValue();
      
      var pageNumber = pageable.getPageNumber();
      var pageSize = pageable.getPageSize();

      query.setFirstResult(pageNumber * pageSize);
      query.setMaxResults(pageSize);

      List<Tuple> content = query.getResultList();

      var courses = content.stream().map(Note::from).toList();

      return Page.of(courses, pageNumber, pageSize, count);
    } 


    @Override
    @SuppressWarnings("unchecked")
    public Page<Note> findMany(Pageable pageable) {
      var query = manager
              .createNativeQuery(NoteQueries.FIND_MANY, Tuple.class);
      
      var count = ((BigInteger) manager
          .createNativeQuery(NoteQueries.COUNT_ACTIVE_NOTES)
          .getSingleResult())
            .longValue();

      var pageNumber = pageable.getPageNumber();
      var pageSize = pageable.getPageSize();

      query.setFirstResult(pageNumber * pageSize);
      query.setMaxResults(pageSize);

      List<Tuple> content = query.getResultList();

      var courses = content.stream().map(Note::from).toList();

      return Page.of(courses, pageNumber, pageSize, count);
    }

    @javax.transaction.Transactional
    @Override
    public Optional<Note> updateById(Long id, UpdateCollectionProps updateProps) {
      return null;
      // LOGGER.info("props: " + updateProps);

      // String updateQuery = "UPDATE Note SET ";
      // if (updateProps.getTitle() != null) {
      //     updateQuery += "collection_title = :collection_title";
      //     if (updateProps.getDescription() != null) {
      //         updateQuery += ", ";
      //     }
      // }
      // if (updateProps.getDescription() != null) {
      //     updateQuery += "collection_desc = :collection_desc";
      // }
      // updateQuery += " WHERE id = :id";

      // LOGGER.info("update query: " + updateQuery);

      // var query = manager.createNativeQuery(updateQuery);
      // query.setParameter("id", id);

      // if (updateProps.getTitle() != null) {
      //   query.setParameter("collection_title", updateProps.getTitle());
      // } 
      // if (updateProps.getDescription() != null) {
      //   query.setParameter("collection_desc", updateProps.getDescription());
      // }

      // try {
      //   query.executeUpdate();
      // } catch (Exception e) {
      //   e.printStackTrace();
      // }
      
      // var query2 = manager
      //         .createNativeQuery(FIND_BY_ID_DETAILS, Tuple.class)
      //             .setParameter("collection_id", id);

      // LOGGER.info("FIND_BY_ID_DETAILS: " + query2);

      // try {
      //     var tuple = (Tuple) query2.getSingleResult();
      //     return of(Note.from(tuple));
      // } catch (NoResultException exception) {
      //     return empty();
      // }
    }

    @Override
    public Optional<Note> findByIdAndFetchComments(Long id) {
      return null;
      //  var query = manager
      //           .createNativeQuery(FIND_BY_ID_AND_FETCH_COMMENTS, Tuple.class)
      //               .setParameter("collection_id", id);
      //   try {
      //       var tuple = (Tuple) query.getSingleResult();
      //       return of(Note.from(tuple));
      //   } catch (NoResultException exception) {
      //       return empty();
      //   }
    }

}
