package com.github.frankiie.springboot.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService {

    UserDetails loadUserByUsername(String name);
    UserDetails loadUserById(Long id);
}
