package com.github.frankiie.springboot.domain.course.repository.custom;

import static com.github.frankiie.springboot.domain.course.repository.custom.CourseQueries.FIND_BY_ID;
import static com.github.frankiie.springboot.domain.course.repository.custom.CourseQueries.FIND_BY_ID_DETAILS;
import static com.github.frankiie.springboot.domain.course.repository.custom.CourseQueries.FIND_BY_KEYWORD;
import static com.github.frankiie.springboot.domain.course.repository.custom.CourseQueries.FIND_ALL;
import static com.github.frankiie.springboot.domain.course.repository.custom.CourseQueries.COUNT_ACTIVE_COURSES;
import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.List;
import java.util.Optional;
import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Tuple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domain.course.entity.Course;
import com.github.frankiie.springboot.domain.pagination.model.Page;

@Repository
public class NativeQueryCourseRepositoryImpl implements NativeQueryCourseRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(NativeQueryCourseRepositoryImpl.class);

    @Autowired EntityManager manager;

    @Override
    public Optional<Course> findById(Long id) {
      return null;
        // var query = manager
        //         // .createNativeQuery(format(FIND_BY_ID, "c.id = :course_id"), Tuple.class)  // placeholder cause unknown error
        //         .createNativeQuery(FIND_BY_ID_DETAILS, Tuple.class)
        //             .setParameter("course_id", id);
        // try {
        //     var tuple = (Tuple) query.getSingleResult();
        //     return of(Course.from(tuple));
        // } catch (NoResultException exception) {
        //     return empty();
        // }
    }
    
    @Override
    public Page<Course> findByKeyword(Pageable pageable, String keyword) {
      return null;
      // var query = manager
      //           .createNativeQuery(format(FIND_BY_KEYWORD, "c.keyword = :keyword"), Tuple.class)
      //               .setParameter("keyword", keyword);

      // var pageNumber = pageable.getPageNumber();
      // var pageSize = pageable.getPageSize();

      // query.setFirstResult(pageNumber * pageSize);
      // query.setMaxResults(pageSize);

      // List<Tuple> content = query.getResultList();

      // var courses = content.stream().map(Course::from).toList();

      // return Page.of(courses, pageNumber, pageSize, 0L);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Course> findAll(Pageable pageable) {
      return null;
      // var query = manager
      //         .createNativeQuery(FIND_ALL, Tuple.class);
      
      // var count = ((BigInteger) manager
      //     .createNativeQuery(COUNT_ACTIVE_COURSES)
      //     .getSingleResult())
      //       .longValue();

      // var pageNumber = pageable.getPageNumber();
      // var pageSize = pageable.getPageSize();

      // query.setFirstResult(pageNumber * pageSize);
      // query.setMaxResults(pageSize);

      // List<Tuple> content = query.getResultList();

      // var courses = content.stream().map(Course::from).toList();

      // return Page.of(courses, pageNumber, pageSize, count);
    }

}
