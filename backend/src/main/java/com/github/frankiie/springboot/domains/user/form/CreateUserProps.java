package com.github.frankiie.springboot.domains.user.form;

import static com.github.frankiie.springboot.domains.mail.validation.EmailValidations.validateEmailUniqueness;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import org.springframework.validation.BindingResult;

import com.github.frankiie.springboot.domains.mail.model.Addressable;
import com.github.frankiie.springboot.domains.user.entity.User;

@Data
public class CreateUserProps implements Addressable {

    @NotEmpty(message = "${user.name.not-empty}")
    private String name;

    @NotEmpty(message = "${user.name.not-empty}")
    private String username;

    @NotEmpty(message = "{user.email.not-empty}")
    @Email(message = "{user.email.is-valid}")
    private String email;

    @NotEmpty(message = "{user.password.not-empty}")
    @Size(min = 8, max = 155, message = "{user.password.size}")
    private String password;

    public void validate(BindingResult result) {
        validateEmailUniqueness(this, result);
    }

    public void validate() {
        validateEmailUniqueness(this);
    }
    
    public User user() {
        return new User(name, username, email, password, List.of());
    }
}
