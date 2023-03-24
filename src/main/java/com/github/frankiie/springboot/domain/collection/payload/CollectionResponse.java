package com.github.frankiie.springboot.domain.collection.payload;

import java.util.ArrayList;
import java.util.List;

import com.github.frankiie.springboot.domain.collection.entity.Collection;
import com.github.frankiie.springboot.domain.collection_image.payload.ImageResponse;
import com.github.frankiie.springboot.domain.management.model.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "CollectionResponse", requiredProperties = {"id", "title", "description", "image"})
public class CollectionResponse implements Entity {

    Long id;
    String title;
    String description; 
    ImageResponse image;

    public CollectionResponse(Collection collection) {
      this.id = collection.getId();
      this.title = collection.getTitle();
      this.description = collection.getDescription();
      if (collection.getImage() != null) {
        image = new ImageResponse(collection.getImage());
      }
    }

    @Override
    public Long getId() {
      return this.id;
    }
  
}
