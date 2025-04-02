package com.bmt.Cine.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bmt.Cine.models.UserDto;
import com.bmt.Cine.models.AppUsers;
import com.bmt.Cine.repositories.AppUserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/users")
public class UsersController {
    
    @Autowired
    private AppUserRepository repo;
    
    @GetMapping("")
    public String showUsersList(Model model) {
        List<AppUsers> users = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("users", users);
        return "admin/users";
    }
    
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "admin/CreateUser";       
    }
    
    @PostMapping("/create")
    public String createUser(
            @Valid @ModelAttribute UserDto userDto,
            BindingResult result
    ) {
        // Validación manual para el email único
        if (repo.findByEmail(userDto.getEmail()) != null) {
            result.addError(new FieldError("userDto", "email", "El email ya está registrado"));
        }
        
        if (result.hasErrors()) {
            return "admin/CreateUser";
        }
        
        // Crear y guardar el usuario
        AppUsers user = new AppUsers();
        user.setNombre(userDto.getNombre());
        user.setApellido(userDto.getApellido());
        user.setEmail(userDto.getEmail());
        user.setTelefono(userDto.getTelefono());
        user.setDireccion(userDto.getDireccion());
        user.setRol(userDto.getRol() != null ? userDto.getRol() : "USER");
        
        repo.save(user);
        
        return "redirect:/admin/users";  
    }
    
    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id) {
        try {
            AppUsers user = repo.findById(id).orElseThrow(() -> 
                new IllegalArgumentException("Usuario no encontrado"));
            
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setNombre(user.getNombre());
            userDto.setApellido(user.getApellido());
            userDto.setEmail(user.getEmail());
            userDto.setTelefono(user.getTelefono());
            userDto.setDireccion(user.getDireccion());
            userDto.setRol(user.getRol());
            
            model.addAttribute("userDto", userDto);
            return "admin/EditUser";
        } catch(Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/admin/users?error=user_not_found";
        }
    }

    @PostMapping("/edit")
    public String updateUser(
            @RequestParam int id,
            @Valid @ModelAttribute("userDto") UserDto userDto,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "admin/EditUser";
        }
        
        try {
            AppUsers user = repo.findById(id).orElseThrow(() -> 
                new IllegalArgumentException("Usuario no encontrado"));
            
            // Solo actualizamos estos campos
            user.setNombre(userDto.getNombre());
            user.setApellido(userDto.getApellido());
            user.setTelefono(userDto.getTelefono());
            user.setDireccion(userDto.getDireccion());
            user.setRol(userDto.getRol());
            
            repo.save(user);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Usuario actualizado correctamente");
        } catch(Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Error al actualizar usuario: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        return "redirect:/admin/users";
    }
    @GetMapping("/delete")
    public String deleteUser(@RequestParam int id) {
        try {
            AppUsers user = repo.findById(id).get();
            repo.delete(user);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }  
        
        return "redirect:/admin/users";
    }
}