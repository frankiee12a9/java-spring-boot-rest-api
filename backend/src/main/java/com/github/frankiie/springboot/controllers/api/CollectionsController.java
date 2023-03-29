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
import org.springframework.web.bind.annotation.DeleteMapping;
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

import com.github.frankiie.springboot.domains.collection.payload.CollectionResponse;
import com.github.frankiie.springboot.domains.collection.payload.CreateCollectionProps;
import com.github.frankiie.springboot.domains.collection.payload.UpdateCollectionProps;
import com.github.frankiie.springboot.domains.collection.service.CreateCollectionService;
import com.github.frankiie.springboot.domains.collection.service.FindCollectionService;
import com.github.frankiie.springboot.domains.collection.service.UpdateCollectionService;
import com.github.frankiie.springboot.domains.collection_image.entity.Image;
import com.github.frankiie.springboot.domains.collection_image.payload.CreateImageProps;
import com.github.frankiie.springboot.domains.collection_image.service.ImageService;
import com.github.frankiie.springboot.domains.collection_note.payload.NoteResponse;
import com.github.frankiie.springboot.domains.collection_note.service.FindNoteService;
import com.github.frankiie.springboot.domains.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domains.collection_note_comment.payload.CommentResponse;
import com.github.frankiie.springboot.domains.collection_note_comment.payload.CreateCommentProps;
import com.github.frankiie.springboot.domains.collection_note_comment.service.CommentService;
import com.github.frankiie.springboot.domains.pagination.model.Page;

import static com.github.frankiie.springboot.utils.Responses.*;
import static org.springframework.http.HttpStatus.*;
import static com.github.frankiie.springboot.constants.VALUES.*;

@RequiredArgsConstructor
@RestController
@Tag(name = "Collections management")
@RequestMapping("/api/collections")
public class CollectionsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionsController.class);

    @Autowired private final FindCollectionService findService;
    @Autowired private final UpdateCollectionService updateService;
    @Autowired private final CreateCollectionService createService;

    @Autowired private final CommentService commentService;
    @Autowired private final FindNoteService findNoteService;
    @Autowired private final ImageService imageService;

    @GetMapping
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Returns a list of collections with paging")
    public ResponseEntity<Page<CollectionResponse>> getMany(Optional<Integer> page, Optional<Integer> size) {
        var content = findService.findMany(page, size);
        return ok(content.map(CollectionResponse::new));
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "")
    public ResponseEntity<CollectionResponse> getOne(@PathVariable Long id) {
      var collection = findService.findById(id);
      var response = new CollectionResponse(collection);
      return ok(response);
    }

    @GetMapping("/{id}/notes")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Returns a list of notes from current collection by id with paging")
    public ResponseEntity<Page<NoteResponse>> getNotes(@PathVariable Long id, Optional<Integer> page, Optional<Integer> size) {
      var response = findNoteService.findByCollectionId(id, page, size);
      return ok(response.map(NoteResponse::new));
    }

    @PostMapping
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(
        summary = "",
        description = ""
    )
    public ResponseEntity<CollectionResponse> save(@Validated @ModelAttribute CreateCollectionProps createProps) {
      var collection = createService.create(createProps);
      var response = new CollectionResponse(collection);
      return created(response, "api/collections");
    }

    @PutMapping("/{id}/addComment")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(
        summary = "",
        description = ""
    )
    public ResponseEntity<Comment> addOrUpdateComment(@PathVariable Long id,  @Validated @RequestBody CreateCommentProps createProps) {
      var comment = updateService.updateByAddingComment(id, createProps);
      return created(comment, "/api/collections/" + id + "/addComment");
    }

    @PutMapping("/{id}/addImage")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(
        summary = "",
        description = ""
    )
    public ResponseEntity<Image> addOrUpdateImage(@PathVariable Long id,  @Validated @ModelAttribute CreateImageProps createProps) {
      var image = updateService.updateByAddingImage(id, createProps);
      return created(image, "/api/collections/" + id + "/addImage");
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(
        summary = "",
        description = ""
    )
    public ResponseEntity<CollectionResponse> put(@PathVariable Long id, @Validated @RequestBody UpdateCollectionProps updateProps) {
      var collection = updateService.updateById(id, updateProps);
      var response = new CollectionResponse(collection);
      return created(response, "/api/collections/" + id);
    }

    @GetMapping("/{id}/comments")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Returns a list of comments from current collection with paging")
    public ResponseEntity<Page<CommentResponse>> getComments(@PathVariable Long id, Optional<Integer> page, Optional<Integer> size)  {
      var response = findService.findCommentsById(id, page, size);
      return ok(response.map(CommentResponse::new));
    }

    @GetMapping("/search")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Returns a list of collections matched given keyword with paging")
    public ResponseEntity<Page<CollectionResponse>> searchAndGet(@RequestParam String keyword, Optional<Integer> page, Optional<Integer> size)  {
      var collections = findService.findByKeyword(keyword, page, size);
      return ok(collections.map(CollectionResponse::new));
    }
    
    @DeleteMapping("/{id}/images/{imageId}")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(
      summary = ""
    )
    public void deleteImage(@PathVariable Long id, @PathVariable Long imageId)  {
      imageService.deleteImageById(imageId);
    }

    @DeleteMapping("/{id}/images")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(
      summary = ""
    )
    public void deleteImages(@PathVariable Long id)  {
      imageService.deleteImageByCollectionId(id);
    }

    @DeleteMapping("/{id}/comments")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(
      summary = "", 
      description = ""
    )
    public void deleteComment(@PathVariable Long id)  {
      commentService.deleteByCollectionId(id);
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    @SecurityRequirement(name = "token")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(
      summary = "", 
      description = ""
    )
    public void deleteComments(@PathVariable Long id, @PathVariable Long commentId)  {
      commentService.deleteById(commentId);
    }

}
