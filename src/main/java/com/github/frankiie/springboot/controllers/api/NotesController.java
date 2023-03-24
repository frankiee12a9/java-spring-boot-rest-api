package com.github.frankiie.springboot.controllers.api;

import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.frankiie.springboot.domain.collection.entity.Collection;
import com.github.frankiie.springboot.domain.collection.payload.CollectionResponse;
import com.github.frankiie.springboot.domain.collection.payload.CreateCollectionProps;
import com.github.frankiie.springboot.domain.collection.payload.UpdateCollectionProps;
import com.github.frankiie.springboot.domain.collection.service.CollectionService;
import com.github.frankiie.springboot.domain.collection_image.entity.Image;
import com.github.frankiie.springboot.domain.collection_image.payload.CreateImageProps;
import com.github.frankiie.springboot.domain.collection_image.payload.ImageResponse;
import com.github.frankiie.springboot.domain.collection_note.entity.Note;
import com.github.frankiie.springboot.domain.collection_note.payload.CreateNoteProps;
import com.github.frankiie.springboot.domain.collection_note.service.NoteService;
import com.github.frankiie.springboot.domain.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domain.collection_note_comment.payload.CreateCommentProps;
import com.github.frankiie.springboot.domain.pagination.model.Page;

import static com.github.frankiie.springboot.utils.Responses.created;
import static com.github.frankiie.springboot.utils.Responses.ok;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static com.github.frankiie.springboot.utils.Responses.created;
import static com.github.frankiie.springboot.utils.Responses.ok;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static com.github.frankiie.springboot.constants.VALUES.DEFAULT_PAGE_NUMBER;
import static com.github.frankiie.springboot.constants.VALUES.DEFAULT_PAGE_SIZE;

@RequiredArgsConstructor
@RestController
@Tag(name = "Notes management")
@RequestMapping("/api/notes")
public class NotesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotesController.class);

    @Autowired private final NoteService noteService;

    @GetMapping
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Returns a list of courses with paging")
    public ResponseEntity<Page<Note>> getMany(Optional<Integer> page, Optional<Integer> size) {
        var content = noteService.findMany(page, size);
        // return ok(content.map(Note::new));
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "")
    public ResponseEntity<Note> getOne(@PathVariable Long id) {
      var note = noteService.findById(id);
      // var response = new CollectionResponse(collection);
      return ok(note);
    }


    @PostMapping
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(
        summary = "",
        description = ""
    )
    public ResponseEntity<Note> save(@Validated @RequestBody CreateNoteProps createProps) {
      var response = noteService.create(createProps);
      LOGGER.info(response.toString());
      return created(response, "api/notes");
    }
}
