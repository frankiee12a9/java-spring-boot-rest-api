package com.github.frankiie.springboot.domain.collection_note.payload;

import com.github.frankiie.springboot.utils.JSON;

import lombok.Data;

@Data
public class UpdateNoteProps {
    private String title;
    private String description;
    private String link;
    
    public UpdateNoteProps() {
    }

    @Override
    public String toString() {
      return JSON.stringify(this);
    }

}
