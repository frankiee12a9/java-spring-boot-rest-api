package com.github.frankiie.springboot.domains.test_foo_bar;

import org.springframework.stereotype.Repository;

import com.github.frankiie.springboot.domains.management.repository.SoftDeleteRepository;

@Repository
public interface FooRepository extends SoftDeleteRepository<Foo> {
}
