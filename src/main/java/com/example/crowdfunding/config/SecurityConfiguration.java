package com.example.crowdfunding.config;

import com.example.crowdfunding.config.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic();
        http.cors().configurationSource(corsConfigurationSource());

        http
            .authorizeRequests().antMatchers(HttpMethod.POST).authenticated().and()
            .authorizeRequests().antMatchers(HttpMethod.GET).authenticated().and()
            .authorizeRequests().antMatchers(HttpMethod.PUT).authenticated().and()
            .authorizeRequests().antMatchers(HttpMethod.DELETE).authenticated().and()
                .exceptionHandling()
            .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests().antMatchers("/api/v1.0/users/login").anonymous().and()
                .authorizeRequests().antMatchers("api/v1.0/projects").anonymous().and()
                .authorizeRequests().antMatchers("/api/v1.0/businesses").anonymous().and()
                .authorizeRequests().antMatchers("/api/v1.0/users/{id}").anonymous().and()
                .authorizeRequests().antMatchers("/api/v1.0/payments").anonymous().and()
                .authorizeRequests().antMatchers("/api/v1.0/payments/create-payment-intent").anonymous().and()
                .authorizeRequests().antMatchers("/api/v1.0/payments/{projectId}&{amount}&{userId}").anonymous()
        ;

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(corsFilter(), SessionManagementFilter.class);

    }



    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/v1.0/users/newuser");
//        web.ignoring().antMatchers("/api/v1.0/users/login");
        web.ignoring().antMatchers("/api/v1.0/users/{id}");
        web.ignoring().antMatchers("/api/v1.0/businesses").antMatchers(HttpMethod.GET);
        web.ignoring().antMatchers("/api/v1.0/projects").antMatchers(HttpMethod.GET);
        web.ignoring().antMatchers("/api/v1.0/payments/**");
        web.ignoring().antMatchers("/api/v1.0/payments/create-payment-intent");
        web.ignoring().antMatchers("/swagger-ui/**}");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoderTest();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    CorsFilter corsFilter() {
        CorsFilter corsFilter = new CorsFilter();
        return corsFilter;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_STAFF \n ROLE_STAFF > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }
}
