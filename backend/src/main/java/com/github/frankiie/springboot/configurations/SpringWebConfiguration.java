package com.github.frankiie.springboot.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(
    title = "A simple CRUD API",
    version = "v4.0.1",
    description = """
      A simple REST API for **Bookmark** application with some domains such 
        `User`, CRUD API, and more (check out the below `User management`) ,
        `Note` CRUD API, User can perform C.R.U.D and more operations on a note (checkout the below `Note management`)
        `Collection` CRUD API (checkout the below `Collection management`),
        `Image` CRUD API (checkout the below `Image management`), 
        `Comment` CRUD API (checkout the below `Comment management`),
        using the RESTful API pattern.
    """,
    license = @License(
        name = "GNU General Public License v3.0",
        url = ""
    )
    ,
    contact = @Contact(
        name = "",
        email = "",
        url = ""
    )
))
@SecurityScheme(
    name = "token",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER,
    scheme = "bearer"
)
public class SpringWebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

}