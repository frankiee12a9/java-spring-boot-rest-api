package com.github.frankiie.springboot.domain.user.form;

import static com.github.frankiie.springboot.domain.mail.validation.EmailValidations.validateEmailUniqueness;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.BindingResult;

import com.github.frankiie.springboot.domain.mail.model.Addressable;
import com.github.frankiie.springboot.domain.role.entity.Role;
import com.github.frankiie.springboot.domain.user.entity.User;
import com.github.frankiie.springboot.utils.JSON;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CreateOrUpdateUserByAppForm implements Addressable {

    private Long id;

    @NotEmpty(message = "${user.name.not-empty}")
    private String name;

    @NotEmpty(message = "${user.name.not-empty}")
    private String username;

    @NotEmpty(message = "{user.email.not-empty}")
    @Email(message = "{user.email.is-valid}")
    private String email;

    @NotNull
    private List<Role> roles;

    public void validate(BindingResult result) {
        validateEmailUniqueness(this, result);
    }

    public void validate() {
        validateEmailUniqueness(this);
    }

    public User user() {
        var user = new User(name, username, email, null, roles);
        user.setId(id);
        return user;
    }

    @Override
    public String toString() {
        return JSON.stringify(this);
    }
}
