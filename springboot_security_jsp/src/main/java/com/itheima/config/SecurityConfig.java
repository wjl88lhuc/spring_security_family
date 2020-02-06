package com.itheima.config;

import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //认证用户的来源，用户可以是内存，可以是来自数据库记录的
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    //配置springsecurity相关信息
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //释放静态资源，指定自定义认证页面，指定退出认证配置，csrf配置
                .antMatchers("/login.jsp", "/failer.jsp", "/css/**", "/img/**",
                        "/plugins/**").permitAll()
                //指定资源拦截规则   anyRequest() 与 authenticated() 联合使用表示 除了permitAll() 指定的资源之外都需要先认证成功才能访问
                .antMatchers("/**").hasAnyRole("USER", "ADMIN")
                .anyRequest()
                .authenticated()
                .and() //  and() 表示又开启一个新的配置
                .formLogin()
                .loginPage("/login.jsp")  //指定认证页面
                .loginProcessingUrl("/login")  //spring-security指定的认证处理器的URL
                .successForwardUrl("/index.jsp")  //指定认证成功前往的页面
                .failureForwardUrl("/failer.jsp")  //指定认证失败前往的页面
                .permitAll()
                .and()
                .logout()  // 指定退出认证
                .logoutUrl("/logout")  //指定退出的处理（由spring-security指定）
                .invalidateHttpSession(true)  //指定退出之后是否清空session
                .logoutSuccessUrl("/login.jsp")//指定退出之后前往的页面
                .permitAll()
                .and()
                .csrf()
                .disable(); // 为了测试方便，禁用 csrf,实际开发中是必须开启这个的
    }
}
