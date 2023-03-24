package com.github.frankiie.springboot.beanconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

  @Bean
  public MyBean myBeans() {
    return new MyBean();
  }
}
