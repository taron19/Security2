package com.example.Security20.configs;

import com.example.Security20.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
@Slf4j
public class Deletecofig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("in the httpscectiry");
        http/*.csrf()
                .disable()*/
                .authorizeRequests()
                .antMatchers("/onlyadmin/**").hasRole("admin")
                .antMatchers("/onlyauth").hasAnyRole("user","admin")
                .antMatchers("/success").hasAuthority("author")
                .antMatchers("/all").authenticated()
                .and()
                .formLogin()
                .and()
                .logout();
    }

    /*@Override   1 СПОСОБ ЧЕРЕЗ БИЛДЕР
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("taron")
                .password("$2a$12$QlhwrN3qBpOOeArW9i9Tpe66Y9noN3EyS4ebXTrElmKexOlI.w1km")
                .roles(Role.ADMIN.getNanme())
                .and()
                .withUser("user")
                .password("$2a$12$QlhwrN3qBpOOeArW9i9Tpe66Y9noN3EyS4ebXTrElmKexOlI.w1km")
                .roles(Role.USER.getNanme())
                .and()
                .withUser("user3")
                .password("$2a$12$LtU7t5Ihv.6Rctq.l2H20O3aC4KAunDz9lSUKJDLnThhAObiF/fQu")
                .authorities("author");
    }*/



    /*@Bean  //1 СПОСОБ ЧЕРЕЗ UserDetailsService
    protected UserDetailsService userDetailsService() {
        log.info("in the userds");
        UserDetails taron = User.builder()
                .username("taron")
                .password("{bcrypt}$2a$12$QlhwrN3qBpOOeArW9i9Tpe66Y9noN3EyS4ebXTrElmKexOlI.w1km")
                .roles(Role.ADMIN.getNanme())
                .build();

        UserDetails user = User.builder()
                        .username("user")
                        .password("{bcrypt}$2a$12$QlhwrN3qBpOOeArW9i9Tpe66Y9noN3EyS4ebXTrElmKexOlI.w1km")
                        .roles(Role.USER.getNanme())
                        .build();
        UserDetails user3 = User.builder()
                .username("user3")
                .password("{bcrypt}$2a$12$QlhwrN3qBpOOeArW9i9Tpe66Y9noN3EyS4ebXTrElmKexOlI.w1km")
                .roles(Role.ACTION.getNanme())
                .build();

        return new InMemoryUserDetailsManager(taron,user,user3);
    }*/


/*    @Bean   //2 СПОСОБ ЧЕРЕЗ JdbcUserDetailsManager!
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
        log.info("in the userds");
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        UserDetails taron = User.builder()
                .username("taron")
                .password("{bcrypt}$2a$12$QlhwrN3qBpOOeArW9i9Tpe66Y9noN3EyS4ebXTrElmKexOlI.w1km")
                .roles(Role.ADMIN.getNanme())
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$12$QlhwrN3qBpOOeArW9i9Tpe66Y9noN3EyS4ebXTrElmKexOlI.w1km")
                .roles(Role.USER.getNanme())
                .build();
        UserDetails user3 = User.builder()
                .username("user3")
                .password("{bcrypt}$2a$12$QlhwrN3qBpOOeArW9i9Tpe66Y9noN3EyS4ebXTrElmKexOlI.w1km")
                .authorities("author")
                .build();


        if(jdbcUserDetailsManager.userExists(taron.getUsername())){
            jdbcUserDetailsManager.deleteUser(taron.getUsername());
        }
        if(jdbcUserDetailsManager.userExists(user.getUsername())){
            jdbcUserDetailsManager.deleteUser(user.getUsername());
        }
        if(jdbcUserDetailsManager.userExists(user3.getUsername())){
            jdbcUserDetailsManager.deleteUser(user3.getUsername());
        }

        jdbcUserDetailsManager.createUser(taron);
        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(user3);
        return jdbcUserDetailsManager;

    }*/


    // 3 СПОСОБ ЧЕРЕЗ ДАО
    //мы отдалу ему пользака логин и пароль и его задача сверить в бд и сказать есть ли такой пользак
    //а если есть то положи в спринг контекст
    //set.userdatailsservice предоставляет наших юзеров в бд
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);

        return authenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("in the encoder");
        return new BCryptPasswordEncoder();
    }

}




