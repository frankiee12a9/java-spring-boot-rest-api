package com.github.frankiie.springboot.domain.collection_note_comment.payload;

import javax.validation.constraints.NotEmpty;

import com.github.frankiie.springboot.domain.collection.entity.Collection;

import lombok.Data;

@Data
public class CreateCommentProps {
    @NotEmpty(message = "")
    private String message;
    
    Collection collection;
}
