package com.bmt.Cine.models;

import jakarta.validation.constraints.*;

public class UserDto {
	
	 private Integer id; // Campo ID añadido
	
    @NotBlank(message = "Nombre es obligatorio")
    @Size(max = 50, message = "Máximo 50 caracteres")
    private String nombre;
    
    @NotBlank(message = "Apellido es obligatorio")
    @Size(max = 50, message = "Máximo 50 caracteres")
    private String apellido;
    
    @NotBlank(message = "Email es obligatorio")
    @Email(message = "Formato de email inválido")
    private String email;
    
    @Size(max = 20, message = "Máximo 20 caracteres")
    private String telefono;
    
    @Size(max = 100, message = "Máximo 100 caracteres")
    private String direccion;
    
    private String rol;

    // Getters y Setters
    
    
    
    public String getNombre() {
        return nombre;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}