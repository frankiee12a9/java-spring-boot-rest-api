package com.github.frankiie.springboot.domain.collection_image.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.github.frankiie.springboot.domain.collection_image.entity.Image;
import com.github.frankiie.springboot.domain.collection_image.payload.CreateImageProps;
import com.github.frankiie.springboot.domain.collection_image.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);
  
    @Autowired private final ImageRepository imageRepository;
    @Autowired private final Cloudinary cloudinaryConfig;

    public Image uploadImage(CreateImageProps createProps) {
      try {
        var uploadResult = cloudinaryConfig.uploader().upload(createProps.getFile().getBytes(), ObjectUtils.emptyMap());
        Image image = new Image(uploadResult.get("url").toString());
        // imageRepository.save(image);
        return image;

      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
}