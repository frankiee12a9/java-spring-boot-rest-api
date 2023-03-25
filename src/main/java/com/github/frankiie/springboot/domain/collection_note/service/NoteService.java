package com.github.frankiie.springboot.domain.collection_note.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.xml.crypto.dsig.keyinfo.PGPData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domain.collection.repository.CollectionRepository;
import com.github.frankiie.springboot.domain.collection.service.CollectionService;
import com.github.frankiie.springboot.domain.collection_image.entity.Image;
import com.github.frankiie.springboot.domain.collection_image.payload.CreateImageProps;
import com.github.frankiie.springboot.domain.collection_image.repository.ImageRepository;
import com.github.frankiie.springboot.domain.collection_image.service.ImageService;
import com.github.frankiie.springboot.domain.collection_note.entity.Note;
import com.github.frankiie.springboot.domain.collection_note.payload.CreateNoteProps;
import com.github.frankiie.springboot.domain.collection_note.payload.UpdateNoteProps;
import com.github.frankiie.springboot.domain.collection_note.repository.NoteRepository;
import com.github.frankiie.springboot.domain.pagination.model.Page;
import com.github.frankiie.springboot.domain.pagination.service.Pagination;
import com.github.frankiie.springboot.utils.Responses;

import lombok.RequiredArgsConstructor;

import static com.github.frankiie.springboot.utils.Messages.*;


@RequiredArgsConstructor
@Service
public class NoteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteService.class);

    @Autowired private final NoteRepository noteRepository;
    @Autowired private final CollectionRepository collectionRepository;
    @Autowired private final ImageRepository imageRepository;
    
    @Autowired private final ImageService imageService;

    public Note create(CreateNoteProps createProps) {
      return save(createProps);
    }

    public Note findById(Long id) {
      return noteRepository.findById(id)
        .orElseThrow(() -> Responses.notFound("note not found"));
    }

    public Page<Image> findImagesById(Long id, Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return noteRepository.findImagesById(id, pageable);
    }

    public Page<Note> findByKeyword(String keyword, Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return noteRepository.findByKeyword(pageable, keyword);
    }

    public Page<Note> findByCollectionId(Long collectionId, Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return noteRepository.findByCollectionId(collectionId, pageable);
    }

    public Page<Note> findMany(Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return noteRepository.findMany(pageable);
    }

    public Page<Note> findManyWithFilter(String filter, Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return noteRepository.findManyWithFilter(filter, pageable); 
    }

    public Image updateByAddingImage(Long id, CreateImageProps imageProps) {
      var note = findById(id);

      var image = imageService.uploadImage(imageProps);

      note.getImages().add(image);

      image.setNote(note);
      noteRepository.save(note);
      return image;
      // return note;
    }

    public Note updateById(Long id, UpdateNoteProps updateProps) {
      var note = findById(id);

      note.merge(updateProps);

      noteRepository.save(note);
      return note;
    }

    private Note save(CreateNoteProps createProps) {
      var collection = collectionRepository.findById(createProps.getCollectionId())
          .orElseThrow(() -> Responses.notFound("associated collection not found"));
          
      var note = new Note(createProps);
      noteRepository.save(note);

      collection.getNotes().add(note);
      collectionRepository.save(collection);
      return note;
    }
}
