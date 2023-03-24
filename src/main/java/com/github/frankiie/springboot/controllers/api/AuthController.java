package com.github.frankiie.springboot.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.github.frankiie.springboot.domain.pagination.model.Page;
import com.github.frankiie.springboot.domain.user.form.CreateUserProps;
import com.github.frankiie.springboot.domain.user.form.SigninProps;
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

@RequiredArgsConstructor
@RestController
@Tag(name = "Authentication")
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private final CreateUserService createService;
    @Autowired private final UpdateUserService updateService;
    @Autowired private final RemoveUserService removeService;
    @Autowired private final FindUserService findService;
    @Autowired private final AuthenticationManager authenticationManager;


    @PostMapping("/signup")
    @ResponseStatus(CREATED)
    @Operation(summary = "Register a new user", description = "Authorizing user")
    public ResponseEntity<UserResponse> save(@Validated @RequestBody CreateUserProps props) {
        var user = createService.create(props);
        return created(new UserResponse(user), "api/auth/signup");
    }


    // @PostMapping("/signin")
    // @ResponseStatus(CREATED)
    // @Operation(summary = "Register a new user", description = "Authenticating user")
    // public ResponseEntity<UserResponse> signin(@Validated @RequestBody SigninProps props) {
    //     Authentication authentication = authenticationManager.authenticate(
    //       new UsernamePasswordAuthenticationToken(props.getEmail(), props.getPassword())
    //     );
    //     SecurityContextHolder.getContext().setAuthentication(authentication);

    //     return created(new UserResponse(user), "api/auth/signup");
    // }
}
