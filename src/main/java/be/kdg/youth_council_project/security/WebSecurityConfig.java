package be.kdg.youth_council_project.security;

import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import be.kdg.youth_council_project.tenants.TenantAuthorizationFilter;
import be.kdg.youth_council_project.tenants.TenantFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;
    private final YouthCouncilRepository youthCouncilRepository;

    public WebSecurityConfig(LoginSuccessHandler loginSuccessHandler, YouthCouncilRepository youthCouncilRepository) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.youthCouncilRepository = youthCouncilRepository;
    }

    @Bean // allows you to create instances for autowiring for which you don't have source code
    // from framework point of view
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**")
                .and()
                .addFilterBefore(new TenantFilter(youthCouncilRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new TenantAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests( // most important block
                        auths -> auths
                                .regexMatchers("/(ideas|action-points|activities|news-items|elections)?")  // permit
                                // all
                                // requests to these urls
                                .permitAll()
                                .antMatchers("/info-pages/**") // permit all requests to these urls
                                .permitAll()
                                .antMatchers("/**/*")
                                .permitAll()
                                .antMatchers(HttpMethod.GET, "/api/**/*") // syntax by which you can specify nested paths generically, like regexes
                                .permitAll()
                                .antMatchers(HttpMethod.POST, "/api/**/*") // syntax by which you can specify nested paths generically, like regexes
                                .authenticated()
                                .antMatchers(HttpMethod.PATCH, "/api/**/*") // syntax by which you can specify nested paths generically, like regexes
                                .authenticated()
                                .antMatchers(HttpMethod.PUT, "/api/**/*") // syntax by which you can specify nested
                                .authenticated()
                                .antMatchers(HttpMethod.DELETE, "/api/**/*") // syntax by which you can specify nested paths generically, like regexes
                                .authenticated()
                                .antMatchers(HttpMethod.GET, "/js/**", "/css/**", "/webjars/**", "/favicon.ico","/images/**")
                                .permitAll()
                                .antMatchers("/", "/register")// allow access to homepages and registerpages
                                .permitAll()
                                .antMatchers("/dashboard")// allow access to homepages and registerpages
                                .permitAll()
                                .antMatchers("/h2-console/**")
                                .permitAll()
                                .anyRequest() // anything else requires authentication
                                .authenticated()) // posts will be caught here and given 403
                .formLogin()
                .loginPage("/login").permitAll()
                .successHandler(loginSuccessHandler)
                .and()
                .logout()
                .permitAll();
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

}
