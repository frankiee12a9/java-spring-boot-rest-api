package com.github.frankiie.springboot.controllers.api;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.frankiie.springboot.domains.recovery.model.RecoveryConfirm;
import com.github.frankiie.springboot.domains.recovery.model.RecoveryRequest;
import com.github.frankiie.springboot.domains.recovery.model.RecoveryUpdate;
import com.github.frankiie.springboot.domains.recovery.service.RecoveryConfirmService;
import com.github.frankiie.springboot.domains.recovery.service.RecoveryService;
import com.github.frankiie.springboot.domains.recovery.service.RecoveryUpdateService;

@RequiredArgsConstructor
@RestController
@Tag(name = "Password recovery")
@RequestMapping("/api/recoveries")
public class RecoveriesController {
    @Autowired private final RecoveryService recoveryService;
    @Autowired private final RecoveryConfirmService confirmService;
    @Autowired private final RecoveryUpdateService updateService;

    @PostMapping
    @ResponseStatus(NO_CONTENT)
    @Operation(
        summary = "Starts recovery password process",
        description = "Sends a email to user with recovery code"
    )
    public void recovery(@RequestBody RecoveryRequest request) {
        recoveryService.recovery(request.getEmail());
    }

    @PostMapping("/confirm")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Confirm recovery code")
    public void confirm(@RequestBody RecoveryConfirm confirm) {
        confirmService.confirm(confirm.getEmail(), confirm.getCode());
    }

    @PostMapping("/update")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Update user password")
    public void update(@RequestBody RecoveryUpdate update) {        
        updateService.update(update.getEmail(), update.getCode(), update.getPassword());
    }

}
