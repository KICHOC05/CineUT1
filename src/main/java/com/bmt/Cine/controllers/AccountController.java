package com.bmt.Cine.controllers;

import java.util.Date;

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

@Controller
public class AccountController {
	
	@Autowired
	private AppUserRepository repo;
	
	@GetMapping("register")
	public String register (Model model) {
		RegisterDto registerDto = new RegisterDto();
		model.addAttribute(registerDto);
		model.addAttribute("success", false);
		return "register";
	}

	@PostMapping("/register")
	public String register(
			Model model,
			@Valid @ModelAttribute RegisterDto registerDto,
			BindingResult result
			) {
		
		if (!registerDto.getContraseña().equals(registerDto.getConfirmarContraseña())) {
			result.addError(
					new FieldError("registerDto", "confirmPassword"
							, "Las contraseñas no coinciden")
					);
		}
		
		AppUsers appUser = repo.findByEmail(registerDto.getEmail());
		if (appUser != null) {
			result.addError(
					new FieldError("registerDto", "Email"
							, "El correo esta actualmente en uso")
					);
		}
		
		if (result.hasErrors()) {
			return "register";
		}
		try {
			//Crear nueva cuenta
			var bCryptEncoder = new BCryptPasswordEncoder();
			
			AppUsers newUser = new AppUsers();
			newUser.setNombre(registerDto.getNombre());
			newUser.setApellido(registerDto.getApellido());
			newUser.setEmail(registerDto.getEmail());
			newUser.setTelefono(registerDto.getTelefono());
			newUser.setDireccion(registerDto.getDireccion());
			newUser.setRol("CLIENT");
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
	}
	
}
