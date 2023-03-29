package com.github.frankiie.springboot.domains.test_foo_bar;

import lombok.Data;

import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.of;

import static com.github.frankiie.springboot.utils.JSON.stringify;

@Data
public class CreateFoo {
    private Long barId;
    
    private String name;

    @Override
    public String toString() {
        return stringify(this);
    }
}

