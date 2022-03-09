package com.example.crowdfunding.config;

import com.example.crowdfunding.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    private UserRepository userRepository;
    RestAuthEntryPoint restAuthEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic();
        http.cors();

        http
            .authorizeRequests().antMatchers(HttpMethod.POST).authenticated().and()
            .authorizeRequests().antMatchers(HttpMethod.GET).authenticated().and()
            .authorizeRequests().antMatchers(HttpMethod.PUT).authenticated().and()
            .authorizeRequests().antMatchers(HttpMethod.DELETE).permitAll()
            .and()
                .authorizeRequests().antMatchers("/api/v1.0/users/newuser").permitAll()
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthEntryPoint)
            .and()
                .formLogin().loginProcessingUrl("/api/v1.0/users/{id}")
            .and()
                .logout();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoderTest();
    }
}
