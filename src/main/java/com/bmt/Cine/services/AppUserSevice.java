package com.bmt.Cine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bmt.Cine.models.AppUsers;
import com.bmt.Cine.repositories.AppUserRepository;

@Service
public class AppUserSevice implements UserDetailsService {

    @Autowired
    private AppUserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca el usuario por su email en la base de datos
        AppUsers appUser = repo.findByEmail(email);

        // Si el usuario no existe, lanza una excepción
        if (appUser == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
        }

        // Construye el UserDetails con el email, contraseña y roles del usuario
        var springUser = User.withUsername(appUser.getEmail())
                .password(appUser.getContraseña())
                // Asegúrate de que el rol esté prefijado con "ROLE_" y en mayúsculas
                .roles("ROLE_" + appUser.getRol().toUpperCase())
                .build();

        return springUser;
    }
}