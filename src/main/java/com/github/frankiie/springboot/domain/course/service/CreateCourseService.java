package com.github.frankiie.springboot.domain.course.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domain.course.entity.Course;
import com.github.frankiie.springboot.domain.course.payload.CreateCourseProps;
import com.github.frankiie.springboot.domain.course.repository.CourseRepository;
import com.github.frankiie.springboot.domain.user.entity.User;
import com.github.frankiie.springboot.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import static com.github.frankiie.springboot.utils.Responses.notFound;

@RequiredArgsConstructor
@Service
public class CreateCourseService {
    @Autowired private final CourseRepository courseRepository;
    @Autowired private final UserRepository userRepository;
    
    public Course create(CreateCourseProps props) {
      return save(props);
    }

    private Course save(CreateCourseProps props) {
      var attendants = new HashSet<User>();

      var instructor = userRepository.findByIdFetchRoles(props.getInstructorId())
          .orElseThrow(() -> notFound("instruction not found"));

      attendants.add(instructor);
      
      var ta1 = userRepository.findByIdFetchRoles(props.getTa1Id());
      var ta2 = userRepository.findByIdFetchRoles(props.getTa2Id());
      if (ta1.isPresent()) {
        attendants.add(ta1.get());
      }

      if (ta2.isPresent()) {
        attendants.add(ta2.get());
      }

      var newCourse = new Course(props, attendants);
      courseRepository.save(newCourse);
      return newCourse;
    }
}
