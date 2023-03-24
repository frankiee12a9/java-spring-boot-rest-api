package com.github.frankiie.springboot.domain.mail.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.github.frankiie.springboot.domain.mail.model.Addressable;
import com.github.frankiie.springboot.domain.user.repository.UserRepository;
import com.github.frankiie.springboot.errors.exceptions.BadRequestException;
import com.github.frankiie.springboot.errors.model.ValidationError;

import static com.github.frankiie.springboot.constants.MESSAGES.EMAIL_ALREADY_USED_MESSAGE;
import static com.github.frankiie.springboot.utils.Messages.message;

import java.util.List;

@Component
public class EmailValidations {

    private static UserRepository repository;

    @Autowired
    public EmailValidations(UserRepository repository) {
        EmailValidations.repository = repository;
    }

    public static void validateEmailUniqueness(Addressable entity) {
        if (repository.existsByEmail(entity.getEmail())) {
            throw new BadRequestException(List.of(new ValidationError("email", message(EMAIL_ALREADY_USED_MESSAGE))));
        }
    }

    public static void validateEmailUniqueness(Addressable entity, BindingResult validations) {
        if (repository.existsByEmail(entity.getEmail())) {
            validations.addError(new ObjectError("email", message(EMAIL_ALREADY_USED_MESSAGE)));
        }
    }

    public static void validateEmailUniquenessOnModify(Addressable newEntity, Addressable actualEntity) {
        var newEmail = newEntity.getEmail();
        var actualEmail = actualEntity.getEmail();

        var changedEmail = !actualEmail.equals(newEmail);

        var emailAlreadyUsed = repository.existsByEmail(newEmail);

        if (changedEmail && emailAlreadyUsed) {
            throw new BadRequestException(List.of(new ValidationError("email", message(EMAIL_ALREADY_USED_MESSAGE))));
        }
    }

    public static void validateEmailUniquenessOnModify(
        Addressable newEntity,
        Addressable actualEntity,
        BindingResult validations
    ) {

        var newEmail = newEntity.getEmail();
        var actualEmail = actualEntity.getEmail();

        var changedEmail = !actualEmail.equals(newEmail);

        var emailAlreadyUsed = repository.existsByEmail(newEmail);

        if (changedEmail && emailAlreadyUsed) {
            validations.addError(new ObjectError("email", message(EMAIL_ALREADY_USED_MESSAGE)));
        }
    }
}
