package com.github.frankiie.springboot.domain.collection_note.payload;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateNoteProps {
    @NotEmpty
    private String title;
    private String description;
    private String link;

    public CreateNoteProps() {
    }
    
}
