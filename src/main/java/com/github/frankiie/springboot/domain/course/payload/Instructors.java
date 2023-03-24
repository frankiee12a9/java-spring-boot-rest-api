package com.github.frankiie.springboot.domain.course.payload;

import lombok.Data;

@Data
public class Instructors {
  String instructor;
  String ta1;
  String ta2;

  public Instructors(String instructor, String ta1) {
    this.instructor = instructor;
    this.ta1 = ta1;
  }

  public Instructors(String instructor, String ta1, String ta2) {
    this.instructor = instructor;
    this.ta1 = ta1;
    this.ta2 = ta2;
  }
}
