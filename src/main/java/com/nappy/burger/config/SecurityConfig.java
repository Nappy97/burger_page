package com.nappy.burger.config;

import com.nappy.burger.config.auth.PrincipalDetailService;
import com.nappy.burger.config.oauth.PrincipalOauth2UserService;
import com.nappy.burger.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalDetailService principalDetailService;
    private final PrincipalOauth2UserService principalOauth2UserService;

    @Autowired
    UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/images/**").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());


        http.formLogin()
                .loginPage("/auth/user/login")
                .failureUrl("/auth/user/login/fail")
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/auth/user/login")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

        http
                .rememberMe().tokenValiditySeconds(60 * 60 * 7)
                .userDetailsService(principalDetailService);
    }

    /**
     * 시큐리티 세션 빈으로 등록
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}