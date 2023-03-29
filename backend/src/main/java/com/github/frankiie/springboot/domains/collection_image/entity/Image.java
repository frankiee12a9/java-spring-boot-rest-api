package com.github.frankiie.springboot.domains.collection_image.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Tuple;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.frankiie.springboot.domains.collection.entity.Collection;
import com.github.frankiie.springboot.domains.collection_image.payload.CreateImageProps;
import com.github.frankiie.springboot.domains.collection_note.entity.Note;
import com.github.frankiie.springboot.domains.management.entity.Auditable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static java.util.Optional.ofNullable;
import static com.github.frankiie.springboot.utils.JSON.stringify;

import java.math.BigInteger;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@Entity
public class Image extends Auditable {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String url;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "note_id", nullable = true)
    private Note note;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "collection_id", unique = true)
    private Collection collection;

    public Image() {
    }

    public Image(String url) {
      this.url = url;
    }

    public Image(String url, Collection collection) {
      this.url = url;
      this.collection = collection;
    }

    public Image(String url, Note note) {
      this.url = url;
      this.note = note;
    }

    public void setId(BigInteger id) {
      this.id = ofNullable(id)
              .map(BigInteger::longValue)
              .orElse(null);
    }

    @Override
    public Long getId() {
      return id;
    }

    public static Image from(Tuple tuple) {
      var image = new Image();
      image.setId(tuple.get("id", BigInteger.class));
      image.setUrl(tuple.get("image_url", String.class));

      return image;
    }

    @Override
    public String toString() {
        return stringify(this);
    }

}



