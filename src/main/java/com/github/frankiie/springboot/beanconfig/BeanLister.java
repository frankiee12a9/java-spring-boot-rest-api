package com.github.frankiie.springboot.beanconfig;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanLister {
  @Autowired private ApplicationContext appCtx;
 
  public void printAllBeans() {
    System.out.println("> GET BEAN DEFINITION NAMES: " + Arrays.asList(appCtx.getBeanDefinitionNames()));
  }

}
