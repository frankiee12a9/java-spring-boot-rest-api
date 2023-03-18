package com.github.throyer.common.springboot.domain.management.entity;

// => ABOUT record
// They are final and immutable.
// They can implement interfaces.
// They can have static members.
// They can define validations.
// They can define default values.
// They accept generics.

public record UserRequestInfo (String greeting, String username, String ipAddress,  String field0, String field1, String field2) {
}
