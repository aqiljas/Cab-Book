package com.mega.citycab.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.mega.citycab.models.AdminMyAppUserService;
import com.mega.citycab.models.MyAppUserService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    // (DI) Pattern (@Autowired)
    @Autowired
    private final MyAppUserService appUserService;  // Regular user service

    @Autowired
    private final AdminMyAppUserService adminUserService;  // Admin service

    // UserDetailsService for regular users
    @Bean
    UserDetailsService userDetailsService() {
        return appUserService;
    }

    // UserDetailsService for admins
    @Bean
    UserDetailsService adminUserDetailsService() {
        return adminUserService;
    }




    // Strategy Pattern (AuthenticationProvider)
    @Bean
    AuthenticationProvider userAuthenticationProvider() {
        // DaoAuthenticationProvider  used as a strategy for authentication
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // AuthenticationProvider for admins
    @Bean
    AuthenticationProvider adminAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminUserDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    // Password encoder
    // Singleton Pattern (bean methods passwordEncoder)
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Security filter chain for admin
    // Template Method Pattern
    @Bean
    @Order(1)
    SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
             // If type any of this path it goes to the login page
            .securityMatcher("/admin/**", "/adminregisterandlogin/**", "/adminregisterandlogin/adminlogin/**", 
             "/adminregisterandlogin/adminsignup/**", "/customerfeedback/adminviewfeedback/**",
              "/customerinterfacebookacab/admineditbookcab/**",  "/customerinterfacebookacab/adminviewbookcab/**",
               "/customerfeedback/adminviewfeedback/**", "/adminadd/googlemaps/**", "/adminlogin") // Apply to admin URLs
            .authenticationProvider(adminAuthenticationProvider()) // Use admin auth provider
            // Builder Pattern
            .formLogin(httpForm -> {
                httpForm.loginPage("/adminlogin").permitAll();
                httpForm.defaultSuccessUrl("/admin", true);
            })
            .authorizeHttpRequests(registry -> {
                registry.requestMatchers("/admin/**").authenticated();
                registry.requestMatchers("/req/adminsignup", "/css/**", "/js/**", "/images/**").permitAll();
                registry.anyRequest().authenticated();
            })
            .build();
    }

    // Security filter chain for users
    @Bean
    @Order(2)
    SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
             // If type any of this path it goes to the login page
            .securityMatcher("/index/**", "/customerinterfacebookacab/about/**", 
            "/customerinterfacebookacab/vehicles/**", "/customerinterfacebookacab/contact/**", "/customerfeedback/customeraddfeedback/**",
             "/customerinterfacebookacab/customerbookcab/**", "/customerinterfacebookacab/editbookcab/**", "/customerinterfacebookacab/viewbookcab/**", 
            "/customerpayment/customermakepayment/**", "/customerpayment/**", "/login") // Apply to user URLs
            .authenticationProvider(userAuthenticationProvider()) // Use user auth provider
            .formLogin(httpForm -> {
                httpForm.loginPage("/login").permitAll();
                httpForm.defaultSuccessUrl("/index", true);
            })
            .authorizeHttpRequests(registry -> {
                registry.requestMatchers("/index/**").authenticated();
                registry.requestMatchers("/req/signup", "/css/**", "/js/**", "/images/**").permitAll();
                registry.anyRequest().authenticated();
            })
            .build();
    }

}
