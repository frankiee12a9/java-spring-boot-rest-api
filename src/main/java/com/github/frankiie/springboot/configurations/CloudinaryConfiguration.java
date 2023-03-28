package com.github.frankiie.springboot.configurations;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfiguration {

    @Bean
    public Cloudinary cloudinary() {
      return new Cloudinary(ObjectUtils.asMap(
        "cloud_name", "your cloud name",
        "api_key", "your_api_key",
        "api_secret", "api_secret"
      ));
    }
}
