// Paquete donde se encuentra la clase
package com.bmt.Cine.controllers;

// Importa anotaciones de Spring
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Marca esta clase como un controlador de Spring MVC
@Controller
public class HomeController {

    // Mapea las rutas raíz ("", "/") al método home()
    @GetMapping({"","/"})
    public String home() {
        // Devuelve la vista "index" (index.html)
        return "index";
    }
    
    // Mapea la ruta "/contact" al método contact()
    @GetMapping({"/contact"})
    public String contact() {
        // Devuelve la vista "contact" (contact.html)
        return "contact";
    }
}