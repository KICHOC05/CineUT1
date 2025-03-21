package com.bmt.Cine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Configura las autorizaciones para las solicitudes HTTP
                .authorizeHttpRequests(auth -> auth
                        // Permite el acceso público a las rutas especificadas
                        .requestMatchers("/", "/contact", "/store/**", "/register", "/login", "/logout").permitAll()
                        // Restringe el acceso al dashboard solo a usuarios con el rol "ADMIN"
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Requiere autenticación para cualquier otra solicitud
                        .anyRequest().authenticated()
                )
                // Configura el formulario de login
                .formLogin(form -> form
                        .loginPage("/login") // Especifica la página de login personalizada
                        .loginProcessingUrl("/login") // Especifica la URL para procesar el login
                        .defaultSuccessUrl("/", true) // Redirige al usuario a la página principal después del login
                        .failureUrl("/login?error=true") // Redirige al usuario a la página de login en caso de error
                        .permitAll() // Permite el acceso a la página de login a todos
                )
                // Configura el logout
                .logout(logout -> logout
                        .logoutUrl("/logout") // Especifica la URL para cerrar sesión
                        .logoutSuccessUrl("/") // Redirige al usuario a la página principal después del logout
                        .invalidateHttpSession(true) // Invalida la sesión actual
                        .clearAuthentication(true) // Limpia la autenticación
                        .permitAll() // Permite el acceso a la funcionalidad de logout a todos
                )
                // Deshabilita la protección CSRF (opcional)
                .csrf(csrf -> csrf.disable()) // Opcional: deshabilita CSRF si no lo necesitas
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Configura el codificador de contraseñas
    }
}