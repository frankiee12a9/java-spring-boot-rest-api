package com.github.throyer.common.springboot.beanconfig;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanLister {

  @Autowired
  private ApplicationContext appCtx;
 
  public void printAllBeans() {
    System.out.println("> GET BEAN DEFINITION NAMES: ");
    // System.out.println(Arrays.asList(appCtx.getBeanDefinitionNames()));

    String[] beanDefinitionNames = appCtx.getBeanDefinitionNames();
    for (var bean : beanDefinitionNames) {
      System.out.print(bean);
    }
  }
}
