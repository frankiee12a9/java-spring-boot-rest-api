package com.github.frankiie.springboot.domain.course.payload;

import com.github.frankiie.springboot.domain.management.model.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AttendCourseResponse", requiredProperties = {"status", "courseId"})
public class AttendOrCancelCourseResponse implements Entity {
    boolean success;
    Long courseId;
    Long attendantId;

    public AttendOrCancelCourseResponse(boolean status, Long courseId, Long attendantId) {
      this.success = status;
      this.courseId = courseId;
      this.attendantId = attendantId;
    }

    @Override
    public Long getId() {
      return courseId;
    }
}
