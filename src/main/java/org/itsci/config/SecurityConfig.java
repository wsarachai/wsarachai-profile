package org.itsci.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(new Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry>() {
            @Override
            public void customize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry configurer) {
                configurer.antMatchers("/home/**").authenticated()
                        .antMatchers("/member/**").hasRole("MEMBER")
                        .antMatchers("/system/**").hasRole("ADMIN");
            }
        });

        http.exceptionHandling(new Customizer<ExceptionHandlingConfigurer<HttpSecurity>>() {
            @Override
            public void customize(ExceptionHandlingConfigurer<HttpSecurity> configurer) {
                configurer.accessDeniedPage("/access-denied");
            }
        });

        http.formLogin(new Customizer<FormLoginConfigurer<HttpSecurity>>() {
                           @Override
                           public void customize(FormLoginConfigurer<HttpSecurity> configurer) {
                               try {
                                   configurer.loginPage("/login")
                                           .loginProcessingUrl("/authenticate")
                                           .permitAll()
                                           .and()
                                           .logout().permitAll();
                               } catch (Exception e) {
                                   e.printStackTrace();
                               }
                           }
                       }
        );
        return http.build();
    }
}
