package com.Borman.cbbbluechips.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new PlainTextPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("Setting up security");
        http.csrf().disable();


        http.authorizeRequests()
                .antMatchers("/", "/rules")
                .permitAll();

        http.authorizeRequests()
                .antMatchers("/portfolio", "/leaderboard")
                .hasAnyAuthority("USER");


        http.authorizeRequests().and().formLogin()//
                .loginPage("/")
                .loginProcessingUrl("/user/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/portfolio")
                .failureForwardUrl("/?error=true")
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/");
    }

}