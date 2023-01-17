package com.shadab.expensetrackerapi;

import com.shadab.expensetrackerapi.filters.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
// implies this is a configuration class and this triggers autoconfiguration and component scanning
public class ExpenseTrackerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerApiApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> filterFilterRegistrationBean(){
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        AuthFilter authFilter = new AuthFilter();
        registrationBean.setFilter(authFilter);
        registrationBean.addUrlPatterns("/api/categories/*");
        return registrationBean;
    }

}
