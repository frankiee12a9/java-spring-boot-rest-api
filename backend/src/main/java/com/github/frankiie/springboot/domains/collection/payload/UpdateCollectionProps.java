package com.github.frankiie.springboot.domains.collection.payload;

import javax.validation.constraints.NotEmpty;

import com.github.frankiie.springboot.domains.collection.entity.Collection;
import com.github.frankiie.springboot.utils.JSON;

import lombok.Data;

@Data
public class UpdateCollectionProps  {
    @NotEmpty private String title;
    private String description;

    public UpdateCollectionProps() {
    }

    public UpdateCollectionProps(Collection collection) {
      this.title = collection.getTitle();
      this.description = collection.getDescription();
    }

    @Override
    public String toString() {
      return JSON.stringify(this);
    }

}
