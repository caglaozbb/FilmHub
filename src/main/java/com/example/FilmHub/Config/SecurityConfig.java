package com.example.FilmHub.Config;

import com.example.FilmHub.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // CSRF korumasını devre dışı bırakıyoruz
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/register").permitAll()  // Kayıt işlemleri serbest
                        .requestMatchers("/swagger-ui/").permitAll()  // Swagger UI erişimi serbest
                        .requestMatchers("/swagger-resources/").permitAll()  // Swagger kaynakları serbest
                        .requestMatchers("/swagger-ui.html").hasAuthority("admin")  // Swagger HTML erişimi sadece admin
                        .requestMatchers("/v3/api-docs/**").permitAll()  // Swagger API doküman erişimi serbest
                        .requestMatchers("/api/users/all").hasAuthority("user")  // Tüm kullanıcıları listeleme sadece kullanıcı yetkisiyle
                        .anyRequest().authenticated()  // Diğer tüm isteklere kimlik doğrulama gerekli
                )
                .httpBasic(withDefaults())  // Temel kimlik doğrulama
                .formLogin(login -> login
                        .defaultSuccessUrl("/swagger-ui.html", true)  // Giriş sonrası yönlendirme
                        .permitAll()
                )
                .logout(withDefaults());  // Logout işlemi için varsayılan ayarları kullanır

        return http.build();
    }
}

