package com.github.frankiie.springboot.domain.user.service;

import static com.github.frankiie.springboot.constants.MESSAGES.NOT_AUTHORIZED_TO_MODIFY;
import static com.github.frankiie.springboot.domain.session.service.SessionService.authorized;
import static com.github.frankiie.springboot.utils.Messages.message;
import static com.github.frankiie.springboot.utils.Responses.notFound;
import static com.github.frankiie.springboot.utils.Responses.unauthorized;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RemoveUserService {
    @Autowired private final UserRepository userRepository;

    public void remove(Long id) {
        authorized()
            .filter(authorized -> authorized.isMeOrAdmin(id))
                .orElseThrow(() -> unauthorized(message(NOT_AUTHORIZED_TO_MODIFY, "'user'")));
        
        var user = userRepository
            .findById(id)
                .orElseThrow(() -> notFound("User not found")); 
        
        userRepository.delete(user);
    }
}
