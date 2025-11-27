package org.example.projetjeegroupeqspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class GestionController {
    @RequestMapping(value = "/gestion")
    public String gestion() {
        return "Gestion";
    }
}
