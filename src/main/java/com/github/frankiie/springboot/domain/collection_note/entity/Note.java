package com.github.frankiie.springboot.domain.collection_note.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Tuple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.frankiie.springboot.domain.collection.entity.Collection;
import com.github.frankiie.springboot.domain.collection_image.entity.Image;
import com.github.frankiie.springboot.domain.collection_note.payload.CreateNoteProps;
import com.github.frankiie.springboot.domain.collection_note.payload.UpdateNoteProps;
import com.github.frankiie.springboot.domain.management.entity.Auditable;

import lombok.Getter;
import lombok.Setter;

import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.of;

import java.math.BigInteger;

import static com.github.frankiie.springboot.utils.JSON.stringify;

@Setter
@Getter
// @EqualsAndHashCode(callSuper = true)
@Table(name = "note")
@Entity
public class Note extends Auditable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Note.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "note_title") private String title;
    @Column(name = "note_desc") private String description;
    @Column(name = "note_link") private String link;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Image> images = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "notes", fetch = FetchType.EAGER)
    private Set<Collection> collections = new HashSet<>();

    public Note() {
    }

    public Note(String title) {
      this.title = title;
    }

    public Note(String title, String description, String link) {
      this.title = title;
      this.description = description;
      this.link = link;
    }

    public Note(CreateNoteProps createProps) {
      this.title = createProps.getTitle();
      if (createProps.getDescription() != null) {
        this.description = createProps.getDescription();
      }
      if (createProps.getDescription() != null) {
        this.link = createProps.getLink();
      }
    }

    public List<Image> getImages() {
      if (this.images == null) {
        return null;
      } else {
        return new ArrayList<>(this.images);
      }
    }

    public void setImages(List<Image> images) {
      if (images == null) {
        this.images = null;
      } else {
        this.images = Collections.unmodifiableList(images);
      }
    }

    public void setId(BigInteger id) {
      this.id = ofNullable(id)
              .map(BigInteger::longValue)
              .orElse(null);
    }

    public Set<Collection> getCollections() {
      return this.collections;
    }

    public void merge(UpdateNoteProps updateProps) {
      this.title = updateProps.getTitle();
      this.description = updateProps.getDescription();
    }

    public Long getId() {
      return id;
    }

    public static Note from(Tuple tuple) {
      var note = new Note();
      note.setId(tuple.get("id", BigInteger.class));
      note.setTitle(tuple.get("note_title", String.class));
      if (tuple.get("note_desc", String.class) != null) {
        note.setDescription(tuple.get("note_desc", String.class));
      }
      if (tuple.get("note_link", String.class) != null) {
        note.setLink(tuple.get("note_link", String.class));
      }

      return note;
    }

    public static Note fromWithImages(Tuple tuple) {
      var note = new Note();
      note.setId(tuple.get("id", BigInteger.class));
      note.setTitle(tuple.get("note_title", String.class));

      if (tuple.get("note_desc", String.class) != null) {
        note.setDescription(tuple.get("note_desc", String.class));
      }
      if (tuple.get("note_link", String.class) != null) {
        note.setLink(tuple.get("note_link", String.class));
      }

      note.setImages(ofNullable(tuple.get("image_urls", String.class))
                .map(roles -> of(roles.split(","))
                        .map(Image::new).toList())
                .orElse(List.of()));

      LOGGER.info("note.from(): " + note);

      return note;
    }

    @Override
    public String toString() {
        return stringify(this);
    }

}
