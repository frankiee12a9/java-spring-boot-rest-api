package com.github.frankiie.springboot.domain.collection_image.repository;

import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domain.collection_image.entity.Image;
import com.github.frankiie.springboot.domain.management.repository.SoftDeleteRepository;

@Repository
public interface ImageRepository extends SoftDeleteRepository<Image> {
}
