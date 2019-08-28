package io.github.jerrychin.shanghaibus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author Jerry Chin
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer {
    private static final  Logger log = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            log.info("run");
        };
    }

    /**
     * 注册全局跨域请求过滤器
     */
    @Bean
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        registration.addUrlPatterns("/*");
        registration.setName("CorsFilter");

        // 完全放开跨域请求
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.applyPermitDefaultValues();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        registration.setFilter(new CorsFilter(source));
        registration.setOrder(SecurityProperties.DEFAULT_FILTER_ORDER - 101);
        return registration;
    }

}
