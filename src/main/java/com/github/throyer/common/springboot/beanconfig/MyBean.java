package com.github.throyer.common.springboot.beanconfig;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class MyBean implements InitializingBean, DisposableBean {

  public MyBean() {
    System.out.print("MyBeans constructor");
  }

  @Override
  public void destroy() throws Exception {
    System.out.println("MyBeans: destroy");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("MyBeans: afterPropertiesSet");
  }
  
}
