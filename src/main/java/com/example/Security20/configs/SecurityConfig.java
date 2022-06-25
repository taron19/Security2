package com.example.Security20.configs;

import com.example.Security20.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@EnableWebSecurity
@Slf4j
public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/ {


   /* private UserService userService;

    @Autowired
    SecurityConfig(UserService userService){
        this.userService = userService;
    }*/

    /**
     * куда пользователь имеет доступ
     * @param http
     * @throws Exception
     */
    //@Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("in the configure");
     http.authorizeRequests()//ко всему имеют доступ наши пользаки. кроме /auth/** там только с аутен-ией!
             .antMatchers("/auth/**").authenticated() //в путь /auth/** пускаем только аутен-ых
             /*.antMatchers("/admin/**").hasRole(ROLE.ADMIN) в админ хэндрел пускаем только с ролью админ*/
             .antMatchers("/foradmin/**").hasRole("admin")
             .antMatchers("/readprofile/**").hasAuthority("read_profile")
             .and()
             .formLogin()//чтобы была форма логина,также можем указать урл где будет регистрация иои вход
             .and()
             .logout().logoutSuccessUrl("/");// хотим чтобы после логаута перенапрвляло нас в корневую страницу

    }

    /*@Bean 1 способо
    public UserDetailsService userDetailsService(){
        log.info("in the userdetailservice method");
        UserDetails user = User  //userDeatils мин инфа о пользаках,либо этой инфы достаточно либо мы будет приводить к этому виду
                .builder()
                .username("user")
                .password("{bcrypt}$2a$12$JdA1lN0I0IrQbSR0OcZEIeFY7I4gGgP5a5rsgXRjqfMUNhH8B/tyq")//пароль храним в bcrypt password-100
                .roles("user")
                .build();

        UserDetails admin = User
                .builder()
                .username("admin")
                .password("{bcrypt}$2a$12$JdA1lN0I0IrQbSR0OcZEIeFY7I4gGgP5a5rsgXRjqfMUNhH8B/tyq")
                .roles("user","admin")
                .build();
    return new InMemoryUserDetailsManager(user,admin);
    }*/


    /**
     * Аутентификация через БД
     * @param
     * @return
     */
   /* @Bean("JdbcUserDetailsManager")
    public JdbcUserDetailsManager userDetailsService(DataSource dataSource){
        log.info("in the JdbcUserDetailsManager");
    //2 способа работы:
        // 1)берет готовых пользаков
        // 2)при подключении попросить спринг заранее положить юзеров
        UserDetails user = User  // мы делаем 2 способ
                .builder()
                .username("user")
                .password("{bcrypt}$2a$12$JdA1lN0I0IrQbSR0OcZEIeFY7I4gGgP5a5rsgXRjqfMUNhH8B/tyq")//пароль храним в bcrypt password-100
                .roles("user")
                .build();

        UserDetails admin = User
                .builder()
                .username("admin")
                .password("{bcrypt}$2a$12$JdA1lN0I0IrQbSR0OcZEIeFY7I4gGgP5a5rsgXRjqfMUNhH8B/tyq")
                .roles("user","admin")
                .build();


        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        if(jdbcUserDetailsManager.userExists(user.getUsername())){
            jdbcUserDetailsManager.deleteUser(user.getUsername());
        }

        if(jdbcUserDetailsManager.userExists(admin.getUsername())){
            jdbcUserDetailsManager.deleteUser(admin.getUsername());
        }


        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(admin);
        //мы сами создали юзеров хотя моглы бы их создать в базе

    return jdbcUserDetailsManager;
    }*/




    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    /**
     * 3 способ аутентификации создаем собственные таблицы(сущности итд) которыми можем управлять
     * задача ДАО у UserDetailsService(источник userdetails по юзерам) запросить юзера взять токен и сравнить совпадают
     * ли эти данные совпадают или нет если да то кладем в контекст!
     * @return
     */
    /*@Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }*/
}
