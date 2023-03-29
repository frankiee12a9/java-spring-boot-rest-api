package com.github.frankiie.springboot.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.frankiie.springboot.domains.user.entity.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String username;
    @JsonIgnore private String email;
    @JsonIgnore private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String name, String username ,String email, String password,
        Collection<? extends GrantedAuthority> authorities) {
      this.id = id;
      this.name = name;
      this.email = email;
      this.username = username;
      this.password = password;

      if (authorities == null) {
        this.authorities = null;
      } else {
        this.authorities = new ArrayList<>(authorities);
      }
    }

    public static UserPrincipal create(User user) {
      List<GrantedAuthority> authorities = user.getRoles().stream()
          .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

      return new UserPrincipal(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
    }

    public Long getId() {
      return id;
    }

    public String getEmail() {
      return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities == null ? null : new ArrayList<>(authorities);
    }

    @Override
    public String getPassword() {
      return password;
    }

    @Override
    public String getUsername() {
      return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    @Override
    public boolean isAccountNonLocked() {
      return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }

    @Override
    public boolean isEnabled() {
      return true;
    }

    public boolean equals(Object object) {
      if (this == object)
        return true;
      if (object == null || getClass() != object.getClass())
        return false;
      UserPrincipal that = (UserPrincipal) object;
      return Objects.equals(id, that.id);
    }

    public int hashCode() {
      return Objects.hash(id);
    }

}
