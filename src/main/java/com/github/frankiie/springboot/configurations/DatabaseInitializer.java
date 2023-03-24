package com.github.frankiie.springboot.configurations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.github.frankiie.springboot.domain.role.entity.Role;
import com.github.frankiie.springboot.domain.role.repository.RoleRepository;
import com.github.frankiie.springboot.domain.user.entity.User;
import com.github.frankiie.springboot.domain.user.repository.UserRepository;

@Configuration
public class DatabaseInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInitializer.class);

    CommandLineRunner initFakeData(RoleRepository roleRepository, UserRepository userRepository) {
      return args -> {
        Role adm = new Role("ADM"),
        usr = new Role("USER");
        
        LOGGER.info("Preloading: " + roleRepository.save(adm));
        User user = new User("test", "test@gmail.com", "kien12a99", List.of(adm, usr));

        LOGGER.info("Preloading: " + userRepository.save(user));
      };
    }

}
