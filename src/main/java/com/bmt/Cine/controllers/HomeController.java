// Paquete donde se encuentra la clase
package com.bmt.Cine.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
// Importa anotaciones de Spring
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bmt.Cine.models.Movies;
import com.bmt.Cine.repositories.MoviesRepository;

// Marca esta clase como un controlador de Spring MVC
@Controller
public class HomeController {

    @Autowired
    private MoviesRepository moviesRepository;
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("movies", moviesRepository.findAll());
        return "index";
    }
  
}
    
    
    
    
