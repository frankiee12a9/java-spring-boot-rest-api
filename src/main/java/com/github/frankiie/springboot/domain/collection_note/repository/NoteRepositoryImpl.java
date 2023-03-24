package com.github.frankiie.springboot.domain.collection_note.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domain.collection.payload.UpdateCollectionProps;
import com.github.frankiie.springboot.domain.collection_note.entity.Note;
import com.github.frankiie.springboot.domain.collection_note.repository.custom.NativeQueryNoteRepository;
import com.github.frankiie.springboot.domain.collection_note.repository.springdata.SpringDataNoteRepository;
import com.github.frankiie.springboot.domain.pagination.model.Page;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class NoteRepositoryImpl implements NoteRepository {

    @Autowired private final NativeQueryNoteRepository nativeQuery;
    @Autowired private final SpringDataNoteRepository springData;


    @Override
    public Page<Note> findMany(Pageable pageable) {
      return nativeQuery.findMany(pageable);
    }

    @Override
    public Page<Note> findByKeyword(Pageable pageable, String keyword) {
      return nativeQuery.findByKeyword(pageable, keyword);
    }

    @Override
    public Optional<Note> findById(Long id) {
      return nativeQuery.findById(id);
    }

    @Override
    public Optional<Note> updateById(Long id, UpdateCollectionProps updateProps) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'updateById'");
    }

    @Override
    public void deleteById(Long id) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public void deleteAll(Iterable<? extends Note> entities) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }

    @Override
    public void delete(Note Note) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Note save(Note note) {
      return springData.save(note);
    }
  
}
