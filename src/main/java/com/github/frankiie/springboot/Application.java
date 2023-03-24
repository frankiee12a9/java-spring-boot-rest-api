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
import com.github.frankiie.springboot.domain.role.entity.Role;
import com.github.frankiie.springboot.domain.role.repository.RoleRepository;
import com.github.frankiie.springboot.domain.user.entity.User;
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
      var userRole = roleRepository.findOptionalByInitials("USER");
      var admRole = roleRepository.findOptionalByInitials("ADMIN");
      
      if (!userRole.isPresent())  {
        var newR = new Role("USER");
        newR.setName("USER");
        newR.setDescription("Application user");
        LOGGER.info("> Preloading [userRole]: " + 
          roleRepository.save(newR)
        );
      }

      if (!admRole.isPresent())  {
        Role _admRole = new Role("ADMIN");
        _admRole.setName("ADM");
        _admRole.setDescription("Application admin, manage everything.");
        LOGGER.info("> Preloading [adminRole]: " + 
          roleRepository.save(_admRole)
        );
      }

      // User admin = new User("admin", "admin@gmail.com", "@kien12a99");
      userRepository.findByEmail("admin@gmail.com").ifPresent(a -> {
        // a.setRoles(List.of(admRole.get())); 
        // userRepository.save(a);
        LOGGER.info("Admin roles: " + a.getRoles());
      });
      userRepository.findByEmail("user@gmail.com").ifPresent(a -> {
        // a.setRoles(List.of(userRole.get())); 
        // userRepository.save(a);
        LOGGER.info("User roles: " + a.getRoles());
      });
      // if (!_admin.isPresent() && admRole.isPresent()) {
      //   admin.setRoles(List.of(admRole.get()));
      //   LOGGER.info("> Preloading [admin]: " + userRepository.save(admin));
      // } else {
      //   LOGGER.info("> FAILED Preloading [admin]: " + _admin);
      // }

      // User user = new User("user", "user@gmail.com", "@kien12a99");
      // if (!userRepository.findByEmail(user.getEmail()).isPresent() && userRole.isPresent()) {
      //   user.setRoles(List.of(userRole.get()));
      //   LOGGER.info("> Preloading: " + userRepository.save(user));
      // }
    }

}
