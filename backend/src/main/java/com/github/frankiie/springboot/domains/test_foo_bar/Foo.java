package com.github.frankiie.springboot.domains.test_foo_bar;

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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.of;

import static com.github.frankiie.springboot.utils.JSON.stringify;

// @Data
@Setter
@Getter
// @EqualsAndHashCode(callSuper = true)
@Table(name = "foo")
@Entity
public class Foo extends Auditable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Foo.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "bar_name")
    private String name;

    @ManyToMany(mappedBy = "foos", fetch = FetchType.EAGER)
    private Set<Bar> bars = new HashSet<>();

    public Foo() {
    }

    public Foo(String name) {
      this.name = name;
    }

    public Foo(CreateFoo props) {
      this.name = props.getName();
    }

    public Set<Bar> getBars() {
      return this.bars;
    }

    public Long getId() {
      return id;
    }

    @Override
    public String toString() {
        return stringify(this);
    }
}

