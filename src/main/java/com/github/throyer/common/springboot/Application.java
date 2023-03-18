package com.github.throyer.common.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github.throyer.common.springboot.beanconfig.BeanConfig;
import com.github.throyer.common.springboot.beanconfig.BeanLister;
import com.github.throyer.common.springboot.beanconfig.MyBean;
import com.github.throyer.common.springboot.configurations.DatabaseInitializer;
import com.github.throyer.common.springboot.domain.role.repository.RoleRepository;
import com.github.throyer.common.springboot.domain.user.entity.User;
import com.github.throyer.common.springboot.domain.user.repository.UserRepository;

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
    ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanLister.class);
    BeanLister beanLister = ctx.getBean(BeanLister.class);
    beanLister.printAllBeans();

    ApplicationContext ctx2 = new AnnotationConfigApplicationContext(BeanConfig.class);
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
    var usrRole = roleRepository.findOptionalByInitials("USER").get();
    var admRole = roleRepository.findOptionalByInitials("ADM").get();
    User user = new User("frankiie", "frankiie@gmail.com", "@kien12a99", List.of(usrRole, admRole));

    if (userRepository.findByEmail(user.getEmail()) != null) 
      return;

    LOGGER.info("> Preloading: " + userRepository.save(user));
  }

}
