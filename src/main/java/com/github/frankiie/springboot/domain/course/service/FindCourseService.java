package com.github.frankiie.springboot.domain.course.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domain.course.entity.Course;
import com.github.frankiie.springboot.domain.course.repository.CourseRepository;
import com.github.frankiie.springboot.domain.pagination.model.Page;
import com.github.frankiie.springboot.domain.pagination.service.Pagination;

import lombok.RequiredArgsConstructor;

import static com.github.frankiie.springboot.utils.Responses.notFound;

@RequiredArgsConstructor
@Service 
public class FindCourseService {
    @Autowired private final CourseRepository courseRepository;

    public Course findById(Long id) {
      return courseRepository.findById(id)
        .orElseThrow(() -> notFound("Course not found"));
    }

    public Course findByKeyword(String keyword) {
      return null;
    }

    public Page<Course> findMany(Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return courseRepository.findAll(pageable);
    }
  
}
