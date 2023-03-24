package com.github.frankiie.springboot.domain.recovery.service;

import static com.github.frankiie.springboot.constants.MAIL.ERROR_SENDING_EMAIL_MESSAGE_TO;
import static com.github.frankiie.springboot.constants.PASSWORD_RECOVERY.MINUTES_TO_EXPIRE_RECOVERY_CODE;
import static com.github.frankiie.springboot.constants.PASSWORD_RECOVERY.SUBJECT_PASSWORD_RECOVERY_CODE;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domain.mail.service.MailService;
import com.github.frankiie.springboot.domain.recovery.entity.Recovery;
import com.github.frankiie.springboot.domain.recovery.model.RecoveryEmail;
import com.github.frankiie.springboot.domain.recovery.repository.RecoveryRepository;
import com.github.frankiie.springboot.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecoveryService {
    private static final Logger LOGGER = Logger.getLogger(RecoveryService.class.getName());

    @Autowired private final UserRepository users;
    @Autowired private final RecoveryRepository recoveries;
    @Autowired private final MailService service;

    public void recovery(String email) {
        var user = users.findByEmail(email);
        if (user.isEmpty()) {
            return;
        }

        var recovery = new Recovery(user.get(), MINUTES_TO_EXPIRE_RECOVERY_CODE);
        recoveries.save(recovery);

        var sendEmailInBackground = new Thread(() -> {
            var recoveryEmail = new RecoveryEmail(
                email,
                SUBJECT_PASSWORD_RECOVERY_CODE,
                user.get().getName(),
                recovery.getCode()
            );

            try {                
                service.send(recoveryEmail);
            } catch (Exception exception) {
                LOGGER.log(Level.INFO, ERROR_SENDING_EMAIL_MESSAGE_TO, email);
            }
        });
        sendEmailInBackground.start();
    }

}
