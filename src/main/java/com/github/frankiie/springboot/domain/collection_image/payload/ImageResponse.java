package com.github.frankiie.springboot.domain.collection_image.payload;

import com.github.frankiie.springboot.domain.collection_image.entity.Image;
import com.github.frankiie.springboot.domain.management.model.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "ImageResponse", requiredProperties = {"id", "image_url"})
public class ImageResponse implements Entity {
    private Long id;
    private String image_url; 

    public ImageResponse(Image image) {
      this.id = image.getId(); 
      this.image_url = image.getUrl();
    }

    public Long getId() {
      return this.id;
    }

}
