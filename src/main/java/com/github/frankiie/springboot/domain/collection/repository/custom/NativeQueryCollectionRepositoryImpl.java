package com.github.frankiie.springboot.domain.collection.repository.custom;

import static com.github.frankiie.springboot.domain.collection.repository.custom.CollectionQueries.*;

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

import com.github.frankiie.springboot.domain.collection.entity.Collection;
import com.github.frankiie.springboot.domain.collection.payload.UpdateCollectionProps;
import com.github.frankiie.springboot.domain.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domain.pagination.model.Page;

@Repository
public class NativeQueryCollectionRepositoryImpl implements NativeQueryCollectionRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(NativeQueryCollectionRepositoryImpl.class);

    @Autowired EntityManager manager;

    @Override
    public Optional<Collection> findById(Long id) {
        var query = manager
                .createNativeQuery(FIND_BY_ID_DETAILS, Tuple.class)
                    .setParameter("collection_id", id);
        try {
            var tuple = (Tuple) query.getSingleResult();
            return of(Collection.from(tuple));
        } catch (NoResultException exception) {
            return empty();
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Page<Comment> findCommentsById(Long id, Pageable pageable) {
       var query = manager
                .createNativeQuery(FIND_BY_ID_AND_FETCH_COMMENTS, Tuple.class)
                    .setParameter("collection_id", id);

       var count = ((BigInteger) manager
          .createNativeQuery(COUNT_ACTIVE_COLLECTION_COMMENTS)
          .setParameter("collection_id", id)
          .getSingleResult())
            .longValue();

      var pageNumber = pageable.getPageNumber();
      var pageSize = pageable.getPageSize();

      query.setFirstResult(pageNumber * pageSize);
      query.setMaxResults(pageSize);

      List<Tuple> content = query.getResultList();

      var comments = content.stream().map(Comment::from).toList();

      return Page.of(comments, pageNumber, pageSize, count);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Collection> findByKeyword(Pageable pageable, String keyword) {
      var query = manager
                .createNativeQuery(FIND_BY_KEYWORD_AND_GET_A_ASSOCIATION, Tuple.class)
                    .setParameter("keyword", keyword);

      var count = ((BigInteger) manager
          .createNativeQuery(COUNT_BY_KEYWORD_AND_GET_A_ASSOCIATION)
          .setParameter("keyword", keyword)
          .getSingleResult())
            .longValue();
      
      var pageNumber = pageable.getPageNumber();
      var pageSize = pageable.getPageSize();

      query.setFirstResult(pageNumber * pageSize);
      query.setMaxResults(pageSize);

      List<Tuple> content = query.getResultList();

      var collections = content.stream().map(Collection::from).toList();

      return Page.of(collections, pageNumber, pageSize, count);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Collection> findMany(Pageable pageable) {
      var query = manager
              .createNativeQuery(FIND_MANY, Tuple.class);
      
      var count = ((BigInteger) manager
          .createNativeQuery(COUNT_ACTIVE_COLLECTIONS)
          .getSingleResult())
            .longValue();

      var pageNumber = pageable.getPageNumber();
      var pageSize = pageable.getPageSize();

      query.setFirstResult(pageNumber * pageSize);
      query.setMaxResults(pageSize);

      List<Tuple> content = query.getResultList();

      var collections = content.stream().map(Collection::from).toList();

      return Page.of(collections, pageNumber, pageSize, count);
    }

    @javax.transaction.Transactional
    @Override
    public Optional<Collection> updateById(Long id, UpdateCollectionProps updateProps) {
      String updateQuery = "UPDATE collection SET ";
      if (updateProps.getTitle() != null) {
          updateQuery += "collection_title = :collection_title";
          if (updateProps.getDescription() != null) {
              updateQuery += ", ";
          }
      }
      if (updateProps.getDescription() != null) {
          updateQuery += "collection_desc = :collection_desc";
      }
      updateQuery += " WHERE id = :id";

      LOGGER.info("update query: " + updateQuery);

      var query = manager.createNativeQuery(updateQuery);
      query.setParameter("id", id);

      if (updateProps.getTitle() != null) {
        query.setParameter("collection_title", updateProps.getTitle());
      } 
      if (updateProps.getDescription() != null) {
        query.setParameter("collection_desc", updateProps.getDescription());
      }

      try {
        query.executeUpdate();
      } catch (Exception e) {
        e.printStackTrace();
      }
      
      var findQuery = manager
              .createNativeQuery(FIND_BY_ID_DETAILS, Tuple.class)
                  .setParameter("collection_id", id);

      try {
          var tuple = (Tuple) findQuery.getSingleResult();
          return of(Collection.from(tuple));
      } catch (NoResultException exception) {
          return empty();
      }
    }

    @Override
    public Optional<Collection> findByIdAndFetchComments(Long id) {
       var query = manager
                .createNativeQuery(FIND_BY_ID_AND_FETCH_COMMENTS, Tuple.class)
                    .setParameter("collection_id", id);
        try {
            var tuple = (Tuple) query.getSingleResult();
            return of(Collection.from(tuple));
        } catch (NoResultException exception) {
            return empty();
        }
    }

}
