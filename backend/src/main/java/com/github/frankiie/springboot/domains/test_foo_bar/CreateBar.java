package com.github.frankiie.springboot.domains.test_foo_bar;

import lombok.Data;

import static com.github.frankiie.springboot.utils.JSON.stringify;

@Data
public class CreateBar {
    private Long fooId;
    
    private String name;

    @Override
    public String toString() {
        return stringify(this);
    }
}

