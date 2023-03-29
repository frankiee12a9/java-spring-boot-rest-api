package com.github.frankiie.springboot.domains.session.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import com.github.frankiie.springboot.domains.role.entity.Role;

import java.io.Serial;
import java.util.List;

public class Authorized extends User {

    @Serial private static final long serialVersionUID = 1L;

    private final Long id;
    private final String username;

    public Authorized(Long id,  List<Role> authorities) {
        super("USERNAME", "SECRET", authorities);
        this.id = id;
        this.username = "";
    }

    public Authorized(com.github.frankiie.springboot.domains.user.entity.User user) {
        super(
            user.getEmail(),
            user.getPassword(),
            user.isActive(),
            true,
            true,
            true,
            user.getRoles()
        );
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    
    /**
     * @return
     */
    public UsernamePasswordAuthenticationToken getAuthentication() {
        return new UsernamePasswordAuthenticationToken(this, null, getAuthorities());
    }

    public Boolean isAdmin() {
        return getAuthorities()
            .stream()
            .anyMatch((role) -> role.getAuthority().equals("ADMIN"));
    }

    public Boolean isMeOrAdmin(Long id) {
        var admin = isAdmin();
        var equals = getId().equals(id);
        if (admin) {
            return true;
        }
        return equals;
    }
 
    @Override
    public String toString() {
        return getId().toString();
    }
}