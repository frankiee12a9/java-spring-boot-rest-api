package com.github.frankiie.springboot.domain.test_foo_bar;

import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domain.management.repository.SoftDeleteRepository;

@Repository
public interface BarRepository extends SoftDeleteRepository<Bar> {
}
