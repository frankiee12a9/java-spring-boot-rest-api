package com.github.frankiie.springboot.domain.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domain.course.repository.CourseRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service 
public class RemoveCourseService {
    @Autowired private final CourseRepository courseRepository; 

    public boolean delete(Long id) {
      var course = courseRepository.findById(id);
      if (!course.isPresent()) {
        return false;
      } 

      courseRepository.delete(course.get());
      return true;
    }
}
