package com.github.frankiie.springboot.controllers.api;

import static com.github.frankiie.springboot.utils.Responses.ok;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.frankiie.springboot.domains.session.model.RefreshTokenRequest;
import com.github.frankiie.springboot.domains.session.model.RefreshTokenResponse;
import com.github.frankiie.springboot.domains.session.model.TokenRequest;
import com.github.frankiie.springboot.domains.session.model.TokenResponse;
import com.github.frankiie.springboot.domains.session.service.CreateTokenService;
import com.github.frankiie.springboot.domains.session.service.RefreshTokenService;


@RequiredArgsConstructor
@RestController
@Tag(name = "Authentication")
@RequestMapping("/api/sessions")
public class UserSessionsController {
    @Autowired private final CreateTokenService createService;
    @Autowired private final RefreshTokenService refreshService;

    @PostMapping
    @Operation(summary = "Return session of current user")
    public ResponseEntity<TokenResponse> create(@RequestBody @Valid TokenRequest request) {
        var token = createService.create(request);
        return ok(token);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Create a new jwt token from refresh code")
    public ResponseEntity<RefreshTokenResponse> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        var token = refreshService.refresh(request);
        return ok(token);
    }

}
