package mariana.thePotteryPlace.security;

import lombok.SneakyThrows;
import mariana.thePotteryPlace.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration
public class WebSecurity {
    private final AuthService authService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public WebSecurity(AuthService authService, AuthenticationEntryPoint authenticationEntryPoint) {
        this.authService = authService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(final HttpSecurity http) {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(authService)
                .passwordEncoder(new BCryptPasswordEncoder());

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.headers(headers ->
                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.csrf(AbstractHttpConfigurer::disable);

        http.exceptionHandling(exception ->
                exception.authenticationEntryPoint(authenticationEntryPoint));

        http.cors(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/users/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/addresses/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/addresses/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/categories/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/categories/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/products/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/products/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/order/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/order/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/order-item/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.POST, "/order-item/**")).permitAll()

                        .requestMatchers(antMatcher(HttpMethod.POST, "/error/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/users/**")).permitAll()
                        //no projeto, precisa listar sem estar cadastrado
                        .anyRequest().authenticated());
        http.authenticationManager(authenticationManager)
                .addFilter(new JWTAuthenticationFilter(authenticationManager, authService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager, authService))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                //session id: id no client, id no server, contem navegador, quem esta acessando. é resetado de acordo com o tempo definido pelo server,
        //stateless - id de sessao por tempo n existe, sempre é unica
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
