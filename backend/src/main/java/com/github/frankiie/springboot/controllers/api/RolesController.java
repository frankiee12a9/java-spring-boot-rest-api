package com.github.frankiie.springboot.controllers.api;

import static com.github.frankiie.springboot.domains.session.service.SessionService.authorized;
import static com.github.frankiie.springboot.utils.Responses.ok;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.frankiie.springboot.domains.role.entity.Role;
import com.github.frankiie.springboot.domains.role.repository.RoleRepository;

@RequiredArgsConstructor
@RestController
@Tag(name = "Roles")
@RequestMapping("/api/roles")
@SecurityRequirement(name = "token")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class RolesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RolesController.class);

    @Autowired private final RoleRepository repository;

    @GetMapping
    @Operation(summary = "Returns a list of roles")
    public ResponseEntity<List<Role>> index() {
      var principal = authorized();
      LOGGER.info("UserPrincipal [1]: " + principal.get().getAuthentication());
      return ok(repository.findAll());
    }

}