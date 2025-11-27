package org.itsci.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.http.HttpMethod;
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
        http.authorizeRequests(
                new Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry>() {
                    @Override
                    public void customize(
                            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry configurer) {
                        try {
                            // Allow anonymous POSTs to the attendance endpoint so mobile/QR clients can
                            // submit attendance. Also allow anonymous GETs for the attendance view page
                            // so images/details can be displayed without logging in.
                            configurer.antMatchers(HttpMethod.POST, "/pub/student/atten/doatten").permitAll();
                            configurer.antMatchers(HttpMethod.GET, "/pub/student/view/**").permitAll();
                            configurer.antMatchers("/home/**").authenticated()
                                    .antMatchers("/admin/**").hasRole("ADMIN")
                                    .antMatchers("/system/**").hasRole("ADMIN")
                                    .antMatchers("/api/**").hasAnyRole("ADMIN", "TEACHER")
                                    .antMatchers("/member/**").hasAnyRole("MEMBER", "STUDENT", "TEACHER", "ADMIN")
                                    .antMatchers("/pub/student/**").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                                    .antMatchers("/**").permitAll()
                                    .and().csrf()
                                    .ignoringAntMatchers("/api/**")
                                    .ignoringAntMatchers("/system/**")
                                    .ignoringAntMatchers("/pub/student/atten/doatten");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                } catch (Exception ignored) {

                }
            }
        });

        return http.build();
    }
}
