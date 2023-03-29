package com.github.frankiie.springboot.domains.collection_note.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domains.collection_image.entity.Image;
import com.github.frankiie.springboot.domains.collection_image.payload.CreateImageProps;
import com.github.frankiie.springboot.domains.collection_image.service.ImageService;
import com.github.frankiie.springboot.domains.collection_note.entity.Note;
import com.github.frankiie.springboot.domains.collection_note.payload.UpdateNoteProps;
import com.github.frankiie.springboot.domains.collection_note.repository.NoteRepository;
import com.github.frankiie.springboot.utils.Responses;

import lombok.RequiredArgsConstructor;

import static com.github.frankiie.springboot.utils.Responses.*;

@RequiredArgsConstructor
@Service
public class UpdateNoteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateNoteService.class);

    @Autowired private final ImageService imageService;

    @Autowired private final NoteRepository noteRepository;

    public Image updateByAddingImage(Long id, CreateImageProps imageProps) {
      var note = noteRepository.findById(id).orElseThrow(() -> Responses.notFound("note not found"));
      var image = imageService.uploadImage(imageProps);
      image.setNote(note);
      imageService.save(image);

      note.getImages().add(image);
      noteRepository.save(note);

      return image;
    }

    public Note updateById(Long id, UpdateNoteProps updateProps) {
      var note = noteRepository.findById(id).orElseThrow(() -> Responses.notFound("note not found"));
      note.merge(updateProps);
      noteRepository.save(note);
      return note;
    }

}
