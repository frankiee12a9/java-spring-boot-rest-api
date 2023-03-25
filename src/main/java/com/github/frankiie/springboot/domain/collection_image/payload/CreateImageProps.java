package com.github.frankiie.springboot.domain.collection_image.payload;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.github.frankiie.springboot.domain.collection.entity.Collection;

import lombok.Data;

@Data
public class CreateImageProps {
    @NotNull MultipartFile file;
    Collection collection;
    
    public void setCollection(Collection collection) {
      this.collection = collection;
    }
}
