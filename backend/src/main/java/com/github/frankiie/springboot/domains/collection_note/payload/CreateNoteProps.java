package com.github.frankiie.springboot.domains.collection_note.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.github.frankiie.springboot.utils.JSON;

import lombok.Data;

@Data
public class CreateNoteProps {
    @NotNull Long CollectionId;
    @NotEmpty private String title;
    private String description;
    private String link;

    public CreateNoteProps() {
    }
    
    @Override
    public String toString() {
      return JSON.stringify(this);
    }

}
