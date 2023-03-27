package com.github.frankiie.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import com.github.frankiie.springboot.beans.BeanConfiguration;
import com.github.frankiie.springboot.beans.BeanLister;
import com.github.frankiie.springboot.beans.MyBean;
import com.github.frankiie.springboot.configurations.DatabaseInitializer;

@EnableCaching
@SpringBootApplication
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInitializer.class);

    public static void main(String... args) throws Exception {
      // TEST BEAN
      ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanLister.class);
      BeanLister beanLister = ctx.getBean(BeanLister.class);
      beanLister.printAllBeans();

      ApplicationContext ctx2 = new AnnotationConfigApplicationContext(BeanConfiguration.class);
      MyBean myBean = ctx2.getBean(MyBean.class);
      LOGGER.info("> MY BEAN: " + myBean);
      myBean.afterPropertiesSet();
      myBean.destroy();

      SpringApplication.run(Application.class, args);

      ((AnnotationConfigApplicationContext) ctx).close();
    }

}
