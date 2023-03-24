package com.github.frankiie.springboot.controllers.app;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.frankiie.springboot.domain.pagination.service.Pagination;
import com.github.frankiie.springboot.domain.toast.Toasts;
import com.github.frankiie.springboot.domain.toast.Type;
import com.github.frankiie.springboot.domain.user.form.CreateOrUpdateUserByAppForm;
import com.github.frankiie.springboot.domain.user.repository.UserRepository;
import com.github.frankiie.springboot.domain.user.service.RemoveUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@PreAuthorize("hasAnyAuthority('ADM')")
@RequestMapping("/app/users")
public class UserController {
    @Autowired private final UserRepository repository;
    
    @Autowired private final RemoveUserService removeService;
    
    @GetMapping
    public String index(Model model, Optional<Integer> page, Optional<Integer> size) {
        var pageable = Pagination.of(page, size);
        var content = repository.findAll(pageable);
        
        model.addAttribute("page", content);
        
        return "app/users/index";
    }

    @GetMapping("/form")
    public String createForm(Model model) {
        model.addAttribute("create", true);
        return "app/users/form";
    }

    @GetMapping("/form/{id}")
    public String editForm(Model model) {
        model.addAttribute("create", false);
        return "app/users/form";
    }

    @PostMapping(produces = "text/html")
    public String create(
        @Valid CreateOrUpdateUserByAppForm props,
        BindingResult validations,
        RedirectAttributes redirect,
        Model model
    ) {
        if (validations.hasErrors()) {
            return "app/users/index";
        }

        return "redirect:/app/users/form";
    }
    
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        removeService.remove(id);
        
        Toasts.add(redirect, "Usuário deletado com sucesso.", Type.SUCCESS);
        
        return "redirect:/app/users";
    }

}
