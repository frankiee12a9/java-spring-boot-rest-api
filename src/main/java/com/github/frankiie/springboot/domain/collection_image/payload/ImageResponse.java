package com.github.frankiie.springboot.domain.collection_image.payload;

import com.github.frankiie.springboot.domain.collection_image.entity.Image;
import com.github.frankiie.springboot.domain.management.model.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "CommentResponse", requiredProperties = {"id", "image_url"})
public class ImageResponse implements Entity {

    Long id;
    private String url; 

    public ImageResponse(Image image) {
      this.id = image.getId(); 
      this.url = image.getUrl();
    }

    public Long getId() {
      return this.id;
    }

}
