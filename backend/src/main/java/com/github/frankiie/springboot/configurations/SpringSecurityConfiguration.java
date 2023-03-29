package com.github.frankiie.springboot.configurations;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static com.github.frankiie.springboot.constants.SECURITY.ACESSO_NEGADO_URL;
import static com.github.frankiie.springboot.constants.SECURITY.DAY_MILLISECONDS;
import static com.github.frankiie.springboot.constants.SECURITY.ENCODER;
import static com.github.frankiie.springboot.constants.SECURITY.HOME_URL;
import static com.github.frankiie.springboot.constants.SECURITY.LOGIN_ERROR_URL;
import static com.github.frankiie.springboot.constants.SECURITY.LOGIN_URL;
import static com.github.frankiie.springboot.constants.SECURITY.LOGOUT_URL;
import static com.github.frankiie.springboot.constants.SECURITY.PASSWORD_PARAMETER;
import static com.github.frankiie.springboot.constants.SECURITY.PUBLICS;
import static com.github.frankiie.springboot.constants.SECURITY.SESSION_COOKIE_NAME;
import static com.github.frankiie.springboot.constants.SECURITY.TOKEN_SECRET;
import static com.github.frankiie.springboot.constants.SECURITY.USERNAME_PARAMETER;
import static com.github.frankiie.springboot.constants.SECURITY.JWT;
import static com.github.frankiie.springboot.utils.Responses.forbidden;
import static java.util.Optional.ofNullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

import com.github.frankiie.springboot.domains.session.service.SessionService;
import com.github.frankiie.springboot.middlewares.AuthorizationMiddleware;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfiguration {
    @Autowired private final SessionService sessionService;
    @Autowired private final AuthorizationMiddleware filter;

    public static String SWAGGER_USERNAME;
    public static String SWAGGER_PASSWORD;

    @Autowired
    protected void globalConfiguration(
        AuthenticationManagerBuilder authentication,
        @Value("${swagger.username}") String username,
        @Value("${swagger.password}") String password
    ) throws Exception {
        SpringSecurityConfiguration.SWAGGER_USERNAME = username;
        SpringSecurityConfiguration.SWAGGER_PASSWORD = password;

        if (Stream
            .of(ofNullable(SWAGGER_PASSWORD), ofNullable(SWAGGER_USERNAME))
            .allMatch(Optional::isPresent)) {

            authentication
                .inMemoryAuthentication()
                .passwordEncoder(ENCODER)
                .withUser(username)
                .password(ENCODER.encode(password))
                .authorities(List.of());
        }

        authentication
            .userDetailsService(sessionService)
            .passwordEncoder(ENCODER);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
    @Bean
    @Order(1)
    public SecurityFilterChain api(HttpSecurity http) throws Exception {
        PUBLICS.injectOn(http);

        http
            .antMatcher("/api/**")
              .authorizeRequests()
              .anyRequest()
              .authenticated()
            .and()
              .csrf().disable()
              .exceptionHandling()
              .authenticationEntryPoint((request, response, exception) -> forbidden(response))
            .and()                
              .sessionManagement()
              .sessionCreationPolicy(STATELESS)
            .and()
              .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
            .cors()
            .configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain app(HttpSecurity http) throws Exception {
        http
            .antMatcher("/app/**")
                .authorizeRequests()
                .antMatchers(GET, LOGIN_URL, "/app", "/app/register", "/app/recovery/**")
                .permitAll()
                .antMatchers(POST, "/app/register", "/app/recovery/**")
                .permitAll()
                .anyRequest()
                .hasAuthority("USER")
            .and()
                .csrf().disable()                    
                .formLogin().loginPage(LOGIN_URL)
                .failureUrl(LOGIN_ERROR_URL)
                .defaultSuccessUrl(HOME_URL)
                .usernameParameter(USERNAME_PARAMETER)
                .passwordParameter(PASSWORD_PARAMETER)
            .and()
                .rememberMe()                    
                .key(TOKEN_SECRET)
                .tokenValiditySeconds(DAY_MILLISECONDS)
            .and()
                .logout()
                .deleteCookies(SESSION_COOKIE_NAME)
                .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL))
                .logoutSuccessUrl(LOGIN_URL)
            .and()
                .exceptionHandling()
                .accessDeniedPage(ACESSO_NEGADO_URL);

        return http.build();
    }

    @Bean
    @Order(4)
    public SecurityFilterChain swagger(HttpSecurity http) throws Exception {
        if (Stream
                .of(ofNullable(SWAGGER_PASSWORD), ofNullable(SWAGGER_USERNAME))
                .allMatch(Optional::isPresent))  {
            http
              .antMatcher("/swagger-ui/**")
              .authorizeRequests()
              .anyRequest()
              .authenticated()
            .and()
              .sessionManagement()
              .sessionCreationPolicy(STATELESS)
            .and()
              .httpBasic();
        }

        return http.build();
    }
}