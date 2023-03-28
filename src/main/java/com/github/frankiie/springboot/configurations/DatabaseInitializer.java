package com.github.frankiie.springboot.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.github.frankiie.springboot.domain.user.repository.UserRepository;

@Configuration
public class DatabaseInitializer implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Autowired private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
      LOGGER.info("FROM DatabaseInitializer.class");
      userRepository.findByEmail("admin@gmail.com").ifPresent(a -> {
        LOGGER.info("Admin roles: " + a.getRoles());
      });
      userRepository.findByEmail("user@gmail.com").ifPresent(a -> {
        LOGGER.info("User roles: " + a.getRoles());
      });
    }


    
}
