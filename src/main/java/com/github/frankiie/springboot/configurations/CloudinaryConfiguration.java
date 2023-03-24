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
        "cloud_name", "dmnhrwy2l",
        "api_key", "399475872756772",
        "api_secret", "hAgvlUB6oSaGiYOafw8pskavDB4"
      ));
    }
}
