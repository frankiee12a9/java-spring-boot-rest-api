package com.github.frankiie.springboot.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class MyBean implements InitializingBean, DisposableBean {
    public MyBean() {
      System.out.print("MyBean initialized");
    }

    @Override public void destroy() throws Exception {
      System.out.println("MyBean: destroy");
    }

    @Override public void afterPropertiesSet() throws Exception {
      System.out.println("MyBean: afterPropertiesSet");
    }
  
}
