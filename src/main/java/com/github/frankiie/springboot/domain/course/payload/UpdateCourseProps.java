package com.github.frankiie.springboot.domain.course.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateCourseProps {
  private String title;
  private String description;
  private String instructor;
  private String TA1;
  private String TA2;
}
