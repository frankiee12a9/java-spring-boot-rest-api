package com.github.frankiie.springboot.domain.collection.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.frankiie.springboot.domain.collection.entity.Collection;
import com.github.frankiie.springboot.domain.collection.payload.UpdateCollectionProps;
import com.github.frankiie.springboot.domain.collection.repository.CollectionRepository;
import com.github.frankiie.springboot.domain.collection_image.entity.Image;
import com.github.frankiie.springboot.domain.collection_image.payload.CreateImageProps;
import com.github.frankiie.springboot.domain.collection_image.service.ImageService;
import com.github.frankiie.springboot.domain.collection_note_comment.entity.Comment;
import com.github.frankiie.springboot.domain.collection_note_comment.payload.CreateCommentProps;
import com.github.frankiie.springboot.domain.collection_note_comment.service.CommentService;
import com.github.frankiie.springboot.utils.Responses;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UpdateCollectionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateCollectionService.class);

    @Autowired private final CollectionRepository collectionRepository;

    @Autowired private final ImageService imageService;
    @Autowired private final CommentService commentService;

    public Collection updateById(Long id, UpdateCollectionProps updateProps) {
      var collection = collectionRepository.updateById(id, updateProps)
          .orElseThrow(() -> Responses.notFound("associated collection not found"));
      return collection;
    }

    public Comment updateByAddingComment(Long id, CreateCommentProps createProps) {
      var collection = collectionRepository.findById(id)
          .orElseThrow(() -> Responses.notFound("associated collection not found"));

      createProps.setCollection(collection);

      var comment = new Comment(createProps);
      commentService.create(comment);
      return comment;
    }

    @Transactional
    public Image updateByAddingImage(Long id, CreateImageProps createProps) {
      var collection = collectionRepository.findById(id)
          .orElseThrow(() -> Responses.notFound("associated collection not found"));

      var image = imageService.uploadImage(createProps);
      image.setCollection(collection);

      collection.setImage(image); 
      collectionRepository.save(collection);

      return image;
    }
}
