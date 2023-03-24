package com.github.frankiie.springboot.domain.course.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domain.course.entity.Course;
import com.github.frankiie.springboot.domain.pagination.model.Page;

@Repository
public interface CourseRepository {
    Page<Course> findAll(Pageable pageable);
    Page<Course> findByKeyword(Pageable pageable, String keyword);
    Optional<Course> findById(Long id);
    Boolean existsById(Long id);
    void deleteById(Long id);
    void deleteAll(Iterable<? extends Course> entities);
    void delete(Course Course);
    Course save(Course Course);
    Collection<Course> saveAll(Collection<Course> Courses);

}
