package com.github.frankiie.springboot.middlewares;

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

@Component
public class NoteServiceOwnershipInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(OwnershipInterceptor.class);

    @Autowired NoteService noteService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
      LOGGER.info("> PreHandle: ", request); 
      // Get the user ID from the request
      Long userId = (Long) request.getAttribute("userId");

      // Get the note ID from the request
      Long noteId = Long.valueOf(request.getParameter("noteId"));

      // Check if the user is the owner of the note
      // if (!noteService.isOwner(noteId, userId)) {
      //     // Return an error response if the user is not the owner
      //     response.sendError(HttpStatus.FORBIDDEN.value());
      //     return false;
      // }

      // Allow the request to proceed if the user is the owner
      return true;
    }
}
