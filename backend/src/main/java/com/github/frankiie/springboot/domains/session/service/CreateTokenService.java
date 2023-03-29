package com.github.frankiie.springboot.domains.session.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domains.session.entity.RefreshToken;
import com.github.frankiie.springboot.domains.session.model.TokenRequest;
import com.github.frankiie.springboot.domains.session.model.TokenResponse;
import com.github.frankiie.springboot.domains.session.repository.RefreshTokenRepository;
import com.github.frankiie.springboot.domains.user.payload.UserResponse;
import com.github.frankiie.springboot.domains.user.repository.UserRepository;

import static com.github.frankiie.springboot.constants.MESSAGES.CREATE_SESSION_ERROR_MESSAGE;
import static com.github.frankiie.springboot.constants.SECURITY.*;
import static com.github.frankiie.springboot.utils.Messages.message;
import static com.github.frankiie.springboot.utils.Responses.forbidden;

import java.time.LocalDateTime;

@Service
public class CreateTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Autowired
    public CreateTokenService(
        RefreshTokenRepository refreshTokenRepository,
        UserRepository userRepository
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public TokenResponse create(TokenRequest request) {
        var session = userRepository.findByEmail(request.getEmail())
            .filter(user -> user.validatePassword(request.getPassword()))
                .orElseThrow(() -> forbidden(message(CREATE_SESSION_ERROR_MESSAGE)));
        return create(new UserResponse(session));
    }

    public TokenResponse create(UserResponse user) {
        var now = LocalDateTime.now();
        var expiresIn = now.plusHours(TOKEN_EXPIRATION_IN_HOURS);

        // var token = JWT.encode(user, expiresIn, TOKEN_SECRET);
        var token = JWT.encodeWithMoreDetails(user, expiresIn, TOKEN_SECRET);
        var refresh = new RefreshToken(user, REFRESH_TOKEN_EXPIRATION_IN_DAYS);

        refreshTokenRepository.disableOldRefreshTokens(user.getId());

        refreshTokenRepository.save(refresh);

        return new TokenResponse(
            user,
            token,
            refresh,
            expiresIn
        );
    }
}