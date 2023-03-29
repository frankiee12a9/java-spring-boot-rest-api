package com.github.frankiie.springboot.domains.collection.entity;

import java.math.BigInteger;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Tuple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.frankiie.springboot.domains.collection.payload.CreateCollectionProps;
import com.github.frankiie.springboot.domains.collection.payload.UpdateCollectionProps;
import com.github.frankiie.springboot.domains.collection_image.entity.Image;
import com.github.frankiie.springboot.domains.collection_note.entity.Note;
import com.github.frankiie.springboot.domains.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domains.management.entity.Auditable;

import lombok.Getter;
import lombok.Setter;

import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.of;

import static com.github.frankiie.springboot.utils.JSON.*;

// note: The @EqualsAndHashCode(callSuper = true) annotation tells Lombok to include the fields of the superclass in the generated equals() 
// and hashCode() methods. If you’re experiencing a StackOverflowError when updating one of the child entities, it’s possible that there is a circular 
// reference between the parent and child entities that is causing an infinite recursion when calling the hashCode() method.

// @EqualsAndHashCode(callSuper = true)
@Setter
@Getter
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
 
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "collection")
    private Image image;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "collection_note", 
            joinColumns = @JoinColumn(name = "collection_id", referencedColumnName = "id"), 
            inverseJoinColumns = @JoinColumn(name = "note_id", referencedColumnName = "id"))
    private Set<Note> notes = new HashSet<>();

    public Collection() {
    }

    public Collection(String title) {
      this.title = title;
    }

    public Collection(String title, String description) {
      this.title = title; 
      this.description = description;
    }

    public Collection(String title, String description, Set<Note> notes) {
      this.title = title; 
      this.description = description;
      this.notes = notes;
    }

    public Collection(CreateCollectionProps props) {
      this.title = props.getTitle();
      this.description = props.getDescription();
    }

    public Set<Note> getNotes() {
      return this.notes;
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

