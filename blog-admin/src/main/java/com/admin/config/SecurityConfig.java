package com.admin.config;

import com.admin.service.JwtAuthenticationFailureHandler;
import com.admin.service.JwtAuthenticationFilter;
import com.admin.service.JwtAuthenticationSuccessHandler;
import com.admin.service.impl.UserDetailsServiceimpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    UserDetailsServiceimpl userDetailsService;
    @Resource
    JwtAuthenticationFailureHandler authenticationFailureHandler;
    @Resource
    JwtAuthenticationSuccessHandler authenticationSuccessHandler;
    @Resource
    AuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    AccessDeniedHandler accessDeniedHandler;
    @Resource
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public BCryptPasswordEncoder getPassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPassword());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()                                                               // 关闭csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)     // 指定session的创建策略，不使用session
                .and()                                                                          // 再次获取到HttpSecurity对象
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()// 进行认证请求的配置
                .antMatchers("/admin/login").anonymous()                                       // 对于登录接口，允许匿名访问
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/admin/login")
                .failureHandler(authenticationFailureHandler)
                .successHandler(authenticationSuccessHandler);
        http.httpBasic();
        //将自定义过滤器添加到springsecurity过滤链中
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //添加认证失败处理器和授权失败处理器
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler);


    }


}