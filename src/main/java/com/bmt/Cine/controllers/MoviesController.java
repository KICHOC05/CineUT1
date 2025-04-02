package com.bmt.Cine.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.*;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bmt.Cine.models.MovieDto;
import com.bmt.Cine.models.Movies;
import com.bmt.Cine.repositories.MoviesRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class MoviesController {
    
    @Autowired
    private MoviesRepository repo;
    
    @GetMapping("/movies")
    public String showMoviesList(Model model) {
        List<Movies> movies = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("movies", movies);
        return "admin/movies";
    }
    
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        MovieDto movieDto = new MovieDto();
        model.addAttribute("movieDto", movieDto);
        return "admin/CreateMovie";       
    }
    
    @PostMapping("/create")
    public String createProduct(
            @Valid @ModelAttribute MovieDto movieDto,
            BindingResult result
           ) {
        
        // Validación manual para la imagen
        if (movieDto.getImagen().isEmpty()) {
            result.addError(new FieldError("movieDto", "imagen", "El poster es requerido"));
        }
        
        if (result.hasErrors()) {
            return "admin/CreateMovie";
        }
        
            // Guardar la imagen
            MultipartFile image = movieDto.getImagen();
            Date createdAt = new Date();
            String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
            
            try { 
            	
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Guardar el archivo en el sistema
            try (InputStream inputStream = image.getInputStream()) {
            	Files.copy(inputStream, Paths.get(uploadDir +  storageFileName),
            			StandardCopyOption.REPLACE_EXISTING);            
            }
    } catch (Exception ex){
    	System.out.println("Exception: " + ex.getMessage());
    }
            
            // Crear y guardar la película
            Movies movie = new Movies();
            movie.setNombre(movieDto.getNombre());
            movie.setGenero(movieDto.getGenero());
            movie.setSinopsis(movieDto.getSinopsis());
            movie.setHorario(movieDto.getHorario());
            movie.setSala(movieDto.getSala());
            movie.setVideo(movieDto.getVideo());
            movie.setEstadoPelicula(movieDto.getEstado());
            movie.setImagen(storageFileName); // Guardamos solo el nombre del archivo
            
            repo.save(movie);

    
    return "redirect:/admin/movies";  
    }
    
    @GetMapping("/edit")
    public String showEditPage(
    		Model model, 
    		@RequestParam int id
    		) {
    	
    	try {
    		Movies movie = repo.findById(id).get();
    	     model.addAttribute("movie", movie);
       		
    	    MovieDto movieDto = new MovieDto();
            movieDto.setNombre(movie.getNombre());
            movieDto.setGenero(movie.getGenero());
            movieDto.setSinopsis(movie.getSinopsis());
            movieDto.setHorario(movie.getHorario());
            movieDto.setSala(movie.getSala());
            movieDto.setVideo(movie.getVideo());
            movieDto.setEstado(movie.getEstadoPelicula());
           
            model.addAttribute("movieDto", movieDto);
    	}
    	catch(Exception ex) {
    		System.out.println("Exception: " + ex.getMessage());
    		return "redirect:/admin";
    	}
    
    	return "admin/EditMovie";
    }
    	  @PostMapping("/edit")
    	    public String updateProduct(
    	    	Model model,
    	    	@RequestParam int id,
    	    	@Valid @ModelAttribute MovieDto movieDto,
    	    	BindingResult result
    	    	) {
    	    	
    	    	try {
    	    		Movies movies = repo.findById(id).get();
    	    		model.addAttribute("movies", movies);
    	    		
    	    		if (result.hasErrors()) {
    	    			return "admin/EditMovie";
    	    			}
    	    		if (!movieDto.getImagen().isEmpty()) {
    	    		//Eliminar imagen vieja
    				String uploadDir = "public/images/";
    				Path oldImagePath = Paths.get(uploadDir + movies.getImagen());
    				
    				try {
    					Files.delete(oldImagePath);
    				}
    				catch (Exception ex) {
    					System.out.println("Exception: " + ex.getMessage());
    				}
    				
    				//Guardar nueva imagen
    				MultipartFile image = movieDto.getImagen();
    				Date createdAt = new Date();
    				String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
    				
    				try (InputStream inputStream = image.getInputStream()) {
    					Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
    					StandardCopyOption.REPLACE_EXISTING);	
    	    		}
    				movies.setImagen(storageFileName);
    	    	}
    	    		  movies.setNombre(movieDto.getNombre());
    	              movies.setGenero(movieDto.getGenero());
    	              movies.setSinopsis(movieDto.getSinopsis());
    	              movies.setHorario(movieDto.getHorario());
    	              movies.setSala(movieDto.getSala());
    	              movies.setVideo(movieDto.getVideo());
    	              movies.setEstadoPelicula(movieDto.getEstado());
    	              
    	              repo.save(movies);
    	    	}
    	    	catch(Exception ex) {
    	    		System.out.println("Exception: " + ex.getMessage());
    	    	}
    	    	return "redirect:/admin/movies";
    	    }
    	  
    	  @GetMapping("/delete")
    	  public String deleteMovie(
    		  @RequestParam int id
    		 ) {
    		  
		  try {
			  Movies movie = repo.findById(id).get();
			  
			  //Eliminar imagen pelicula
			  Path imagePath = Paths.get("public/images/" + movie.getImagen());
			  
			  try {
				  Files.delete(imagePath);
			  }
			  catch(Exception ex){
				  System.out.println("Exception: " + ex.getMessage());
			  }
			  
			  //Eliminar la pelicula
			  repo.delete(movie);
		  }
    		catch (Exception ex) {
    			System.out.println("Exception: " + ex.getMessage());
    		}  
    	  return "redirect:/admin/movies";
    	  } 
    	    }
    