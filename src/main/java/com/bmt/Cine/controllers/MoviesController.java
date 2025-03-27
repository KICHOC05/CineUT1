package com.bmt.Cine.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bmt.Cine.models.MovieDto;
import com.bmt.Cine.models.Movies;
import com.bmt.Cine.repositories.MoviesRepository;

@Controller
@RequestMapping("admin/movies")
public class MoviesController {
	
	@Autowired
	private MoviesRepository repo;
	
	@GetMapping({"", "/"})
	public String showMoviesList(Model model) {
		List<Movies> movies = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("movies", movies);
		return ("admin/movies");
	}
	
	@GetMapping("/create")
	public String showCreatePage(Model model) {
		MovieDto movieDto = new MovieDto();
		model.addAttribute("movieDto", movieDto);
		return "admin/CreateMovie";		
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
			movieDto.setEstado(movie.getEstadoPelicula());
			
			model.addAttribute("movieDto" + movieDto);
			
			
		}
		catch(Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			return "redirect:/movies";
		}
		
		
		return "admin/EditProduct";
	}
	
}
