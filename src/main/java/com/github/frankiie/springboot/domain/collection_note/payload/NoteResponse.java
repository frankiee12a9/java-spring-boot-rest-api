package com.github.frankiie.springboot.domain.collection_note.payload;

import com.github.frankiie.springboot.domain.collection_note.entity.Note;
import com.github.frankiie.springboot.domain.management.model.Entity;
import com.github.frankiie.springboot.utils.JSON;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import static com.github.frankiie.springboot.utils.JSON.*;

@Getter
@Schema(name = "NoteResponse", requiredProperties = {"id", "title", "description", "image"})
public class NoteResponse implements Entity {
    Long id;
    String title;
    String description;
    String link;
    // ImageResponse image;
    // List<ImageResponse> images = new ArrayList<>();

    public NoteResponse(Note note) {
      this.id = note.getId();
      this.title = note.getTitle();
      this.description = note.getDescription();
      this.link = note.getLink();
      // if (note.getImages().size() > 0) {
      //   images.addAll(note.getImages().stream().map(ImageResponse::new).toList());
      //   note.getImages().stream().map(ImageResponse::new);
      // }
    }

    @Override
    public Long getId() {
      return id;
    }
  
    @Override
    public String toString() {
      return JSON.stringify(this);
    }

}
