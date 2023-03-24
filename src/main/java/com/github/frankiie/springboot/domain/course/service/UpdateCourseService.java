package com.github.frankiie.springboot.domain.course.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domain.course.entity.Course;
import com.github.frankiie.springboot.domain.course.payload.CancelCourseProps;
import com.github.frankiie.springboot.domain.course.payload.EnrollCourseProps;
import com.github.frankiie.springboot.domain.course.payload.UpdateCourseProps;
import com.github.frankiie.springboot.domain.course.repository.CourseRepository;
import com.github.frankiie.springboot.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import static com.github.frankiie.springboot.utils.Responses.notFound;

@RequiredArgsConstructor
@Service 
public class UpdateCourseService {
    @Autowired private final CourseRepository courseRepository;
    @Autowired private final UserRepository userRepository;

    public Course update(Long id, UpdateCourseProps props) {
      var course = courseRepository.findById(id).orElse(null);
      if (course == null) {
        return null;
      }
      
      if (props.getTitle() != null) course.setTitle(props.getTitle());
      if (props.getDescription() != null) course.setDescription(props.getDescription());

      return courseRepository.save(course);
    }

    public Course attend(Long id, EnrollCourseProps props) {
      var attendant = userRepository.findById(props.getAttendantId()).orElseThrow(() -> notFound("attendant not found"));
      var course = courseRepository.findById(id).orElseThrow(() -> notFound("course not found"));

      // course.getAttendants().add(attendant);
      // course.setAttendants(course.getAttendants());
      // courseRepository.save(course);
      return course ;
    }

    public Course cancel(Long id, CancelCourseProps props) {
      var attendant = userRepository.findById(props.getAttendantId()).orElseThrow(() -> notFound("attendant not found"));
      var course = courseRepository.findById(id).orElseThrow(() -> notFound("course not found"));

      // course.getAttendants().remove(attendant);
      return courseRepository.save(course);
    }
}
