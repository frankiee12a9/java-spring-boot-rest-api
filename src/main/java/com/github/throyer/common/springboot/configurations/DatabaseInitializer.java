package com.github.throyer.common.springboot.configurations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.github.throyer.common.springboot.domain.role.entity.Role;
import com.github.throyer.common.springboot.domain.role.repository.RoleRepository;
import com.github.throyer.common.springboot.domain.user.entity.User;
import com.github.throyer.common.springboot.domain.user.repository.UserRepository;

// @Component
@Configuration
public class DatabaseInitializer {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInitializer.class);

  CommandLineRunner initFakeData(RoleRepository roleRepository, UserRepository userRepository) {
    return args -> {
      // LOGGER.info("Preloading " + repository.save(new Note("new note 1", "new note 1 description")));
      // User user = new User("test", "test@gmail.com", "kien12a99", List.of("ADM", "USER"));
      Role adm = new Role("ADM"),
      usr = new Role("USER");
      
      LOGGER.info("Preloading >>>>>>>>>>> " + roleRepository.save(adm));
      User user = new User("test", "test@gmail.com", "kien12a99", List.of(adm, usr));
      LOGGER.info("Preloading >>>>>>>>>>> " + userRepository.save(user));
        // return new User(name, email, password, List.of());
    };
  }
}
