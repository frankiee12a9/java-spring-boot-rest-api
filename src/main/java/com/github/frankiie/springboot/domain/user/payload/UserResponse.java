package com.github.frankiie.springboot.domain.user.payload;

import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.List;

import com.github.frankiie.springboot.domain.management.model.Entity;
import com.github.frankiie.springboot.domain.role.entity.Role;
import com.github.frankiie.springboot.domain.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "User", requiredProperties = {"id", "name", "email", "roles"})
public class UserResponse implements Entity {
    private final Long id;
    private final String username;
    private final String email;

    private final List<String> roles;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getName();
        this.email = user.getEmail();

        this.roles = user.getRoles()
            .stream()
            .map(Role::getAuthority)
            .toList();
    }

    public UserResponse(Long id, String name, String email) {
        this.id = id;
        this.username = name;
        this.email = email;
        this.roles = new ArrayList<>();
    }    

    public UserResponse(Long id, String name, String email, String roles) {
        this.id = id;
        this.username = name;
        this.email = email;
        
        this.roles = ofNullable(roles)
            .map(string -> List.of(string.split(",")))
            .orElse(List.of());
    }
}
