package pl.kedziorek.medicalcentreapplication.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.kedziorek.medicalcentreapplication.service.impl.UserDetailsServiceImpl;

import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtUtils jwtUtils;

    private final UserDetailsServiceImpl userDetailsServiceimpl;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(jwtUtils, userDetailsServiceimpl);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/api/user/**").hasAuthority("ADMIN")
                .antMatchers("/api/role/**").hasAuthority("ADMIN")
                .antMatchers("/api/researchProject/**").hasAuthority("DOCTOR")
                .antMatchers("/api/permission/**").hasAuthority("DOCTOR")
                .antMatchers("/api/commission/save").hasAuthority("DOCTOR")
                .antMatchers("/api/commission/uuid={uuid}/delete").hasAuthority("DOCTOR")
                .antMatchers("/api/commission/uuid={uuid}/get").permitAll()
                .antMatchers("/api/result/saveOrUpdate").hasAuthority("DOCTOR")
                .antMatchers("/api/result/uuid={uuid}/delete").hasAuthority("DOCTOR")
                .antMatchers("/api/result/uuid={uuid}/get").permitAll()
                .and()
                .logout()
                .clearAuthentication(true)
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                .deleteCookies("token");
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200/"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "PUT"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Content-Type", "Access-Control-Allow-Origin"," Access-Control-Allow-Headers", "Authorization", "Set-Cookie"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}