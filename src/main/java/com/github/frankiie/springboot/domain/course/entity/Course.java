package com.github.frankiie.springboot.domain.course.entity;

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
import javax.persistence.Tuple;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.frankiie.springboot.domain.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domain.course.payload.CreateCourseProps;
import com.github.frankiie.springboot.domain.management.entity.Auditable;
import com.github.frankiie.springboot.domain.user.entity.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.of;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Course extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "course_title")
    private String title;

    @Column(name = "course_description")
    private String description;

    // @JsonIgnore
    // @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Comment> notices = new ArrayList<>();

    // @JsonIgnore
    // @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<TaskSubmission> taskSubmissions = new ArrayList<>();

    // @ManyToMany(fetch = FetchType.EAGER)
    // @JoinTable(name = "course_attendant", 
    //         joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"), 
    //         inverseJoinColumns = @JoinColumn(name = "attendant_id", referencedColumnName = "id"))
    // private Set<User> attendants;

    public Course() {
    }

    public Course(String title, String description) {
      this.title = title;
      this.description = description;
    }

    public Course(CreateCourseProps props, Set<User> attendants) {
      this.title = props.getTitle();
      this.description = props.getDescription();
      // this.attendants = attendants;
    }

    public Long getId() {
      return id;
    }

   public void setId(BigInteger id) {
        this.id = ofNullable(id)
                .map(BigInteger::longValue)
                .orElse(null);
    }

    // public List<Comment> getNotices() {
    //   if (this.notices == null) {
    //     return null;
    //   } else {
    //     return new ArrayList<>(this.notices);
    //   }
    // }

    // public void setNotices() {
    //   if (this.notices == null) {
    //     this.notices = null;
    //   } else {
    //     this.notices = Collections.unmodifiableList(this.notices);
    //   }
    // }

    // public Set<User> getAttendants() {
    //   if (this.attendants == null) {
    //     return null;
    //   } else {
    //     return new HashSet<>(this.attendants);
    //   }
    // }

    // public void getAttendants(Set<User> attendants) {
    //   if (this.attendants == null) {
    //     this.attendants = attendants;
    //   } else {
    //     this.attendants.retainAll(attendants);
    //     this.attendants.addAll(attendants);
    //   }
    // } 

    // public static Course from(Tuple tuple) {
    //   Course course = new Course();
    //   course.setId(tuple.get("id", BigInteger.class));
    //   course.setTitle(tuple.get("course_title", String.class));
    //   course.setDescription(tuple.get("course_description", String.class));

      // course.setAttendants(ofNullable(tuple.get( "instructors", String.class))
      //           .map(notices -> of(notices.split(","))
      //                   .map(User::new).toList())
      //           .orElse(Set.of()));

    //   course.setNotices(ofNullable(tuple.get( "notice_title", String.class))
    //             .map(notices -> of(notices.split(","))
    //                     .map(Comment::new).toList())
    //             .orElse(List.of()));

    //   return course;
    // }

    @Override
    public String toString() {
      return "Course{" +
              "id=" + id +
              ", title='" + title + '\'' +
              ", description='" + description + '\'' +
              // ", notices=" + notices +
              // ", attendants=" + attendants +
              ", createdAt=" +  getCreatedAt() +
              ", updateAt=" + getUpdatedAt() +
              '}';
    }
  
}
