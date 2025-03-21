// Define el paquete donde se encuentra la clase
package com.bmt.Cine.config;

// Importa las anotaciones y clases necesarias de Spring Security
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Marca esta clase como una clase de configuración de Spring
@Configuration
// Habilita la configuración de seguridad web
@EnableWebSecurity
public class SecurityConfig {

    // Define un bean para configurar la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Configura las autorizaciones para las solicitudes HTTP
                .authorizeHttpRequests(auth -> auth
                        // Permite el acceso público a las rutas especificadas
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/store/**").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()
                        // Requiere autenticación para cualquier otra solicitud
                        .anyRequest().authenticated()
                )
                // Configura el formulario de login
                .formLogin(form -> form
                        // Redirige al usuario a la página principal después del login
                        .defaultSuccessUrl("/", true)
                )
                // Configura el logout
                .logout(config -> config.logoutSuccessUrl("/"))
                // Construye y devuelve la configuración de seguridad
                .build();
    }
    
    @Bean 
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}