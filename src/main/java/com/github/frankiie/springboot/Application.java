package com.github.frankiie.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github.frankiie.springboot.beanconfig.BeanConfiguration;
import com.github.frankiie.springboot.beanconfig.BeanLister;
import com.github.frankiie.springboot.beanconfig.MyBean;
import com.github.frankiie.springboot.configurations.DatabaseInitializer;
import com.github.frankiie.springboot.domain.role.repository.RoleRepository;
import com.github.frankiie.springboot.domain.user.repository.UserRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

@EnableCaching
@SpringBootApplication
@EntityScan(basePackageClasses = { Application.class})
public class Application implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInitializer.class);

    public static void main(String... args) throws Exception {
      // TEST BEAN
      ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanLister.class);
      BeanLister beanLister = ctx.getBean(BeanLister.class);
      beanLister.printAllBeans();

      ApplicationContext ctx2 = new AnnotationConfigApplicationContext(BeanConfiguration.class);
      MyBean myBean = ctx2.getBean(MyBean.class);
      LOGGER.info("> MY_BEAN: " + myBean);
      myBean.afterPropertiesSet();
      myBean.destroy();

      SpringApplication.run(Application.class, args);

      ((AnnotationConfigApplicationContext) ctx).close();
    }
  
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
      userRepository.findByEmail("admin@gmail.com").ifPresent(a -> {
        LOGGER.info("Admin roles: " + a.getRoles());
      });
      userRepository.findByEmail("user@gmail.com").ifPresent(a -> {
        LOGGER.info("User roles: " + a.getRoles());
      });
    }

}
