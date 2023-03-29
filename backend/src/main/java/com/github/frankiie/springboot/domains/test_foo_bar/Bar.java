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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.frankiie.springboot.domains.management.entity.Auditable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.of;

import static com.github.frankiie.springboot.utils.JSON.stringify;

@Setter
@Getter
// @EqualsAndHashCode(callSuper = true)
@Table(name = "bar")
@Entity
public class Bar extends Auditable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Foo.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "bar_name")
    private String name;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "foo_bar", 
            joinColumns = @JoinColumn(name = "bar_id", referencedColumnName = "id"), 
            inverseJoinColumns = @JoinColumn(name = "foo_id", referencedColumnName = "id"))
    private Set<Foo> foos = new HashSet<>();

    public Set<Foo> getBars() {
      return this.foos;
    }

    public Bar() {
    }

    public Bar(String name) {
      this.name = name;
    }

    public Bar(CreateBar props) {
      this.name = props.getName();
    }

    @Override
    public String toString() {
        return stringify(this);
    }

}

