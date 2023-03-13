package be.kdg.youth_council_project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Bean // allows you to create instances for autowiring for which you don't have source code
    // from framework point of view
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http//.httpBasic()
                // .and()
                .csrf().ignoringAntMatchers("/h2-console/**")
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeHttpRequests( // most important block
                        auths -> auths
                                .regexMatchers("/(youth-councils.*)") // permit all requests to these urls
                                .permitAll()
                                .antMatchers(HttpMethod.GET, "/api/**/*") // syntax by which you can specify nested paths generically, like regexes
                                // allows gets, not posts
                                .permitAll()
                                .antMatchers(HttpMethod.POST, "/api/**/*") // syntax by which you can specify nested paths generically, like regexes
                                // allows gets, not posts
                                .permitAll()
                                .antMatchers(HttpMethod.PATCH, "/api/**/*") // syntax by which you can specify nested paths generically, like regexes
                                // allows gets, not posts
                                .permitAll()
                                .antMatchers(HttpMethod.PATCH, "/api/**/*") // syntax by which you can specify nested paths generically, like regexes
                                // allows gets, not posts
                                .permitAll()
                                .antMatchers(HttpMethod.GET, "/javascript/**", "/css/**", "/webjars/**", "/favicon.ico","/images/**")
                                .permitAll()
                                .antMatchers("/", "/register")// allow access to homepages and registerpages
                                .permitAll()
                                .antMatchers("/h2-console/**")
                                .permitAll()
                                .anyRequest() // anything else requires authentication
                                .authenticated()) // posts will be caught here and given 403
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .permitAll();
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
