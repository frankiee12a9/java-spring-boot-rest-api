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

import com.github.frankiie.springboot.domain.collection_image.payload.CreateImageProps;
import com.github.frankiie.springboot.domain.collection_image.payload.ImageResponse;
import com.github.frankiie.springboot.domain.collection_note.entity.Note;
import com.github.frankiie.springboot.domain.collection_note.payload.CreateNoteProps;
import com.github.frankiie.springboot.domain.collection_note.payload.NoteResponse;
import com.github.frankiie.springboot.domain.collection_note.payload.UpdateNoteProps;
import com.github.frankiie.springboot.domain.collection_note.service.NoteService;
import com.github.frankiie.springboot.domain.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domain.collection_note_comment.payload.CreateCommentProps;
import com.github.frankiie.springboot.domain.pagination.model.Page;

import static com.github.frankiie.springboot.utils.Responses.*;
import static org.springframework.http.HttpStatus.*;
import static com.github.frankiie.springboot.constants.VALUES.*;

@RequiredArgsConstructor
@RestController
@Tag(name = "Notes management")
@RequestMapping("/api/notes")
public class NotesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotesController.class);

    @Autowired private final NoteService noteService;

    @GetMapping
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Returns a list of notes with paging")
    public ResponseEntity<Page<NoteResponse>> getMany(Optional<Integer> page, Optional<Integer> size) {
        var content = noteService.findMany(page, size);
        return ok(content.map(NoteResponse::new));
    }

    @GetMapping("/sort")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Returns a list of notes with paging based on filter (name, created_at, used_at) provided")
    public ResponseEntity<Page<NoteResponse>> getByFilter(@RequestParam String filter, Optional<Integer> page, Optional<Integer> size) {
        var content = noteService.findManyWithFilter(filter, page, size);
        return ok(content.map(NoteResponse::new));
    }

    @GetMapping("/{id}/images")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Returns a list of images from current note with paging")
    public ResponseEntity<Page<ImageResponse>> getImages(@PathVariable Long id, Optional<Integer> page, Optional<Integer> size) {
         var response = noteService.findImagesById(id, page, size);
        return ok(response.map(ImageResponse::new));
    }

    @GetMapping("/search")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Returns a list of notes containing given keyword with paging")
    public ResponseEntity<Page<NoteResponse>> getImages(@RequestParam String keyword, Optional<Integer> page, Optional<Integer> size) {
         var response = noteService.findByKeyword(keyword, page, size);
        return ok(response.map(NoteResponse::new));
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "")
    public ResponseEntity<NoteResponse> getOne(@PathVariable Long id) {
      var note = noteService.findById(id);
      var response = new NoteResponse(note);
      return ok(response);
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
      return created(response, "api/notes");
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(
        summary = "",
        description = ""
    )
    public ResponseEntity<NoteResponse> save(@PathVariable Long id, @Validated @RequestBody UpdateNoteProps updateProps) {
      var note = noteService.updateById(id, updateProps);
      return created(new NoteResponse(note), "api/notes");
    }

    @PutMapping("/{id}/addImage")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(
        summary = "",
        description = ""
    )
    public ResponseEntity<ImageResponse> updateOrAddImages(@PathVariable Long id, @ModelAttribute CreateImageProps createProps) {
      var note = noteService.updateByAddingImage(id, createProps);
      return created(new ImageResponse(note), "api/notes");
    }

}
