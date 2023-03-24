package com.github.frankiie.springboot.controllers.api;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.frankiie.springboot.domain.course.payload.EnrollCourseProps;
import com.github.frankiie.springboot.domain.course.payload.CourseResponse;
import com.github.frankiie.springboot.domain.course.entity.Course;
import com.github.frankiie.springboot.domain.course.payload.CancelCourseProps;
import com.github.frankiie.springboot.domain.course.payload.AttendOrCancelCourseResponse;
import com.github.frankiie.springboot.domain.course.payload.CreateCourseProps;
import com.github.frankiie.springboot.domain.course.payload.CreateCourseResponse;
import com.github.frankiie.springboot.domain.course.payload.UpdateCourseProps;
import com.github.frankiie.springboot.domain.course.service.CreateCourseService;
import com.github.frankiie.springboot.domain.course.service.FindCourseService;
import com.github.frankiie.springboot.domain.course.service.UpdateCourseService;
import com.github.frankiie.springboot.domain.pagination.model.Page;
import com.github.frankiie.springboot.domain.course.service.RemoveCourseService;
import com.github.frankiie.springboot.domain.recovery.model.RecoveryConfirm;
import com.github.frankiie.springboot.domain.recovery.model.RecoveryRequest;
import com.github.frankiie.springboot.domain.recovery.model.RecoveryUpdate;
import com.github.frankiie.springboot.domain.recovery.service.RecoveryConfirmService;
import com.github.frankiie.springboot.domain.recovery.service.RecoveryService;
import com.github.frankiie.springboot.domain.recovery.service.RecoveryUpdateService;
import com.github.frankiie.springboot.domain.user.form.UpdateUserProps;
import com.github.frankiie.springboot.domain.user.payload.UserResponse;

import static com.github.frankiie.springboot.utils.Responses.created;
import static com.github.frankiie.springboot.utils.Responses.ok;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequiredArgsConstructor
@RestController
@Tag(name = "Courses management")
@RequestMapping("/api/courses")
public class CoursesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoursesController.class);

    @Autowired private final CreateCourseService createCourseService;
    @Autowired private final FindCourseService findCourseService;
    @Autowired private final RemoveCourseService removeCourseService;
    @Autowired private final UpdateCourseService updateCourseService;


    @GetMapping
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Returns a list of courses with paging")
    public ResponseEntity<Page<CourseResponse>> getMany(Optional<Integer> page, Optional<Integer> size) {
        var content = findCourseService.findMany(page, size);
        return ok(content.map(CourseResponse::new));
    }

    @PostMapping
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(
        summary = "",
        description = ""
    )
    public ResponseEntity<CreateCourseResponse> save(@Validated @RequestBody CreateCourseProps props) {
      var course = createCourseService.create(props);
      LOGGER.info(course.toString());

      var response = new CreateCourseResponse(course);
      return created(response, "api/courses");
    }
  
    @GetMapping("/{id}")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR', 'TA', 'USER')")
    @Operation(summary = "")
    public ResponseEntity<Course> getOne(@PathVariable Long id) {
      var response = findCourseService.findById(id);
      // return ok(new CourseResponse(response));
      return ok(response);
    }

    @PutMapping("/{id}/attend")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR', 'TA', 'USER')")
    @Operation(summary = "")
    public ResponseEntity<AttendOrCancelCourseResponse> attend(@PathVariable Long id, @RequestBody @Validated EnrollCourseProps props) {
      var course = updateCourseService.attend(id, props);
      var response = new AttendOrCancelCourseResponse(true, course.getId(), props.getAttendantId());
      return created(response, "api/courses/attend");
    }

    @PutMapping("/{id}/cancel")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR', 'TA', 'STUDENT')")
    @Operation(summary = "")
    public ResponseEntity<AttendOrCancelCourseResponse> cancel(@PathVariable Long id, @RequestBody @Validated CancelCourseProps props) {
      var course = updateCourseService.cancel(id, props);
      var response = new AttendOrCancelCourseResponse(true, course.getId(), props.getAttendantId());
      return created(response, "api/courses/cancel");
    }

}
