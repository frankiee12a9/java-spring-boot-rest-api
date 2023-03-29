package com.github.frankiie.springboot.domains.collection_note_comment.payload;

import javax.validation.constraints.NotEmpty;

import com.github.frankiie.springboot.domains.collection.entity.Collection;

import lombok.Data;

@Data
public class CreateCommentProps {
    @NotEmpty(message = "") private String message;
    Collection collection;
}
