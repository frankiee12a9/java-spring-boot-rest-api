package com.github.frankiie.springboot.domains.collection.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.frankiie.springboot.domains.collection.entity.Collection;
import com.github.frankiie.springboot.domains.collection.payload.CreateCollectionProps;
import com.github.frankiie.springboot.domains.collection.repository.CollectionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RemoveCollectionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveCollectionService.class);

    @Autowired private final CollectionRepository collectionRepository;

    // todo
}
