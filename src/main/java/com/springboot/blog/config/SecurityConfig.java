package com.springboot.blog.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	  @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf().disable()
	            .authorizeHttpRequests()
	            .anyRequest()
	            .authenticated()
	            .and()
	            .httpBasic();
			return http.build();

	  }
}

