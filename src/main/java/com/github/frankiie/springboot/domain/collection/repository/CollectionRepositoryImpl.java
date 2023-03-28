package com.github.frankiie.springboot.domain.collection.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domain.collection.entity.Collection;
import com.github.frankiie.springboot.domain.collection.payload.UpdateCollectionProps;
import com.github.frankiie.springboot.domain.collection.repository.custom.NativeQueryCollectionRepository;
import com.github.frankiie.springboot.domain.collection.repository.springdata.SpringDataCollectionRepository;
import com.github.frankiie.springboot.domain.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domain.pagination.model.Page;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CollectionRepositoryImpl implements CollectionRepository {

    @Autowired private final NativeQueryCollectionRepository nativeQuery;
    @Autowired private final SpringDataCollectionRepository springData;

    @Override
    public Page<Collection> findMany(Pageable pageable) {
      return nativeQuery.findMany(pageable);
    }

    @Override
    public Page<Comment> findCommentsById(Long id, Pageable pageable) {
      return nativeQuery.findCommentsById(id, pageable);
    }

    @Override
    public Page<Collection> findByKeyword(Pageable pageable, String keyword) {
      return nativeQuery.findByKeyword(pageable, keyword);
    }

    @Override
    public Optional<Collection> findById(Long id) {
      // note: this caused a bug while updating associated entity (Collection_Note)
      // return nativeQuery.findById(id); 
      return springData.findById(id);
    }

    @Override
    public Boolean existsById(Long id) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }

    @Override
    public void deleteById(Long id) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public void deleteAll(Iterable<? extends Collection> entities) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }

    @Override
    public void delete(Collection collection) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Collection save(Collection collection) {
      return springData.save(collection);
    }

    @Override
    public Optional<Collection> findByIdAndFetchComments(Long id) {
      // note: if use nativeQuery.findById() in this case, 
      // it will cause problems when updating the associated `Notes` in the join table `collection_note`
      return nativeQuery.findByIdAndFetchComments(id);
    }

    @Override
    public Optional<Collection> updateById(Long id, UpdateCollectionProps updateProps) {
        return nativeQuery.updateById(id, updateProps);
    }

}
