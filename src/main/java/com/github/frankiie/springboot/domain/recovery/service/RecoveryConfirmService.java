package com.github.frankiie.springboot.domain.recovery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.frankiie.springboot.domain.recovery.repository.RecoveryRepository;
import com.github.frankiie.springboot.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecoveryConfirmService {
    @Autowired private final UserRepository users;

    @Autowired private final RecoveryRepository recoveryRepository;

    public void confirm(String email, String code) {
        var user = users.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

        var recovery = recoveryRepository
                .findFirstOptionalByUser_IdAndConfirmedIsFalseAndUsedIsFalseOrderByExpiresInDesc(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

        if (!recovery.nonExpired() || !recovery.getCode().equals(code)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        recovery.setConfirmed(true);

        recoveryRepository.save(recovery);
    }

}
