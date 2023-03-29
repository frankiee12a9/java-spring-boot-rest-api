package com.github.frankiie.springboot.domains.collection_note.payload;

import com.github.frankiie.springboot.domains.collection_image.entity.Image;
import com.github.frankiie.springboot.domains.collection_note.entity.Note;
import com.github.frankiie.springboot.domains.management.model.Entity;
import com.github.frankiie.springboot.utils.JSON;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import static com.github.frankiie.springboot.utils.JSON.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Schema(name = "NoteResponse", requiredProperties = {"id", "title", "description", "image"})
public class NoteResponse implements Entity {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteResponse.class);

    private Long id;
    private String title;
    private String description;
    private String link;
    private List<String> images;

    public NoteResponse(Note note) {
      this.id = note.getId();
      this.title = note.getTitle();
      this.description = note.getDescription();
      this.link = note.getLink();
      this.images = note.getImages().stream().map(Image::getUrl).toList();
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
