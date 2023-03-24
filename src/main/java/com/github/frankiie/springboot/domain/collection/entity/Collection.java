package com.github.frankiie.springboot.domain.collection.entity;

import java.math.BigInteger;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Tuple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.frankiie.springboot.domain.collection.payload.CreateCollectionProps;
import com.github.frankiie.springboot.domain.collection.payload.UpdateCollectionProps;
import com.github.frankiie.springboot.domain.collection_image.entity.Image;
import com.github.frankiie.springboot.domain.collection_note.entity.Note;
import com.github.frankiie.springboot.domain.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domain.management.entity.Auditable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.of;

import static com.github.frankiie.springboot.utils.JSON.stringify;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "collection")
@Entity
public class Collection extends Auditable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Collection.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "collection_title")
    private String title;

    @Column(name = "collection_desc")
    private String description;
 
    // CascadeType.ALL will cause the `PersistentObjectException` when persist Image, then persist Collection
    // reference: https://www.baeldung.com/hibernate-detached-entity-passed-to-persist
    // @OneToOne(cascade = CascadeType.MERGE)
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "collection")
    private Image image;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "collection_note", 
            joinColumns = @JoinColumn(name = "collection_id", referencedColumnName = "id", nullable = true), 
            inverseJoinColumns = @JoinColumn(name = "note_id", referencedColumnName = "id", nullable = true))
    private List<Note> notes = new ArrayList<>();

    public Collection() {
    }

    public Collection(String title) {
      this.title = title;
    }

    public Collection(String title, String description) {
      this.title = title; 
      this.description = description;
    }

    public Collection(String title, String description, List<Note> notes) {
      this.title = title; 
      this.description = description;
      this.notes = notes;
    }

    public Collection(CreateCollectionProps props) {
      this.title = props.getTitle();
      this.description = props.getDescription();
    }

    public List<Note> getNotes() {
      if (this.notes == null) {
        return null;
      } else {
        return new ArrayList<>(this.notes);
      }
    }

    public void setNotes(List<Note> notes) {
      if (this.notes == null) {
        this.notes = new ArrayList<>();
      }
      this.notes.clear();
      if (this.notes != null) {
        this.notes.addAll(notes);
      }
    }

    public List<Comment> getComments() {
      if (this.comments == null) {
        return null;
      } else {
        return new ArrayList<>(this.comments);
      }
    }

    public void setComments(List<Comment> comments) {
      if (comments == null) {
        this.comments = null;
      } else {
        this.comments = Collections.unmodifiableList(comments);
      }
    }

    public Long getId() {
      return id;
    }

    public void setId(BigInteger id) {
      this.id = ofNullable(id)
              .map(BigInteger::longValue)
              .orElse(null);
    }
    
    public void merge(UpdateCollectionProps updateProps) {
      this.title = updateProps.getTitle();
      this.description = updateProps.getDescription();
    }

    public static Collection from(Tuple tuple) {
      var collection = new Collection();
      collection.setId(tuple.get("id", BigInteger.class));
      collection.setTitle(tuple.get("collection_title", String.class));
      collection.setDescription(tuple.get("collection_desc", String.class));
      Image image = new Image();
      image.setId(tuple.get("i_id", BigInteger.class));
      image.setUrl(tuple.get("image_url", String.class));
      collection.setImage(image);

      return collection;
    }

    @Override
    public String toString() {
        return stringify(this);
    }
}

