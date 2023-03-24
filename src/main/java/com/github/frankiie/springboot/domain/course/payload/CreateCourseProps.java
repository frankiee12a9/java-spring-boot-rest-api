package com.github.frankiie.springboot.domain.course.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateCourseProps {
    @NotEmpty(message = "")
    private String title;

    @NotEmpty(message = "")
    private String description; 

    @NotNull(message = "")
    private Long instructorId;

    private Long Ta1Id;

    private Long Ta2Id;
}
