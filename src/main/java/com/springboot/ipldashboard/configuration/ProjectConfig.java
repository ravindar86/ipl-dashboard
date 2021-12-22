package com.springboot.ipldashboard.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Value("#{'${cors.allowed.origins}'.split(',')}")
    private List<String> allowedOrigins;

    @Value("#{'${cors.allowed.methods}'.split(',')}")
    private List<String> allowedMethods;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();

        http.csrf().ignoringAntMatchers("/test/**");
        http.authorizeRequests().anyRequest().permitAll();

        http.cors(configurer -> {
            CorsConfigurationSource cs = r -> {
                CorsConfiguration cc = new CorsConfiguration();
                cc.setAllowedOrigins(allowedOrigins);
                cc.setAllowedMethods(allowedMethods);
                return cc;
            };
            configurer.configurationSource(cs);
        });
    }
}
