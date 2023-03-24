package com.github.frankiie.springboot.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domain.user.entity.User;
import com.github.frankiie.springboot.domain.user.repository.UserRepository;


// @Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    @Autowired private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
      User user = userRepository.findByEmail(email)
          .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with this username or email: %s", email)));
      return UserPrincipal.create(user);
    }

  	@Override
    @Transactional
    public UserDetails loadUserById(Long id) {
      User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with id: %s", id)));

      return UserPrincipal.create(user);
    }

}
