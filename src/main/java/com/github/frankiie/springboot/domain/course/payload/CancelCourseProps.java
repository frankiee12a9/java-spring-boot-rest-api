package com.github.frankiie.springboot.domain.course.payload;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CancelCourseProps {
  @NotEmpty(message = "") private Long attendantId;
  @NotEmpty(message = "") private Long courseId;
}
