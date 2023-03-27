package com.github.frankiie.springboot.domain.collection.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domain.collection.entity.Collection;
import com.github.frankiie.springboot.domain.collection.payload.CreateCollectionProps;
import com.github.frankiie.springboot.domain.collection.payload.UpdateCollectionProps;
import com.github.frankiie.springboot.domain.collection.repository.CollectionRepository;
import com.github.frankiie.springboot.domain.collection_image.entity.Image;
import com.github.frankiie.springboot.domain.collection_image.payload.CreateImageProps;
import com.github.frankiie.springboot.domain.collection_image.repository.ImageRepository;
import com.github.frankiie.springboot.domain.collection_image.service.ImageService;
import com.github.frankiie.springboot.domain.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domain.collection_note_comment.payload.CreateCommentProps;
import com.github.frankiie.springboot.domain.collection_note_comment.service.CommentService;
import com.github.frankiie.springboot.domain.pagination.model.Page;
import com.github.frankiie.springboot.domain.pagination.service.Pagination;
import com.github.frankiie.springboot.utils.Responses;

import lombok.RequiredArgsConstructor;

import static com.github.frankiie.springboot.constants.MESSAGES.*;
import static com.github.frankiie.springboot.domain.session.service.SessionService.authorized;
import static com.github.frankiie.springboot.utils.Messages.*;
import static com.github.frankiie.springboot.utils.Responses.*;

import java.util.Optional;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CollectionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionService.class);

    @Autowired private final CollectionRepository collectionRepository;

    @Autowired private final ImageService imageService;
    @Autowired private final CommentService commentService;

    public Collection findById(Long id) {
      return collectionRepository.findById(id)
        .orElseThrow(() -> Responses.notFound("collection not found"));
    }

    public Page<Comment> findCommentsById(Long id, Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return collectionRepository.findCommentsById(id, pageable);
    }
  
    public Page<Collection> findByKeyword(String keyword, Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return collectionRepository.findByKeyword(pageable, keyword);
    }

    public Page<Collection> findMany(Optional<Integer> page, Optional<Integer> size) {
      var pageable = Pagination.of(page, size);
      return collectionRepository.findMany(pageable);
    }

    public Collection updateById(Long id, UpdateCollectionProps updateProps) {
      var collection = collectionRepository.updateById(id, updateProps)
          .orElseThrow(() -> InternalServerError(""));
      return collection;
    }

    public Collection create(CreateCollectionProps props) {
      return save(props);
    }

    public Comment updateByAddingComment(Long id, CreateCommentProps createProps) {
      var collection = collectionRepository.findById(id)
          .orElseThrow(() -> Responses.notFound("collection not found"));

      createProps.setCollection(collection);

      var newComment = new Comment(createProps);
      commentService.create(newComment);

      return newComment;
    }

    @Transactional
    public Image updateByAddingImage(Long id, CreateImageProps createProps) {
      var collection = collectionRepository.findById(id)
          .orElseThrow(() -> Responses.notFound("collection not found"));

      var image = imageService.uploadImage(createProps);
      image.setCollection(collection);

      collection.setImage(image); 
      collectionRepository.save(collection);

      return image;
    }

    // public boolean deleteOne(Long id) {
    //   var collection = findById(id);
    //   var comments = collectionRepository.findCommentsById(id, null);
    //   comments.getContent().forEach(cm -> cm.setCollection(null));
    //   collection.getComments().clear();
    //   collectionRepository.save(collection);
    //   return true;
    // }

    private Collection save(CreateCollectionProps props) {
      var collection = new Collection(props);
      collectionRepository.save(collection);
      return collection;
    }

}
