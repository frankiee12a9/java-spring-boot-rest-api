package com.github.frankiie.springboot.middlewares;

import static com.github.frankiie.springboot.domains.session.service.SessionService.authorize;
import static com.github.frankiie.springboot.domains.session.service.SessionService.authorized;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.github.frankiie.springboot.annotations.OwnershipRequired;
import com.github.frankiie.springboot.domains.collection_note.service.NoteService;
import com.github.frankiie.springboot.domains.session.service.AuthorizationService;

@Component
// @Order(2)
public class OwnershipInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(OwnershipInterceptor.class);

    @Autowired private NoteService noteService;
    @Autowired private AuthorizationService authorizationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
      LOGGER.info("> PreHandle: "); 

      authorize(request, response);

      if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(OwnershipRequired.class)) {
                // Get the userId and recordId from the request
                // Long userId = authorizationService.getCurrentUserId();
                Long recordId = Long.valueOf(request.getParameter("recordId"));

                // Check ownership
                // if (!authorizationService.isOwner(userId, recordId)) {
                //     throw new UnauthorizedException("You are not authorized to perform this action.");
                // }
            }
        }
      return true;
    }
}
