package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.springsecurity.JWTAuthorizationFilter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    // TODO
    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    // TODO
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // désactivation de la protection CSRF
                // non utilisée dans le cadre d'une Web API
                .csrf().disable().authorizeRequests()
                // un GET /exemples n'est pas soumise à authentification
                .antMatchers(HttpMethod.GET, "/exemples").permitAll()// TODO
                // accès à la console h2 sans authentification
                .antMatchers("/h2-console/**").permitAll().antMatchers(HttpMethod.POST, "/auth").permitAll()
                // TODO
                .antMatchers("/admin").hasRole("ADMIN")

                // Les autres requêtes sont soumises à authentification
                .anyRequest().authenticated()

                // TODO
                // accès à la console h2 sans authentification
                .and().headers().frameOptions().disable()
                // TODO
                .and().addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)

                // TODO
                // Gestion de la déconnexion
                // /POST /logout
                .logout()
                // en cas de succès un OK est envoyé (à la place d'une redirection vers /login)
                .logoutSuccessHandler((req, resp, auth) -> resp.setStatus(HttpStatus.OK.value()))
                // suppression du cookie d'authentification
                .deleteCookies(TOKEN_COOKIE); // Gestion de la déconnexion
    }
}
