package com.github.frankiie.springboot.domain.user.service;

import static com.github.frankiie.springboot.constants.MESSAGES.NOT_AUTHORIZED_TO_MODIFY;
import static com.github.frankiie.springboot.domain.mail.validation.EmailValidations.validateEmailUniquenessOnModify;
import static com.github.frankiie.springboot.domain.session.service.SessionService.authorized;
import static com.github.frankiie.springboot.utils.Messages.message;
import static com.github.frankiie.springboot.utils.Responses.notFound;
import static com.github.frankiie.springboot.utils.Responses.unauthorized;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domain.user.entity.User;
import com.github.frankiie.springboot.domain.user.form.UpdateUserProps;
import com.github.frankiie.springboot.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UpdateUserService {
    @Autowired private final UserRepository userRepository;

    public User update(Long id, UpdateUserProps body) {
        authorized()
            .filter(authorized -> authorized.isMeOrAdmin(id))
            .orElseThrow(() -> unauthorized(message(NOT_AUTHORIZED_TO_MODIFY, "'user'")));

        var actual = userRepository
            .findByIdFetchRoles(id)
            .orElseThrow(() -> notFound("User not found"));
        
        validateEmailUniquenessOnModify(body, actual);

        actual.merge(body);

        userRepository.save(actual);
        return actual;
    }
}
