package com.project.ecommerce.user.config;

import com.project.ecommerce.user.config.filter.JwtTokenValidator;
import com.project.ecommerce.user.service.impl.UserImpl;
import com.project.ecommerce.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    //Configurar endpoints publico
                    http.requestMatchers(HttpMethod.POST, "/user/auth/**").permitAll();

                    //Configurar endpoints de usuario
                    http.requestMatchers(HttpMethod.GET, "/user/**").hasAuthority("READ");
                    http.requestMatchers(HttpMethod.PUT, "/user/**").hasAuthority("UPDATE");
                    http.requestMatchers(HttpMethod.DELETE, "/user/**").hasRole("ADMIN");

                    //Configurar endpoints de producto
                    http.requestMatchers(HttpMethod.GET, "/product/**").hasAuthority("READ");
                    http.requestMatchers(HttpMethod.POST, "/product/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/product/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/product/**").hasRole("ADMIN");

                    //Configurar endpoints de wishlist
                    http.requestMatchers(HttpMethod.GET, "/wishlist/**").hasAuthority("READ");
                    http.requestMatchers(HttpMethod.POST, "/wishlist/**").hasAuthority("CREATE");
                    http.requestMatchers(HttpMethod.PUT, "/wishlist/**").hasAuthority("UPDATE");
                    http.requestMatchers(HttpMethod.DELETE, "/wishlist/**").hasRole("ADMIN");

                    //Configurar endpoints de checkout
                    http.requestMatchers(HttpMethod.GET, "/checkout/**").hasAuthority("READ");
                    http.requestMatchers(HttpMethod.POST, "/checkout/**").hasAuthority("CREATE");
                    http.requestMatchers(HttpMethod.PUT, "/checkout/**").hasAuthority("UPDATE");
                    http.requestMatchers(HttpMethod.DELETE, "/checkout/**").hasRole("ADMIN");

                    //Configurar endpoints de category
                    http.requestMatchers(HttpMethod.GET, "/category/**").hasAuthority("READ");
                    http.requestMatchers(HttpMethod.POST, "/category/**").hasAuthority("CREATE");
                    http.requestMatchers(HttpMethod.PUT, "/category/**").hasAuthority("UPDATE");
                    http.requestMatchers(HttpMethod.DELETE, "/category/**").hasRole("ADMIN");

                    //Configurar endpoints de payment
                    http.requestMatchers(HttpMethod.GET, "/payment/**").hasAuthority("READ");
                    http.requestMatchers(HttpMethod.POST, "/payment/**").hasAuthority("CREATE");
                    http.requestMatchers(HttpMethod.PUT, "/payment/**").hasAuthority("UPDATE");
                    http.requestMatchers(HttpMethod.DELETE, "/payment/**").hasRole("ADMIN");

                    //Configurar endpoints no especificados
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


