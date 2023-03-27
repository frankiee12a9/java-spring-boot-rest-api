package com.github.frankiie.springboot.domain.session.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domain.session.model.Authorized;
import com.github.frankiie.springboot.domain.user.repository.UserRepository;
import com.github.frankiie.springboot.utils.Authorization;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.github.frankiie.springboot.constants.MESSAGES.INVALID_USERNAME;
import static com.github.frankiie.springboot.constants.SECURITY.*;
import static com.github.frankiie.springboot.utils.Messages.message;
import static com.github.frankiie.springboot.utils.Responses.expired;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;

@RequiredArgsConstructor
@Service
public class SessionService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionService.class);

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException(message(INVALID_USERNAME)));
        
        return new Authorized(user);
    }

    public static void authorize(HttpServletRequest request, HttpServletResponse response) {
        if (PUBLICS.anyMatch(request)) {
            return;
        }

        var token = Authorization.extract(request);
        if (isNull(token)) {
            return;
        }

        try {
            var authorized = JWT.decode(token, TOKEN_SECRET);
            LOGGER.info("> Authorized: " + authorized.getAuthentication());

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authorized.getAuthentication());
        } catch (Exception exception) {
            expired(response);
        }
    }

    public static Long serviceOwnerId() {
      return authorized().map(authorized -> authorized.getId()).orElse(null);
    }

    public static Optional<Authorized> authorized() {
        try {
          var principal = getPrincipal();

          if (nonNull(principal) && principal instanceof Authorized authorized) {
              return of(authorized);
          }
          return empty();
        } catch (Exception exception) {
          return empty();
        }
    }

    private static Object getPrincipal() {
        var authentication = SecurityContextHolder
                              .getContext()
                              .getAuthentication();
        if (nonNull(authentication)) {
            return authentication.getPrincipal();
        }
        return null;
    }

}
