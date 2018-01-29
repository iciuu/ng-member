package com.ng.member.config.web.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author niuguang
 * @date 18-1-17
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtLoginFilter(authenticationManager()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager()));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/swagger-ui.html")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/v2/**")
                .antMatchers("/webjars/**")
                .antMatchers("/favicon.ico")
                .antMatchers("/member/login")
                .antMatchers("/login/**")
                .antMatchers("/member/queryUserGroupByUserId")
                .antMatchers("/member/queryUserToGroup")
                .antMatchers("/member/queryUserListByGroupId")
                .antMatchers("/member/register")
                .antMatchers("/member/queryUserInfo")
                .antMatchers("/member/queryUserInfoList");
    }
}
