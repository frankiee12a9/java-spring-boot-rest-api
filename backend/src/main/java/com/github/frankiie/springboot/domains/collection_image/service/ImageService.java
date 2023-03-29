package com.github.frankiie.springboot.domains.collection_image.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

import com.github.frankiie.springboot.domains.collection.repository.CollectionRepository;
import com.github.frankiie.springboot.domains.collection_image.entity.Image;
import com.github.frankiie.springboot.domains.collection_image.payload.CreateImageProps;
import com.github.frankiie.springboot.domains.collection_image.repository.ImageRepository;
import com.github.frankiie.springboot.domains.collection_note.repository.NoteRepository;
import com.github.frankiie.springboot.utils.Responses;

@RequiredArgsConstructor
@Service
public class ImageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);
  
    @Autowired private final ImageRepository imageRepository;
    @Autowired private final CollectionRepository collectionRepository;
    @Autowired private final NoteRepository noteRepository;
    @Autowired private final Cloudinary cloudinaryConfig;

    public Image uploadImage(CreateImageProps createProps) {
      try {
        var uploadResult = cloudinaryConfig.uploader().upload(createProps.getFile().getBytes(), ObjectUtils.emptyMap());
        Image image = new Image(uploadResult.get("url").toString());
        return image;
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    public void save(Image image) {
      imageRepository.save(image);
    }

    public void deleteImageById(Long id) {
      imageRepository.deleteById(id);
    }

    public void deleteImageByCollectionId(Long collectionId) {
      collectionRepository.findById(collectionId)
                          .orElseThrow(() ->  Responses.notFound("associated collection not found"));

      imageRepository.deleteByCollectionId(collectionId);
    }

    public void deleteImageByNoteId(Long noteId) {
      noteRepository.findById(noteId)
                    .orElseThrow(() -> Responses.notFound("associated note not found"));

      imageRepository.deleteByNoteId(noteId);
    }
}