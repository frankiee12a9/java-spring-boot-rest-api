package com.github.frankiie.springboot.domains.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.frankiie.springboot.domains.role.repository.RoleRepository;
import com.github.frankiie.springboot.domains.toast.Toasts;
import com.github.frankiie.springboot.domains.user.entity.User;
import com.github.frankiie.springboot.domains.user.form.CreateUserProps;
import com.github.frankiie.springboot.domains.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import static com.github.frankiie.springboot.constants.TOAST_MESSAGES.TOAST_SUCCESS_MESSAGE;
import static com.github.frankiie.springboot.domains.mail.validation.EmailValidations.validateEmailUniqueness;
import static com.github.frankiie.springboot.domains.toast.Type.SUCCESS;
import static com.github.frankiie.springboot.utils.Messages.message;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CreateUserService {
    @Autowired private final UserRepository userRepository;
    @Autowired private final RoleRepository roleRepository;

    public User create(CreateUserProps props) {
        validateEmailUniqueness(props);
        return save(props);
    }

    public void create(
        CreateUserProps props,
        BindingResult validations,
        RedirectAttributes redirect,
        Model model
    ) {
        validateEmailUniqueness(props, validations);

        if (validations.hasErrors()) {
            model.addAttribute("user", props);
            Toasts.add(model, validations);
            return;
        }

        Toasts.add(redirect, message(TOAST_SUCCESS_MESSAGE), SUCCESS);
        save(props);
    }

    private User save(CreateUserProps props) {
        var roles = roleRepository.findOptionalByInitials("USER")
            .map(List::of)
                .orElseGet(List::of);
        return userRepository.save(new User(props, roles));
    }
}
