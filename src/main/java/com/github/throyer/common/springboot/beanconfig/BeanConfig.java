package com.github.throyer.common.springboot.beanconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

  @Bean
  public MyBean myBeans() {
    return new MyBean();
  }
}
