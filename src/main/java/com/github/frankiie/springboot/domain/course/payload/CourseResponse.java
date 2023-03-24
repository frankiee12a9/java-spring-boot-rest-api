package com.github.frankiie.springboot.domain.course.payload;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.frankiie.springboot.domain.course.entity.Course;
import com.github.frankiie.springboot.domain.management.model.Entity;
import com.github.frankiie.springboot.domain.user.payload.UserResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CourseResponse", requiredProperties = {"", "", "", ""})
public class CourseResponse implements Entity {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseResponse.class);

    Long id;
    String title;
    String description;
    int attendantCount;
    boolean active;
    List<UserResponse> instructors;

    public CourseResponse(Course course) {
      LOGGER.info("[Course]: " + course.toString());

      this.id = course.getId();
      this.title = course.getTitle();
      this.description = course.getDescription();
      if (course.getCreatedAt() != null) {
        this.active = ChronoUnit.DAYS.between(course.getCreatedAt(), LocalDateTime.now()) > 30 ? false : true; // check whether CreatedAt is exceed 20 days
      }

      // if (course.getAttendants() != null) {
      //   this.instructors = course.getAttendants().stream()
      //   .filter(u -> u.getRoles().stream().anyMatch(r -> r.getName().equals("TA") || r.getName().equals("INSTRUCTOR")))
      //   .map(u -> new UserResponse(u.getId(), u.getName(), u.getEmail()))
      //   .collect(Collectors.toUnmodifiableList()); 
      //   this.attendantCount = this.instructors.size();
      // } else {
      //   this.instructors = null;
      //   this.attendantCount = 0;
      // }
    }

    public CourseResponse(String title, String description, int attendantCount, List<UserResponse> instructors) {
      this.title = title;
      this.description = description;
      this.attendantCount = attendantCount;
      this.instructors = instructors;
    }

    @Override
    public Long getId() {
      return id;
    }
}
