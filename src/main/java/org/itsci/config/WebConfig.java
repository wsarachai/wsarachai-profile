package org.itsci.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@EnableWebMvc
//@EnableAspectJAutoProxy
@ComponentScan(basePackages = "org.itsci")
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToAuthorityConverter());
        registry.addConverter(new StringToDateConverter());
        registry.addConverter(new DateToStringConverter());
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("/WEB-INF/assets/")
                .resourceChain(true)
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
    }

    @Bean
    public SpringResourceTemplateResolver springTemplateResolver(){
        SpringResourceTemplateResolver springTemplateResolver = new SpringResourceTemplateResolver();
        springTemplateResolver.setApplicationContext(this.applicationContext);
        springTemplateResolver.setPrefix("/WEB-INF/view/");
        springTemplateResolver.setSuffix(".html");
        return springTemplateResolver;
    }

    @Bean
    public SpringTemplateEngine springTemplateEngine(){
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(springTemplateResolver());
        return springTemplateEngine;
    }

    @Bean
    public ViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(springTemplateEngine());
        return viewResolver;
    }

//    @Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/view/");
//        viewResolver.setSuffix(".jsp");
//        return viewResolver;
//    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setFallbackToSystemLocale(true);
        messageSource.setCacheSeconds(3600);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
