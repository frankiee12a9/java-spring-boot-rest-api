package com.github.frankiie.springboot.domain.collection_note.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import com.github.frankiie.springboot.domain.course.entity.Course;
import com.github.frankiie.springboot.domain.management.entity.Auditable;
import com.github.frankiie.springboot.domain.user.entity.User;

import groovyjarjarantlr4.v4.parse.ANTLRParser.qid_return;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.of;

import java.math.BigInteger;

import static com.github.frankiie.springboot.utils.JSON.stringify;

@Data
@EqualsAndHashCode(callSuper = true)
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "collection_note", 
            joinColumns = @JoinColumn(name = "collection_id", referencedColumnName = "id"), 
            inverseJoinColumns = @JoinColumn(name = "note_id", referencedColumnName = "id"))
    private List<Collection> collections = new ArrayList<>();

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

    public List<Collection> getCollections() {
      if (this.collections == null) {
        return null;
      } else {
        return new ArrayList<>(this.collections);
      }
    }

    public void setCollections(List<Collection> collections) {
      if (collections == null) {
        this.collections = null;
      } else {
        this.collections = Collections.unmodifiableList(collections);
      }
    }

    public Long getId() {
      return id;
    }

    public static Note from(Tuple tuple) {
      var note = new Note();
      note.setId(tuple.get("id", BigInteger.class));
      note.setTitle(tuple.get("title", String.class));
      note.setDescription(tuple.get("desc", String.class));
      note.setLink(tuple.get("link", String.class));

      return note;
    }

    @Override
    public String toString() {
        return stringify(this);
    }
}
