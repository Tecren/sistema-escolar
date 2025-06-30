package com.unesc.sistemasescolares.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/")
    public String redirecionarParaFrontend() {
        return "redirect:https://sistema-escolar-frontend.onrender.com";
    }
}

