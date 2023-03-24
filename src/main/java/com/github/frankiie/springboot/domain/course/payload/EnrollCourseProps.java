package com.github.frankiie.springboot.domain.course.payload;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EnrollCourseProps {
  
  @NotNull private Long attendantId;

  @NotNull private Long courseId;
}
