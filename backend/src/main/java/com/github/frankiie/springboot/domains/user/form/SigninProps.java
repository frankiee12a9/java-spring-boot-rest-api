package com.github.frankiie.springboot.domains.user.form;

import static com.github.frankiie.springboot.domains.mail.validation.EmailValidations.validateEmailUniqueness;

import javax.validation.constraints.NotEmpty;

import org.springframework.validation.BindingResult;

import com.github.frankiie.springboot.domains.mail.model.Addressable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SigninProps implements Addressable {
    @NotEmpty(message = "email must not be empty")
    private String email;

    @NotEmpty(message = "password must not be empty")
    private String password;

    public void validate(BindingResult result) {
        validateEmailUniqueness(this, result);
    }

    public void validate() {
        validateEmailUniqueness(this);
    }
}
