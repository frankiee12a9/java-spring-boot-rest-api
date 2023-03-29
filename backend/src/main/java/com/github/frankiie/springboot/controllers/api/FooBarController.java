package com.github.frankiie.springboot.controllers.api;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.frankiie.springboot.domains.test_foo_bar.Bar;
import com.github.frankiie.springboot.domains.test_foo_bar.CreateBar;
import com.github.frankiie.springboot.domains.test_foo_bar.CreateFoo;
import com.github.frankiie.springboot.domains.test_foo_bar.Foo;
import com.github.frankiie.springboot.domains.test_foo_bar.FooBarService;

import static com.github.frankiie.springboot.utils.Responses.*;
import static org.springframework.http.HttpStatus.*;
import static com.github.frankiie.springboot.constants.VALUES.*;

@RequiredArgsConstructor
@RestController
@Tag(name = "Foo_Bar management")
@RequestMapping("/api/foobar")
public class FooBarController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotesController.class);

    @Autowired private final FooBarService service;

    @GetMapping("/foo/{id}")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "")
    public ResponseEntity<Foo> getFoo(@PathVariable Long id) {
      var response = service.getFooById(id);
      return ok(response);
    }

    @GetMapping("/bar/{id}")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "")
    public ResponseEntity<Bar> getBar(@PathVariable Long id) {
      var response = service.getBarById(id);
      return ok(response);
    }

    @PostMapping("/bar")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(
        summary = "",
        description = ""
    )
    public ResponseEntity<Bar> saveFoo(@Validated @RequestBody CreateBar props) {
      var response = service.saveBar(props);
      return created(response, "api/notes");
    }

    @PostMapping("/foo")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(
        summary = "",
        description = ""
    )
    public ResponseEntity<Foo> saveBar(@Validated @RequestBody CreateFoo props) {
      var response = service.saveFoo(props);
      return created(response, "api/notes");
    }

}
