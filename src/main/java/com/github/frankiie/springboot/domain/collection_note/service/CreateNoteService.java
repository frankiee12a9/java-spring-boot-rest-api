package com.github.frankiie.springboot.domain.collection_note.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domain.collection.repository.CollectionRepository;
import com.github.frankiie.springboot.domain.collection_note.entity.Note;
import com.github.frankiie.springboot.domain.collection_note.payload.CreateNoteProps;
import com.github.frankiie.springboot.domain.collection_note.repository.NoteRepository;
import com.github.frankiie.springboot.utils.Responses;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CreateNoteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindNoteService.class);

    @Autowired private final CollectionRepository collectionRepository;
    @Autowired private final NoteRepository noteRepository;

    public Note create(CreateNoteProps createProps) {
      return save(createProps);
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
