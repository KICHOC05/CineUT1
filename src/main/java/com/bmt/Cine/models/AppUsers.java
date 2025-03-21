// Define el paquete donde se encuentra la clase
package com.bmt.Cine.models;

// Importa las clases necesarias
import java.util.Date;
import jakarta.persistence.*;

// Marca la clase como una entidad JPA
@Entity
// Especifica el nombre de la tabla en la base de datos
@Table(name="users")
public class AppUsers {
	
    // Marca el campo como la clave primaria
    @Id
    // Configura la generación automática del valor de la clave primaria
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
    private String Nombre;
    private String Apellido;
	
    // Define una columna con restricciones: única y no nula
    @Column(unique = true, nullable = false)
    private String email;
	
    private String telefono;
    private String direccion;
    private String contraseña;
    private String rol;
    private Date createdAt;

    // Getter y Setter para el campo id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Getter y Setter para el campo Nombre
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    // Getter y Setter para el campo Apellido
    public String getApellido() {
        return Apellido;
    }
    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    // Getter y Setter para el campo email
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter y Setter para el campo telefono
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Getter y Setter para el campo direccion
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Getter y Setter para el campo contraseña
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    // Getter y Setter para el campo rol
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }

    // Getter y Setter para el campo createdAt
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}