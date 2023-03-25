package com.github.frankiie.springboot.domain.collection_note_comment.payload;

import static java.util.Optional.ofNullable;

import com.github.frankiie.springboot.domain.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domain.management.model.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "CommentResponse", requiredProperties = {"id", "message"})
public class CommentResponse implements Entity {
    private Long id;
    private String message;

    public CommentResponse(Comment comment) {
      this.id = comment.getId();
      this.message = comment.getMessage();
    }

    @Override
    public Long getId() {
      return this.id;
    }
    
}
