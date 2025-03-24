package com.bmt.Cine.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("titulo", "Panel de Administración");
        return "admin/index";
    }
}
