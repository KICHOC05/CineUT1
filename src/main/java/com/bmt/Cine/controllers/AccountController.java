// Define el paquete donde se encuentra la clase
package com.bmt.Cine.controllers;

import java.util.Date;

// Importa las anotaciones y clases necesarias
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bmt.Cine.models.AppUsers;
import com.bmt.Cine.models.RegisterDto;
import com.bmt.Cine.repositories.AppUserRepository;

import jakarta.validation.Valid;

// Marca esta clase como un controlador de Spring MVC
@Controller
public class AccountController {

    // Inyecta el repositorio de usuarios
    @Autowired
    private AppUserRepository repo;
    
    // Mapea la ruta GET /register para mostrar el formulario de registro
    @GetMapping("/register")
    public String register(Model model) {
        // Crea un nuevo objeto RegisterDto y lo agrega al modelo
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute("registerDto", registerDto);
        model.addAttribute("success", false);
        // Devuelve la vista "register"
        return "register";
    }
    
    // Mapea la ruta POST /register para procesar el formulario de registro
    @PostMapping("/register")
    public String register(
            Model model,
            // Valida el objeto RegisterDto
            @Valid @ModelAttribute("registerDto") RegisterDto registerDto,
            // BindingResult contiene los errores de validación
            BindingResult result
    ) {
        if (!registerDto.getContraseña().equals(registerDto.getConfirmarContraseña())) {
        	result.addError(
        			new FieldError("registerDto", "ConfirmarContraseña"
        					, "Las contraseñas no coinciden" )
        			);
        }
        
        AppUsers appUser = repo.findByEmail(registerDto.getEmail());
        if (appUser != null) {
        	result.addError(
        			new FieldError("registerDto", "email"
        					,"El Email esta actualmente en uso")
        			);        	
        }
        
        if (result.hasErrors()) {
        	return "register";
        }
        
        try {
        	//Crear una nueva cuenta 
        	var bCryptEncoder = new BCryptPasswordEncoder();
        	
        	AppUsers newUser = new AppUsers();
        	newUser.setNombre(registerDto.getNombre());
        	newUser.setApellido(registerDto.getApellido());
        	newUser.setEmail(registerDto.getEmail());
        	newUser.setTelefono(registerDto.getTelefono());
        	newUser.setDireccion(registerDto.getDireccion());
        	newUser.setRol("cliente");
        	newUser.setCreatedAt(new Date());
        	newUser.setContraseña(bCryptEncoder.encode(registerDto.getContraseña()));
        	
        	repo.save(newUser);
        	
        	model.addAttribute("registerDto", new RegisterDto());
        	model.addAttribute("success", true);
        }
        catch(Exception ex) {
        	result.addError(
        			new FieldError("registerDto", "Nombre"
        					, ex.getMessage())
        			);
        }
        return "register";
        
        
        
        // Lógica para guardar el usuario en la base de datos
        // repo.save(registerDto); // Aquí deberías convertir RegisterDto a una entidad y guardarla
    }
}