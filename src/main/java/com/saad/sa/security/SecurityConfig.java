//package com.saad.sa.security;
//
//import java.util.List;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
//		security.cors().and().csrf().disable().authorizeHttpRequests().requestMatchers("/api/auth/**").permitAll()
//				.anyRequest().authenticated().and().oauth2ResourceServer().jwt();
//
//		return security.build();
//	}
//
//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowedOrigins(List.of("*"));
//		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//		config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
//		config.setAllowCredentials(true); // optional
//
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", config);
//
//		return source;
//	}
//
//}
