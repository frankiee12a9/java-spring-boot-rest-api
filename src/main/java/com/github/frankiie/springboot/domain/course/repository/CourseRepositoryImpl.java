package com.github.frankiie.springboot.domain.course.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domain.course.entity.Course;
import com.github.frankiie.springboot.domain.course.repository.custom.NativeQueryCourseRepository;
import com.github.frankiie.springboot.domain.course.repository.springdata.SpringDataCourseRepository;
import com.github.frankiie.springboot.domain.pagination.model.Page;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CourseRepositoryImpl implements CourseRepository {

    @Autowired private final NativeQueryCourseRepository nativeQuery;
    @Autowired private final SpringDataCourseRepository springData;

    @Override
    public Page<Course> findAll(Pageable pageable) {
      var response = nativeQuery.findAll(pageable);
      return response;
    }

    @Override
    public Optional<Course> findById(Long id) {
      var response = nativeQuery.findById(id);
      return response;
    }

    @Override
    public Boolean existsById(Long id) {
      return true;
    }

    @Override
    public void deleteById(Long id) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public void deleteAll(Iterable<? extends Course> entities) {
      springData.deleteAll(entities);
    }

    @Override
    public void delete(Course course) {
      springData.delete(course);
    }

    @Override
    public Course save(Course course) {
      return springData.save(course);
    }

    @Override
    public Collection<Course> saveAll(Collection<Course> courses) {
      return springData.saveAll(courses);
    }

    @Override
    public Page<Course> findByKeyword(Pageable pageable, String keyword) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'findByKeyword'");
    }
  
}
