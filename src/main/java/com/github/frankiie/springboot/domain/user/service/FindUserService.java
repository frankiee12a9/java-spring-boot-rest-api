package com.github.frankiie.springboot.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domain.pagination.model.Page;
import com.github.frankiie.springboot.domain.pagination.service.Pagination;
import com.github.frankiie.springboot.domain.user.entity.User;
import com.github.frankiie.springboot.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import static com.github.frankiie.springboot.constants.MESSAGES.NOT_AUTHORIZED_TO_READ;
import static com.github.frankiie.springboot.domain.session.service.SessionService.authorized;
import static com.github.frankiie.springboot.utils.Messages.message;
import static com.github.frankiie.springboot.utils.Responses.notFound;
import static com.github.frankiie.springboot.utils.Responses.unauthorized;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FindUserService {
    @Autowired private final UserRepository repository;

    public User find(Long id) {
        authorized()
            .filter(authorized -> authorized.itsMeOrSessionIsADM(id))
            .orElseThrow(() -> unauthorized(message(NOT_AUTHORIZED_TO_READ, "'user'")));

        return repository
            .findById(id)
            .orElseThrow(() -> notFound("Not found"));
    }

    public Page<User> find(Optional<Integer> page, Optional<Integer> size) {
        var pageable = Pagination.of(page, size);
        return repository.findAll(pageable);
    }
}
