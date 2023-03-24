package com.github.frankiie.springboot.domain.course.payload;

import java.util.List;
import java.util.Set;

import com.github.frankiie.springboot.domain.course.entity.Course;
import com.github.frankiie.springboot.domain.management.model.Entity;
import com.github.frankiie.springboot.domain.user.entity.User;

import lombok.Data;

@Data
public class CreateCourseResponse implements Entity {
    private Long courseId;
    private String title;
    private String description;
    // User instructor;
    // int taCount;
    Set<User> instructors;

    public CreateCourseResponse(Course course) {
      this.courseId = course.getId();
      this.title = course.getTitle();
      this.description = course.getDescription();
      // this.instructors = course.getAttendants();
      // this.instructor = course.getAttendants()
      //     .stream()
      //     .filter(a -> a.getRoles().stream().anyMatch(r -> r.getName().equals("INSTRUCTOR")))
      //     .findFirst().get();
      // this.taCount = course.getAttendants()
      //     .stream()
      //     .filter(a -> a.getRoles().stream().anyMatch(r -> r.getName().equals("TA")))
      //     .toList()
      //     .size();
    }

    @Override
    public Long getId() {
      return courseId; 
    }
}
