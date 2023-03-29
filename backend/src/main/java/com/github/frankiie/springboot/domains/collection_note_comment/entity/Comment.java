package com.github.frankiie.springboot.domains.collection_note_comment.entity;

import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Tuple;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.frankiie.springboot.domains.collection.entity.Collection;
import com.github.frankiie.springboot.domains.collection_note_comment.payload.CreateCommentProps;
import com.github.frankiie.springboot.domains.management.entity.Auditable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static java.util.Optional.ofNullable;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@Table(name = "comment")
@Entity
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "message") private String message;

    @JsonIgnore
    @JoinColumn(name = "collection_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Collection collection;

    public Comment() {
    }

    public Comment(String message) {
      this.message = message; 
    }

    public Comment(CreateCommentProps props) {
      this.message = props.getMessage();
      this.collection = props.getCollection();
    }

    public void setId(BigInteger id) {
      this.id = ofNullable(id)
              .map(BigInteger::longValue)
              .orElse(null);
    }

    public Long getId() {
      return id;
    }

    public static Comment from(Tuple tuple) {
      var comment = new Comment();
      comment.setId(tuple.get("id", BigInteger.class));
      comment.setMessage(tuple.get("message", String.class));

      return comment;
    }
  
}
