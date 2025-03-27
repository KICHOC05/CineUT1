package com.bmt.Cine.models;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;

public class MovieDto {

	@NotEmpty(message = "El nombre es requerido")
	private String nombre;
	
	@NotEmpty(message = "El genero es requerido")
	private String genero;
	
	@NotEmpty(message = "La sinopsis es requerida")
	private String sinopsis;
	
	@NotEmpty(message = "El horario es requerido")
	private String horario;
	
	@NotEmpty(message = "Las sala es requerida")
	private String sala;
	
	@NotEmpty(message = "El poster es requerido")
	private MultipartFile imagen;
	
	@NotEmpty(message = "El trailer es requerido")
	private String video;
	
	@NotEmpty(message = "El estado es requerido")
	private String estado;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public MultipartFile getImagen() {
		return imagen;
	}

	public void setImagen(MultipartFile MultipartFile) {
		this.imagen = MultipartFile;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}
	
	
	
}
