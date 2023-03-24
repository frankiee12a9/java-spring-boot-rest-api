package com.github.frankiie.springboot.domain.course.repository.custom;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domain.course.entity.Course;
import com.github.frankiie.springboot.domain.pagination.model.Page;

import java.util.Optional;

@Repository
public interface NativeQueryCourseRepository {
    Optional<Course> findById(Long id);
    Page<Course> findByKeyword(Pageable pageable, String keyword);
    Page<Course> findAll(Pageable pageable);
}
