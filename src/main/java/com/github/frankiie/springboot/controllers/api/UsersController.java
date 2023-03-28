package com.github.frankiie.springboot.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.github.frankiie.springboot.domain.pagination.model.Page;
import com.github.frankiie.springboot.domain.user.form.CreateUserProps;
import com.github.frankiie.springboot.domain.user.form.UpdateUserProps;
import com.github.frankiie.springboot.domain.user.payload.UserResponse;
import com.github.frankiie.springboot.domain.user.service.CreateUserService;
import com.github.frankiie.springboot.domain.user.service.FindUserService;
import com.github.frankiie.springboot.domain.user.service.RemoveUserService;
import com.github.frankiie.springboot.domain.user.service.UpdateUserService;

import java.util.Optional;

import static com.github.frankiie.springboot.utils.Responses.created;
import static com.github.frankiie.springboot.utils.Responses.ok;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static com.github.frankiie.springboot.constants.VALUES.DEFAULT_PAGE_NUMBER;
import static com.github.frankiie.springboot.constants.VALUES.DEFAULT_PAGE_SIZE;

@RequiredArgsConstructor
@RestController
@Tag(name = "Users")
@RequestMapping("/api/users")
public class UsersController {
    @Autowired private final CreateUserService createService;
    @Autowired private final UpdateUserService updateService;
    @Autowired private final RemoveUserService removeService;
    @Autowired private final FindUserService findService;

    @GetMapping
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Returns a list of users")
    public ResponseEntity<Page<UserResponse>> index(Optional<Integer> page, Optional<Integer> size)  {
        var content = findService.find(page, size);
        return ok(content.map(UserResponse::new));
    }
    
    @GetMapping("/{id}")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Show user info")
    public ResponseEntity<UserResponse> show(@PathVariable Long id) {
        var user = findService.find(id);
        return ok(new UserResponse(user));
    }
    
    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Register a new user", description = "Returns the new user")
    public ResponseEntity<UserResponse> save(@Validated @RequestBody CreateUserProps props) {
        var user = createService.create(props);
        return created(new UserResponse(user), "api/users");
    }
    
    @PutMapping("/{id}")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Update user data")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody @Validated UpdateUserProps body) {
        var user = updateService.update(id, body);
        return created(new UserResponse(user));
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @SecurityRequirement(name = "token")
    @Operation(summary = "Delete user")
    public void destroy(@PathVariable Long id) {
        removeService.remove(id);
    }

}