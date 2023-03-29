package com.github.frankiie.springboot.domains.session.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domains.collection_note.service.NoteService;

import static com.github.frankiie.springboot.constants.MESSAGES.NOT_AUTHORIZED_TO_MODIFY;
import static com.github.frankiie.springboot.domains.session.service.SessionService.authorized;
import static com.github.frankiie.springboot.utils.Messages.message;
import static com.github.frankiie.springboot.utils.Responses.unauthorized;


@Service
public class AuthorizationService {
    @Autowired private NoteService noteService;

    public  boolean isOwner(Long userId, Long noteId) {
        // authorized()
        //   .filter(authorized -> authorized.isMeOrAdmin(userId) && noteService.authorizeOwnership(userId, noteId))
        //   .orElseThrow(() -> unauthorized(message(NOT_AUTHORIZED_TO_MODIFY, "'user'")));
        
      return true;
    }
}
