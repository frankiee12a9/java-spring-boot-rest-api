package com.github.frankiie.springboot.domain.collection.payload;

import javax.validation.constraints.NotEmpty;

import com.github.frankiie.springboot.domain.collection.entity.Collection;

import lombok.Data;

@Data
public class UpdateCollectionProps  {
    @NotEmpty
    private String title;
    private String description;

    public UpdateCollectionProps() {
    }

    public UpdateCollectionProps(Collection collection) {
      this.title = collection.getTitle();
      this.description = collection.getDescription();
    }
}
